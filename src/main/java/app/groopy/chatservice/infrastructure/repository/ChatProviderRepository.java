package app.groopy.chatservice.infrastructure.repository;

import app.groopy.chatservice.infrastructure.models.chathistory.ChatHistoryRequest;
import app.groopy.chatservice.infrastructure.models.chathistory.ChatHistoryResponse;
import app.groopy.chatservice.infrastructure.models.chatinfo.ChatInfoRequest;
import app.groopy.chatservice.infrastructure.models.chatinfo.ChatInfoResponse;
import app.groopy.chatservice.infrastructure.models.createchannel.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.models.createchannel.CreateChatChannelResponse;
import app.groopy.chatservice.infrastructure.models.firemessage.FireMessageRequest;
import app.groopy.chatservice.infrastructure.models.firemessage.FireMessageResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatProviderRepository {
    CreateChatChannelResponse createChannel(CreateChatChannelRequest request);
    ChatInfoResponse getChatInfo(ChatInfoRequest request);
    FireMessageResponse fireMessage(FireMessageRequest fireMessageRequest);
    ChatHistoryResponse getChatHistory(ChatHistoryRequest chatHistoryRequest);
}
