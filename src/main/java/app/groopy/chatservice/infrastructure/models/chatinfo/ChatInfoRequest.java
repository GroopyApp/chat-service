package app.groopy.chatservice.infrastructure.models.chatinfo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatInfoRequest {
    List<String> ids;
    List<String> channels;
}
