package cl.sebastian.sounddroid.soundcloud;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by sebalegutie on 11-03-15.
 */
public interface SoundCloudService {

    static final String CLIENT_ID="492ef483a178476f1546f7b160ddbbf6";

    @GET("/tracks?client_id="+CLIENT_ID)
    public void searchSongs(@Query("q") String query, Callback<List<Track>> cb);
}
