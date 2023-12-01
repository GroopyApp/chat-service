package app.groopy.chatservice.infrastructure.repository;

import app.groopy.chatservice.domain.models.requests.ChatMessageRequestDto;
import app.groopy.chatservice.infrastructure.models.ChatInfo;
import app.groopy.chatservice.infrastructure.models.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.models.CreateChatChannelResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatProviderRepository {
    CreateChatChannelResponse createChannel(CreateChatChannelRequest request);
    List<ChatInfo> getChatInfo(List<String> ids);

    Integer fireMessage(ChatMessageRequestDto chatMessageRequestDto);
}
