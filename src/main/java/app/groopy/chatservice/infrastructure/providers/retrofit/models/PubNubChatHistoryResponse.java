package app.groopy.chatservice.infrastructure.providers.retrofit.models;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Value
@ToString
@EqualsAndHashCode
public class PubNubChatHistoryResponse implements Serializable {

    @SerializedName("status")
    String status;

    @SerializedName("error")
    boolean error;

    @SerializedName("error_message")
    String errorMessage;

    @SerializedName("channels")
    Map<String, List<PubNubChannelHistoryRecord>> channels;
}
