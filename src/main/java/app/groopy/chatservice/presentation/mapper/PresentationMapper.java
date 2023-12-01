package app.groopy.chatservice.presentation.mapper;

import app.groopy.chatservice.domain.models.requests.CreateChatRoomRequestDto;
import app.groopy.chatservice.domain.models.requests.ChatDetailsRequestDto;
import app.groopy.protobuf.ChatServiceProto;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface PresentationMapper {

    //PROTO to DTO
    CreateChatRoomRequestDto map(ChatServiceProto.CreateChatRoomRequest input);

    ChatDetailsRequestDto map(ChatServiceProto.ChatDetailsRequest input);

    default LocalDateTime toLocalDate(String input) {
        return LocalDateTime.parse(input);
    }

    default String toDateString(LocalDateTime input) {
        return input.toString();
    }
}
