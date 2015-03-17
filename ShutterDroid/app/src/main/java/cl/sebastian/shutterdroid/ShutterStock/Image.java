package cl.sebastian.shutterdroid.ShutterStock;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sebalegutie on 16-03-15.
 */
public class Image {
    @SerializedName("id")
    String id;

    @SerializedName("description")
    String description;

    @SerializedName("assets")
    ImageAssets assets;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLargeThumbnail(){
        return assets.largeThumb.url;
    }

    private class ImageAssets{
        @SerializedName("preview")
        Thumbnail preview;

        @SerializedName("small_thumb")
        Thumbnail smallThumb;

        @SerializedName("large_thumb")
        Thumbnail largeThumb;

    }

    private class Thumbnail{
        @SerializedName("url")
        String url;
    }
}
