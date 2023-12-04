package app.groopy.chatservice.infrastructure.providers.retrofit.models;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

@Value
@ToString
@EqualsAndHashCode
public class PubNubChannelHistoryRecord implements Serializable {

    @SerializedName("message")
    PubNubChatMessage message;

    @SerializedName("timetoken")
    String timetoken;

    @Value
    public class PubNubChatMessage implements Serializable {
        @SerializedName("message")
        String message;
        @SerializedName("senderId")
        String senderId;
    }
}
