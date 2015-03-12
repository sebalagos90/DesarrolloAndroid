package cl.sebastian.sounddroid.soundcloud;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.sebastian.sounddroid.R;

/**
 * Created by sebalegutie on 12-03-15.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    List<Track> tracksList;
    Context context;
    AdapterView.OnItemClickListener mOnItemClickListener;


    public TracksAdapter(List<Track> tracksList, Context context){
        this.tracksList = tracksList;
        this.context = context;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.track_row,viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Track track = tracksList.get(i);
        viewHolder.titleTextView.setText(track.getTitle());
        Picasso.with(context).load(track.getAvatarURL()).into(viewHolder.thumbImageView);

    }

    @Override
    public int getItemCount() {
        return tracksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView titleTextView;
        public final ImageView thumbImageView;
        ViewHolder(View v){
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.track_title);
            thumbImageView = (ImageView) v.findViewById(R.id.track_thumbnail);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(null,v,getPosition(),0);
            }

        }
    }
}
