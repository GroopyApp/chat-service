package app.groopy.chatservice.infrastructure.providers.mappers;

import app.groopy.chatservice.infrastructure.models.ChatInfo;
import app.groopy.chatservice.infrastructure.providers.db.models.ChatEntity;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProviderMapper {

    @Mappings({
            @Mapping(target = "chatName", source = "channelName")})
    ChatInfo map(ChatEntity input);
}
