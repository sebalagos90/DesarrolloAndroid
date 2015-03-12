package cl.sebastian.sounddroid;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.sebastian.sounddroid.soundcloud.SoundCloud;
import cl.sebastian.sounddroid.soundcloud.SoundCloudService;
import cl.sebastian.sounddroid.soundcloud.Track;
import cl.sebastian.sounddroid.soundcloud.TracksAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{
    static final String TAG = "MainActivity";
    List<Track> tracksList;
    TracksAdapter tracksAdapter;
    private ImageView selectedThumbnail;
    private TextView selectedTitle;
    private Toolbar playerToolbar;
    private MediaPlayer mediaPlayer;
    private ImageView playerState;
    private SearchView searchView;
    private SoundCloudService soundCloudService;
    private List<Track> previousTracks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerToolbar = (Toolbar) findViewById(R.id.player_toolbar);
        selectedTitle = (TextView) findViewById(R.id.selected_title);
        selectedThumbnail = (ImageView) findViewById(R.id.selected_thumbnail);
        playerState = (ImageView) findViewById(R.id.player_state);

        playerState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleSongState();

            }
        });


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                toogleSongState();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playerState.setImageResource(R.drawable.ic_play);
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.songsListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tracksList = new ArrayList<>();
        tracksAdapter = new TracksAdapter(tracksList, this);
        recyclerView.setAdapter(tracksAdapter);

        soundCloudService = SoundCloud.getService();
        soundCloudService.getRecentsSongs(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                updateTracks(tracks);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        tracksAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track selectedTrack = tracksList.get(position);
                selectedTitle.setText(selectedTrack.getTitle());
                Picasso.with(MainActivity.this).load(selectedTrack.getAvatarURL()).into(selectedThumbnail);

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                try {
                    mediaPlayer.setDataSource(selectedTrack.getStreamURL()+"?client_id="+SoundCloudService.CLIENT_ID);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void toogleSongState() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            playerState.setImageResource(R.drawable.ic_play);
        }
        else{
            mediaPlayer.start();
            playerState.setImageResource(R.drawable.ic_pause);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search_view), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                previousTracks = new ArrayList<Track>(tracksList);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                updateTracks(previousTracks);
                return true;
            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_view) {
            searchView.setIconifiedByDefault(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        soundCloudService.searchSongs(query, new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                updateTracks(tracks);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    public void updateTracks(List<Track> tracks){
        tracksList.clear();
        tracksList.addAll(tracks);
        tracksAdapter.notifyDataSetChanged();
    }


}
