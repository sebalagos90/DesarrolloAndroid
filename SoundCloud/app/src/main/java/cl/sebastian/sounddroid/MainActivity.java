package cl.sebastian.sounddroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import cl.sebastian.sounddroid.soundcloud.SoundCloud;
import cl.sebastian.sounddroid.soundcloud.SoundCloudService;
import cl.sebastian.sounddroid.soundcloud.Track;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SoundCloudService soundCloudService = SoundCloud.getService();
        soundCloudService.searchSongs("dark horse", new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                Log.d(TAG,"Titles: "+tracks.get(0).getTitle());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
