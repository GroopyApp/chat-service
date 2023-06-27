package app.groopy.chatservice.domain.resolver;

import app.groopy.chatservice.application.exceptions.ApplicationException;
import app.groopy.chatservice.domain.models.ErrorMetadataDto;

public class InfrastructureExceptionResolver {

    public static ApplicationException resolve(Exception e) {
//        if (e instanceof EntityAlreadyExistsException) {
//            var ex = (EntityAlreadyExistsException) e;
//            return new ApplicationAlreadyExistsException(ErrorMetadataDto.builder()
//                    .errorDescription(ex.getLocalizedMessage())
//                    .existingEntityId(ex.getId())
//                    .entityName(ex.getEntityName())
//                    .build());

        return new ApplicationException(ErrorMetadataDto.builder()
                .errorDescription("Unmapped exception")
                .build());
    }
}
