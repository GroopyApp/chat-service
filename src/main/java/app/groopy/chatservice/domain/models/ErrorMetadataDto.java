package app.groopy.chatservice.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMetadataDto {
    String entityName;
    String existingEntityId;
    String errorDescription;
    String notFoundId;
    String startDate;
    String endDate;
    String userId;
    String targetId;
}
