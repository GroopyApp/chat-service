package app.groopy.chatservice.infrastructure.models.chathistory;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ChatHistoryResponse {
    Map<String, List<ChatHistoryRecord>> chatHistory;
}
