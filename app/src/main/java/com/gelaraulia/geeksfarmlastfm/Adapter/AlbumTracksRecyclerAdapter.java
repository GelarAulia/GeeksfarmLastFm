package com.gelaraulia.geeksfarmlastfm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gelaraulia.geeksfarmlastfm.AlbumDetailActivity;
import com.gelaraulia.geeksfarmlastfm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by G_Aulia on 29 Des 2017.
 */

public class AlbumTracksRecyclerAdapter extends RecyclerView.Adapter<AlbumTracksRecyclerAdapter.ViewHolder> {
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrDuration = new ArrayList<>();
    ArrayList<String> arrUrl = new ArrayList<>();
    Context mContext;
    int minute = 0, second = 0;
    String url;
    public AlbumTracksRecyclerAdapter(Context context, ArrayList<String> name, ArrayList<String> duration, ArrayList<String> url){
        arrName = name;
        arrDuration = duration;
        arrUrl = url;
        mContext = context;
    }
    @Override
    public AlbumTracksRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.content_album_tracks, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AlbumTracksRecyclerAdapter.ViewHolder holder, final int position) {
        holder.tv_name.setText(arrName.get(position));
        minute = Integer.parseInt(arrDuration.get(position))/60;
        second = Integer.parseInt(arrDuration.get(position))%60;
        holder.tv_duration.setText(String.valueOf(minute) + ":" + String.valueOf(second));
//        holder.tv_durmin.setText(String.valueOf(minute));
//        holder.tv_dursec.setText(String.valueOf(second));
        holder.ll_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = arrUrl.get(position);
//                if(!url.startsWith("http://") && !url.startsWith("https://")){
//                    url = "http://"+url;
//                }
                Intent browInt = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mContext.startActivity(browInt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_track;
        TextView tv_name;
        TextView tv_duration;
//        TextView tv_durmin, tv_dursec;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView)itemView.findViewById(R.id.tv_trackName);
            tv_duration = (TextView)itemView.findViewById(R.id.tv_trackDuration);
//            tv_durmin = (TextView)itemView.findViewById(R.id.tv_trackDurMin);
//            tv_dursec = (TextView)itemView.findViewById(R.id.tv_trackDurSec);
            ll_track = (LinearLayout)itemView.findViewById(R.id.ll_trackLinLayout);
        }
    }
}
