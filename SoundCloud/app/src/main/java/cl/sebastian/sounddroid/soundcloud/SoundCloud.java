package cl.sebastian.sounddroid.soundcloud;

import retrofit.RestAdapter;

/**
 * Created by sebalegutie on 12-03-15.
 */
public class SoundCloud {
    static final String API_URL = "http://api.soundcloud.com";
    static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .build();
    static final SoundCloudService SERVICE = REST_ADAPTER.create(SoundCloudService.class);

    public static SoundCloudService getService(){
        return SERVICE;
    }
}
