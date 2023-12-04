package app.groopy.chatservice.infrastructure.providers.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;

import java.io.Serializable;

@Builder
public class PubNubChatMessageRequest implements Serializable {

    @Expose
    @SerializedName("message")
    String message;

    @Expose
    @SerializedName("senderId")
    String senderId;
}
