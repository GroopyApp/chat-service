package app.groopy.chatservice.infrastructure.models.chathistory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatHistoryRequest {
    List<String> channels;
    LocalDateTime start;
    LocalDateTime end;
}
