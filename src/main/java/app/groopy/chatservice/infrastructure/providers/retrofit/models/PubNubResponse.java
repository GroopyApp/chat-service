package app.groopy.chatservice.infrastructure.providers.retrofit.models;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class PubNubResponse {
    String service;
    String status;
    Boolean error;
    String message;
}
