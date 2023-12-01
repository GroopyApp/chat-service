package app.groopy.chatservice.application;

import app.groopy.chatservice.application.exceptions.ApplicationException;
import app.groopy.chatservice.domain.models.entities.ChatInfoDto;
import app.groopy.chatservice.domain.models.requests.ChatDetailsRequestDto;
import app.groopy.chatservice.domain.models.requests.CreateChatRoomRequestDto;
import app.groopy.chatservice.domain.resolver.InfrastructureExceptionResolver;
import app.groopy.chatservice.domain.services.DomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);

    private final DomainService domainService;

    @Autowired
    public ApplicationService(DomainService domainService) {
        this.domainService = domainService;
    }

    public ChatInfoDto createChatRoom(CreateChatRoomRequestDto request) throws ApplicationException {
        try {
            var result = domainService.createRoom(request);
            LOGGER.info("chat room created successfully: {}", result);
            return result;
        } catch (Exception e) {
            throw InfrastructureExceptionResolver.resolve(e);
        }
    }

    public List<ChatInfoDto> getDetails(ChatDetailsRequestDto request) throws ApplicationException {
        try {
            var result = domainService.get(request.getIds());
            LOGGER.info("chat rooms fetched successfully: {}", result);
            return result;
        } catch (Exception e) {
            throw InfrastructureExceptionResolver.resolve(e);
        }    }
}
