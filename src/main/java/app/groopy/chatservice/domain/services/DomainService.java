package app.groopy.chatservice.domain.services;

import app.groopy.chatservice.application.mapper.ApplicationMapper;
import app.groopy.chatservice.domain.models.entities.ChatInfoDto;
import app.groopy.chatservice.domain.models.requests.CreateChatRoomRequestDto;
import app.groopy.chatservice.infrastructure.models.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.repository.ChatProviderRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static app.groopy.chatservice.domain.utils.Utils.generateChatGroupName;
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
                        .groupName(generateChatGroupName(request.getGroupName()))
                        .uuid(request.getUuid())
                .build());
        return ChatInfoDto.builder()
                .channelName(result.getChannelName())
                .groupName(result.getGroupName())
                .uuid(request.getUuid())
                .build();
    }
}
