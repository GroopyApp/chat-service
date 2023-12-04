package app.groopy.chatservice.infrastructure.models.chathistory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatHistoryRecord {
    private String senderId;
    private String message;
    private LocalDateTime dateTime;
}
