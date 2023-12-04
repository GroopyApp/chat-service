package app.groopy.chatservice.infrastructure.models.firemessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FireMessageRequest {
    String senderId;
    String message;
    String channelId;
    String groupId;
}