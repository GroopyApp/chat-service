package app.groopy.chatservice.domain.models.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatDetailsRequestDto {
    List<String> ids;
}
