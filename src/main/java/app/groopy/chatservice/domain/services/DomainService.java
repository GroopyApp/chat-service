package app.groopy.chatservice.domain.services;

import app.groopy.chatservice.application.mapper.ApplicationMapper;
import app.groopy.chatservice.domain.models.entities.ChatInfoDto;
import app.groopy.chatservice.domain.models.requests.ChatMessageRequestDto;
import app.groopy.chatservice.domain.models.requests.CreateChatRoomRequestDto;
import app.groopy.chatservice.infrastructure.models.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.repository.ChatProviderRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<ChatInfoDto> get(List<String> ids) {
        LOGGER.info("trying to get chat rooms: {}", ids);
        var result = chatProviderRepository.getChatInfo(ids);
        return result.stream().map(chatInfo -> ChatInfoDto.builder()
                .channelName(chatInfo.getChatName())
                .groupName(chatInfo.getGroupName())
                .uuid(chatInfo.getUuid())
                .build()).toList();
    }

    public Integer fireMessage(ChatMessageRequestDto chatMessageRequestDto) {
        LOGGER.info("trying to fire a message: {}", chatMessageRequestDto);
        return chatProviderRepository.fireMessage(chatMessageRequestDto);
    }
}
