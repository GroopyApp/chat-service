package app.groopy.chatservice.infrastructure.providers.db.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@Document("chats")
public class ChatEntity extends Entity {
    String groupName;
    String channelName;
    String uuid;
    LocalDateTime date;
}
