package app.groopy.chatservice.infrastructure.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatInfo {
    private String chatName;
    private String groupName;
    private String uuid;
}
