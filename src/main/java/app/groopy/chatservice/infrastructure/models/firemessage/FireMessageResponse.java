package app.groopy.chatservice.infrastructure.models.firemessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FireMessageResponse {
    private Integer status;
}
