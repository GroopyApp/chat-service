package app.groopy.chatservice.infrastructure.repository;

import app.groopy.chatservice.infrastructure.models.CreateChatChannelRequest;
import app.groopy.chatservice.infrastructure.models.CreateChatChannelResponse;

public interface ChatProviderRepository {
    CreateChatChannelResponse createChannel(CreateChatChannelRequest request);
}
