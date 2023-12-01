package app.groopy.chatservice.infrastructure.providers.db;

import app.groopy.chatservice.infrastructure.providers.db.models.ChatEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DatabaseRepository extends MongoRepository<ChatEntity, String> {

    List<ChatEntity> findAllByUuidIn(List<String> uuids);

}
