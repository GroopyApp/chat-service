package app.groopy.chatservice.infrastructure.providers.models;

import lombok.Value;

@Value
public class PubNubCreateChannelResponse {
    String service;
    String status;
    Boolean error;
    String message;
}
