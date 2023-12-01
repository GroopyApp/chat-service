package app.groopy.chatservice.infrastructure.providers.retrofit;

import app.groopy.chatservice.infrastructure.providers.retrofit.models.ChatMessageRequest;
import app.groopy.chatservice.infrastructure.providers.retrofit.models.PubNubResponse;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PubNubRepository {

    //FIXME find a way to use @Value to fetch from properties file
    String SUBSCRIBE_KEY = "sub-c-7f2c8668-6e8f-44ef-b387-cb7882fd63f1";
    String PUBLISH_HEY = "pub-c-dbb19108-b72c-4c11-82c1-ae22896f203d";

    String ADDING_CHANNEL_ENDPOINT = "/v2/subscribe/" + SUBSCRIBE_KEY + "/{channel}/{callback}";
    String FIRE_MESSAGE_ENDPOINT = "/publish/" + PUBLISH_HEY + "/" + SUBSCRIBE_KEY + "/0/{channel}/{callback}?store=1";

    @GET(ADDING_CHANNEL_ENDPOINT)
    Call<PubNubResponse> createChannel(
            @Path("channel") String channel,
            @Path("callback") String callback,
            @Query("channel-group") String group,
            @Query("uuid") String uuid);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST(FIRE_MESSAGE_ENDPOINT)
    Call<String> fireMessage(
            @Path("channel") String channel,
            @Path("callback") String callback,
            @Query("space-id") String group,
            @Body ChatMessageRequest message);
}
