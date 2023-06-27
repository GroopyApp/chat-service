package app.groopy.chatservice.domain.models.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatInfoDto {
    String channelName;
    String groupName;
    String uuid;

}
