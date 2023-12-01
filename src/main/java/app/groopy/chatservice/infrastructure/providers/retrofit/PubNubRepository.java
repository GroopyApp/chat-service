package app.groopy.chatservice.infrastructure.providers.retrofit;

import app.groopy.chatservice.infrastructure.providers.retrofit.models.PubNubCreateChannelResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PubNubRepository {

    //FIXME find a way to use @Value to fetch from properties file
    String SUBSCRIBE_KEY = "sub-c-7f2c8668-6e8f-44ef-b387-cb7882fd63f1";

    String ADDING_CHANNEL_ENDPOINT = "/v1/channel-registration/sub-key/" + SUBSCRIBE_KEY + "/channel-group/{group}";

    @GET(ADDING_CHANNEL_ENDPOINT)
    Call<PubNubCreateChannelResponse> createChannel(@Path("group") String group, @Query("add") String channelName, @Query("uuid") String uuid);
}
