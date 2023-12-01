package app.groopy.chatservice.domain.models.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageRequestDto {
    String senderId;
    String message;
    String channelId;
    String groupId;
}