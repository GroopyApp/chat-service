package app.groopy.chatservice.infrastructure.providers;

import app.groopy.chatservice.infrastructure.models.chathistory.ChatHistoryRecord;
import app.groopy.chatservice.infrastructure.models.chathistory.ChatHistoryRequest;
import app.groopy.chatservice.infrastructure.models.chathistory.ChatHistoryResponse;
import app.groopy.chatservice.infrastructure.models.chatinfo.ChatInfoRequest;
import app.groopy.chatservice.infrastructure.models.chatinfo.ChatInfoResponse;
import app.groopy.chatservice.infrastructure.models.createchannel.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.models.createchannel.CreateChatChannelResponse;
import app.groopy.chatservice.infrastructure.models.firemessage.FireMessageRequest;
import app.groopy.chatservice.infrastructure.models.firemessage.FireMessageResponse;
import app.groopy.chatservice.infrastructure.providers.db.DatabaseRepository;
import app.groopy.chatservice.infrastructure.providers.db.models.ChatEntity;
import app.groopy.chatservice.infrastructure.providers.exceptions.DatabaseException;
import app.groopy.chatservice.infrastructure.providers.exceptions.PubNubException;
import app.groopy.chatservice.infrastructure.providers.mappers.ProviderMapper;
import app.groopy.chatservice.infrastructure.providers.retrofit.PubNubRepository;
import app.groopy.chatservice.infrastructure.providers.retrofit.models.PubNubChatHistoryResponse;
import app.groopy.chatservice.infrastructure.providers.retrofit.models.PubNubChatMessageRequest;
import app.groopy.chatservice.infrastructure.providers.retrofit.utils.TimeTokenConverter;
import app.groopy.chatservice.infrastructure.repository.ChatProviderRepository;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatProviderRepositoryImpl implements ChatProviderRepository {


    private final Logger LOGGER = LoggerFactory.getLogger(ChatProviderRepositoryImpl.class);
    private final PubNubRepository pubNubRepository;
    private final DatabaseRepository databaseRepository;
    private final ProviderMapper providerMapper;

    @Value("${pubnub.pub-key}")
    private String PUB_KEY;
    @Value("${pubnub.sub-key}")
    private String SUB_KEY;

    @SneakyThrows
    @Autowired
    public ChatProviderRepositoryImpl(@Value("${pubnub.host}") String pubnubHost,
                                      DatabaseRepository databaseRepository,
                                      ProviderMapper providerMapper) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(pubnubHost)
                .addConverterFactory(ScalarsConverterFactory.create()) //important
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
        this.pubNubRepository = retrofit.create(PubNubRepository.class);
        this.databaseRepository = databaseRepository;
        this.providerMapper = providerMapper;
    }

    @SneakyThrows
    public CreateChatChannelResponse createChannel(CreateChatChannelRequest request) {
        ChatEntity chatEntity;
        try {
            chatEntity = databaseRepository.save(ChatEntity.builder()
                    .groupName(request.getGroupName())
                    .channelName(request.getChannelName())
                    .uuid(request.getUuid())
                    .date(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            LOGGER.error("An error occurred trying to save the chat room: request={}", request, e);
            throw new DatabaseException(e.getMessage());
        }

        Response<String> createChannelResponse = pubNubRepository.createChannel(
                SUB_KEY,
                request.getChannelName(),
                "callback",
                request.getGroupName(),
                request.getUuid()
        ).execute();

        if (!createChannelResponse.isSuccessful()) {
            LOGGER.error("An error occurred trying to create the chat room: request={}, error={}", request, createChannelResponse.errorBody());
            databaseRepository.delete(chatEntity);
            throw new PubNubException(createChannelResponse.errorBody() != null ?
                    createChannelResponse.errorBody().toString() :
                    createChannelResponse.body() != null ?
                            createChannelResponse.body() :
                            "Unknown error");
        }

        LOGGER.info("Chat room created successfully: response={}", createChannelResponse.body());
        return CreateChatChannelResponse.builder()
                .channelName(request.getChannelName())
                .groupName(request.getGroupName())
                .build();
    }

    @Override
    public ChatInfoResponse getChatInfo(ChatInfoRequest request) {
        if (request.getIds() != null && !request.getIds().isEmpty()) {
            return ChatInfoResponse.builder().chatInfo(
                            databaseRepository.findAllByUuidIn(request.getIds()).stream()
                                    .map(providerMapper::map)
                                    .toList())
                    .build();
        }
        if (request.getChannels() != null && !request.getChannels().isEmpty()) {
            return ChatInfoResponse.builder().chatInfo(
                            databaseRepository.findAllByChannelNameIn(request.getChannels()).stream()
                                    .map(providerMapper::map)
                                    .toList())
                    .build();
        }
        return ChatInfoResponse.builder().build();
    }

    @SneakyThrows
    public FireMessageResponse fireMessage(FireMessageRequest fireMessageRequest) {
        var response = pubNubRepository.fireMessage(
                SUB_KEY,
                PUB_KEY,
                fireMessageRequest.getChannelId(),
                "callback",
                fireMessageRequest.getGroupId(),
                        PubNubChatMessageRequest.builder()
                        .message(fireMessageRequest.getMessage())
                        .senderId(fireMessageRequest.getSenderId())
                        .build())
                .execute();
        if (response.code() != 200) {
            LOGGER.error("An error occurred trying to send the message: request={}, response={}", fireMessageRequest, response.errorBody());
            throw new PubNubException("An error occurred trying to send the message");
        }
        //TODO investigate if it's worth to save the message in the database
        return FireMessageResponse.builder()
                .status(response.code())
                .build();
    }

    @SneakyThrows
    public ChatHistoryResponse getChatHistory(ChatHistoryRequest request) {

        LocalDateTime dateFrom = request.getStart() != null ? request.getStart() : LocalDateTime.now().minusDays(7);
        LocalDateTime dateTo = request.getEnd() != null ? request.getEnd() : LocalDateTime.now();

        Response<PubNubChatHistoryResponse> response = pubNubRepository.getHistory(
                String.join(",", request.getChannels()), TimeTokenConverter.toTimeToken(dateFrom),
                TimeTokenConverter.toTimeToken(dateTo)).execute();

        if (!response.isSuccessful()) {
            LOGGER.error("An error occurred trying to fetch chat history: request={}, error={}", request, response.errorBody());
            throw new PubNubException(response.errorBody() != null ?
                    response.errorBody().toString() : "Unknown error");
        }

        LOGGER.info("Chat history fetched successfully: response={}", response.body());
        return ChatHistoryResponse.builder()
                .chatHistory(response.body().getChannels().entrySet().stream().collect(
                        Collectors.toMap(Map.Entry::getKey,
                                entry -> entry.getValue().stream().map(elem -> ChatHistoryRecord.builder()
                                .message(elem.getMessage().getMessage())
                                .senderId(elem.getMessage().getSenderId())
                                .dateTime(TimeTokenConverter.toLocalDateTime(elem.getTimetoken()))
                                .build()).toList())))
                .build();
    }
}
