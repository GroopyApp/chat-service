package app.groopy.chatservice.application.exceptions;

import app.groopy.chatservice.domain.models.ErrorMetadataDto;
import lombok.Getter;

@Getter
public class ApplicationException extends Exception {
    private final ErrorMetadataDto errorResponse;

    public ApplicationException(ErrorMetadataDto errorResponse) {
        this.errorResponse = errorResponse;
    }
}
