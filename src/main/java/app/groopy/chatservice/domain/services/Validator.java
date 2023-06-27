package app.groopy.chatservice.domain.services;

import app.groopy.chatservice.domain.exceptions.EndDateIsBeforeException;
import app.groopy.chatservice.domain.models.requests.CreateChatRoomRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public Validator(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void validate(CreateChatRoomRequestDto createEventRequest) throws EndDateIsBeforeException {
//        if (createEventRequest.getEndDate().isBefore(createEventRequest.getStartDate())) {
//            throw new EndDateIsBeforeException(createEventRequest.getStartDate(), createEventRequest.getEndDate());
//        }
//        if (createEventRequest.getStartDate().isBefore(LocalDateTime.now())) {
//            throw new EventInThePastException(createEventRequest.getStartDate());
//        }
    }
}
