package app.groopy.chatservice.presentation.mapper;

import app.groopy.chatservice.domain.models.requests.ChatHistoryRequestDto;
import app.groopy.chatservice.domain.models.requests.ChatMessageRequestDto;
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

    ChatHistoryRequestDto map(ChatServiceProto.ChatHistoryRequest input);

    @Mappings({@Mapping(target = "message", source = "body")})
    ChatMessageRequestDto map(ChatServiceProto.ChatMessageRequest input);

    default LocalDateTime toLocalDate(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(input);
    }

    default String toDateString(LocalDateTime input) {
        if (input == null) {
            return null;
        }
        return input.toString();
    }
}
