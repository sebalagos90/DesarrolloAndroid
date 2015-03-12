package cl.sebastian.sounddroid.soundcloud;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sebalegutie on 11-03-15.
 */
public class Track {
    @SerializedName("title")
    private String title;

    @SerializedName("stream_url")
    private String streamURL;

    @SerializedName("id")
    private int id;

    @SerializedName("artwork_url")
    String artWorkURL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreamURL() {
        return streamURL;
    }

    public void setStreamURL(String streamURL) {
        this.streamURL = streamURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtWorkURL() {
        return artWorkURL;
    }

    public void setArtWorkURL(String artWorkURL) {
        this.artWorkURL = artWorkURL;
    }

    public String getAvatarURL(){
        String avatarURL = artWorkURL;
        if(avatarURL != null){
            avatarURL = avatarURL.replace("large","tiny");
        }
        return artWorkURL;
    }
}
