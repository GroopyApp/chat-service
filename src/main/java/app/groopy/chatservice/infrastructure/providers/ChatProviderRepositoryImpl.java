package app.groopy.chatservice.infrastructure.providers;

import app.groopy.chatservice.infrastructure.models.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.models.CreateChatChannelResponse;
import app.groopy.chatservice.infrastructure.providers.exceptions.PubNubException;
import app.groopy.chatservice.infrastructure.providers.models.PubNubCreateChannelResponse;
import app.groopy.chatservice.infrastructure.providers.retrofit.PubNubRepository;
import app.groopy.chatservice.infrastructure.repository.ChatProviderRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class ChatProviderRepositoryImpl implements ChatProviderRepository {


    private final Logger LOGGER = LoggerFactory.getLogger(ChatProviderRepositoryImpl.class);
    private final PubNubRepository pubNubRepository;

    @SneakyThrows
    @Autowired
    public ChatProviderRepositoryImpl(@Value("${pubnub.host}") String pubnubHost) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(pubnubHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.pubNubRepository = retrofit.create(PubNubRepository.class);
    }

    @SneakyThrows
    public CreateChatChannelResponse createChannel(CreateChatChannelRequest request) {
        Response<PubNubCreateChannelResponse> createChannelResponse = pubNubRepository.createChannel(
                request.getGroupName(),
                request.getChannelName(),
                request.getUuid()
        ).execute();

        if (!createChannelResponse.isSuccessful() || (createChannelResponse.body() != null && createChannelResponse.body().getError())) {
            LOGGER.error("An error occurred trying to create the chat room: request={}, error={}", request, createChannelResponse.errorBody());
            throw new PubNubException(createChannelResponse.errorBody() != null ?
                    createChannelResponse.errorBody().toString() :
                    createChannelResponse.body() != null ?
                            createChannelResponse.body().getMessage() :
                            "Unknown error");
        }

        LOGGER.info("Chat room created successfully: response={}", createChannelResponse.body());

        return CreateChatChannelResponse.builder()
                .channelName(request.getChannelName())
                .groupName(request.getGroupName())
                .build();
    }
}
