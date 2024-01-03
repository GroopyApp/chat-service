package app.groopy.chatservice.infrastructure.providers.retrofit;

import app.groopy.chatservice.infrastructure.providers.retrofit.models.ChatMessageRequest;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.http.*;

@Repository
public interface PubNubRepository {

    String PUBLISH_KEY_PLACEHOLDER = "{pubKey}";
    String SUBSCRIBE_KEY_PLACEHOLDER = "{subKey}";

    String ADDING_CHANNEL_ENDPOINT = "/v2/subscribe/" + SUBSCRIBE_KEY_PLACEHOLDER + "/{channel}/{callback}";
    String FIRE_MESSAGE_ENDPOINT = "/publish/" + PUBLISH_KEY_PLACEHOLDER + "/" + SUBSCRIBE_KEY_PLACEHOLDER + "/0/{channel}/{callback}?store=1";

    @GET(ADDING_CHANNEL_ENDPOINT)
    Call<String> createChannel(
            @Path("subKey") String subKey,
            @Path("channel") String channel,
            @Path("callback") String callback,
            @Query("channel-group") String group,
            @Query("uuid") String uuid);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST(FIRE_MESSAGE_ENDPOINT)
    Call<String> fireMessage(
            @Path("subKey") String subKey,
            @Path("pubKey") String pubKey,
            @Path("channel") String channel,
            @Path("callback") String callback,
            @Query("space-id") String group,
            @Body ChatMessageRequest message);
}
