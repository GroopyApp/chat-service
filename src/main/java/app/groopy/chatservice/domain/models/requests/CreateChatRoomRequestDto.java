package app.groopy.chatservice.domain.models.requests;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateChatRoomRequestDto {
    String channelName;
    String groupName;
    String uuid;
}
