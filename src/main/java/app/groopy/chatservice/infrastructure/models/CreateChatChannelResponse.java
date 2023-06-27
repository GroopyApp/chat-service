package app.groopy.chatservice.infrastructure.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateChatChannelResponse {

    String channelName;
    String groupName;
}
