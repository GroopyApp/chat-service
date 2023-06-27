package app.groopy.chatservice.application.exceptions;

import app.groopy.chatservice.domain.models.ErrorMetadataDto;
import lombok.Getter;

@Getter
public class ApplicationBadRequestException extends ApplicationException {

    public ApplicationBadRequestException(ErrorMetadataDto errorResponse) {
        super(errorResponse);
    }
}
