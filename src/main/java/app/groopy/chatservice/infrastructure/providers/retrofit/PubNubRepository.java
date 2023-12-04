package app.groopy.chatservice.infrastructure.providers.retrofit;

import app.groopy.chatservice.infrastructure.providers.retrofit.models.PubNubChatMessageRequest;
import app.groopy.chatservice.infrastructure.providers.retrofit.models.PubNubChatHistoryResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface PubNubRepository {

    //FIXME find a way to use @Value to fetch from properties file
    String SUBSCRIBE_KEY = "sub-c-7f2c8668-6e8f-44ef-b387-cb7882fd63f1";
    String PUBLISH_HEY = "pub-c-dbb19108-b72c-4c11-82c1-ae22896f203d";

    String ADDING_CHANNEL_ENDPOINT = "/v2/subscribe/" + SUBSCRIBE_KEY + "/{channel}/{callback}";
    String FIRE_MESSAGE_ENDPOINT = "/publish/" + PUBLISH_HEY + "/" + SUBSCRIBE_KEY + "/0/{channel}/{callback}?store=1";
    String HISTORY_ENDPOINT = "/v3/history/sub-key/" + SUBSCRIBE_KEY + "/channel/{channels}?max=1000";

    @GET(ADDING_CHANNEL_ENDPOINT)
    Call<String> createChannel(
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
            @Body PubNubChatMessageRequest message);

    @GET(HISTORY_ENDPOINT)
    Call<PubNubChatHistoryResponse> getHistory(
            @Path("channels") String channels,
            @Query("start") Long startDateTime,
            @Query("end") Long endDateTime);
}
