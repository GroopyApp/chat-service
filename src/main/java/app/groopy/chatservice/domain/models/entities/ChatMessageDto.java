package app.groopy.chatservice.domain.models.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageDto {
    String senderId;
    String body;
    String groupId;
    String dateTime;
}
