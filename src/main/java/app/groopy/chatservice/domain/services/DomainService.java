package app.groopy.chatservice.domain.services;

import app.groopy.chatservice.application.mapper.ApplicationMapper;
import app.groopy.chatservice.domain.models.entities.ChatInfoDto;
import app.groopy.chatservice.domain.models.entities.ChatMessageDto;
import app.groopy.chatservice.domain.models.requests.ChatMessageRequestDto;
import app.groopy.chatservice.domain.models.requests.CreateChatRoomRequestDto;
import app.groopy.chatservice.infrastructure.models.chathistory.ChatHistoryRequest;
import app.groopy.chatservice.infrastructure.models.chatinfo.ChatInfo;
import app.groopy.chatservice.infrastructure.models.chatinfo.ChatInfoRequest;
import app.groopy.chatservice.infrastructure.models.createchannel.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.models.firemessage.FireMessageRequest;
import app.groopy.chatservice.infrastructure.repository.ChatProviderRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static app.groopy.chatservice.domain.utils.Utils.generateChatName;

@Component
public class DomainService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainService.class);

    private final ApplicationMapper applicationMapper;
    private final ChatProviderRepository chatProviderRepository;

    @Autowired
    public DomainService(ApplicationMapper applicationMapper, ChatProviderRepository chatProviderRepository) {
        this.applicationMapper = applicationMapper;
        this.chatProviderRepository = chatProviderRepository;
    }

    @SneakyThrows
    public ChatInfoDto createRoom(CreateChatRoomRequestDto request) {
        LOGGER.info("trying to create a chat room: {}", request);
        var result = chatProviderRepository.createChannel(CreateChatChannelRequest.builder()
                        .channelName(generateChatName(request.getChannelName()))
                        .groupName(request.getGroupName())
                        .uuid(request.getUuid())
                .build());
        return ChatInfoDto.builder()
                .channelName(result.getChannelName())
                .groupName(result.getGroupName())
                .uuid(request.getUuid())
                .build();
    }

    public List<ChatInfoDto> getChatsDetails(List<String> ids) {
        LOGGER.info("trying to get chat rooms: {}", ids);
        var result = chatProviderRepository.getChatInfo(ChatInfoRequest.builder()
                        .ids(ids)
                .build());
        return result.getChatInfo().stream().map(chatInfo -> ChatInfoDto.builder()
                .channelName(chatInfo.getChatName())
                .groupName(chatInfo.getGroupName())
                .uuid(chatInfo.getUuid())
                .build()).toList();
    }

    public Map<String, List<ChatMessageDto>> getChatHistory(List<String> channels, LocalDateTime dateFrom, LocalDateTime dateTo) {
        LOGGER.info("trying to get chat histories for channels: {}", channels);
        var result = chatProviderRepository.getChatHistory(ChatHistoryRequest.builder()
                        .channels(channels)
                        .start(dateFrom)
                        .end(dateTo)
                .build());

        Map<String, String> channelToGroup = chatProviderRepository.getChatInfo(ChatInfoRequest.builder()
                        .channels(channels)
                .build()).getChatInfo().stream().collect(Collectors.toMap(ChatInfo::getChatName, ChatInfo::getGroupName));

        return result.getChatHistory().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        entry.getValue().stream().map(message -> ChatMessageDto.builder()
                        .body(message.getMessage())
                        .senderId(message.getSenderId())
                        .dateTime(message.getDateTime().toString())
                        .groupId(channelToGroup.get(entry.getKey()))
                        .build()).sorted(Comparator.comparing(ChatMessageDto::getDateTime).reversed()).toList()));
    }

    public Integer fireMessage(ChatMessageRequestDto chatMessageRequestDto) {
        LOGGER.info("trying to fire a message: {}", chatMessageRequestDto);
        return chatProviderRepository.fireMessage(FireMessageRequest.builder()
                .message(chatMessageRequestDto.getMessage())
                        .channelId(chatMessageRequestDto.getChannelId())
                        .groupId(chatMessageRequestDto.getGroupId())
                        .senderId(chatMessageRequestDto.getSenderId())
                        .build())
                .getStatus();
    }
}
