package app.groopy.chatservice.infrastructure.providers.db.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class Entity {
    @MongoId(value = FieldType.OBJECT_ID)
    private String id;
}
