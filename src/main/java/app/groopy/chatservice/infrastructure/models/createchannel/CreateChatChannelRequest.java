package app.groopy.chatservice.infrastructure.models.createchannel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateChatChannelRequest {
    String channelName;
    String groupName;
    String uuid;
}
