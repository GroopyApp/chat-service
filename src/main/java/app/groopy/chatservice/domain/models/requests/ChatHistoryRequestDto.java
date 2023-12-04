package app.groopy.chatservice.domain.models.requests;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatHistoryRequestDto {
    List<String> channels;
    LocalDateTime dateFrom;
    LocalDateTime dateTo;
}
