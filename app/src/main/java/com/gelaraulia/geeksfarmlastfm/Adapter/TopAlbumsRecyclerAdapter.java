package com.gelaraulia.geeksfarmlastfm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gelaraulia.geeksfarmlastfm.AlbumDetailActivity;
import com.gelaraulia.geeksfarmlastfm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by G_Aulia on 29 Des 2017.
 */

public class TopAlbumsRecyclerAdapter extends RecyclerView.Adapter<TopAlbumsRecyclerAdapter.ViewHolder> {
    ArrayList<String> arrImage = new ArrayList<>();
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrArtist = new ArrayList<>();
    Context mContext;
    Intent mIntent;
    public TopAlbumsRecyclerAdapter(Context context, ArrayList<String> image, ArrayList<String> name, ArrayList<String> artist){
        arrImage = image;
        arrName = name;
        mContext = context;
        arrArtist = artist;
    }
    @Override
    public TopAlbumsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.content_top_albums, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final TopAlbumsRecyclerAdapter.ViewHolder holder, final int position) {
//        Glide.with(mContext).load(arrImage.get(position)).into(holder.iv_imageHolder);
        if(arrImage.get(position) != ""){
            Picasso.with(mContext).load(arrImage.get(position)).into(holder.iv_topAlbums);
        }else{
            Picasso.with(mContext).load("@drawable/ic_launcher_background").into(holder.iv_topAlbums);
        }

        holder.iv_topAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(mContext, AlbumDetailActivity.class);
                mIntent.putExtra("name",arrName.get(position));
                mIntent.putExtra("image",arrImage.get(position));
                mIntent.putExtra("artist",arrArtist.get(position));
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_topAlbums;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_topAlbums = (ImageView)itemView.findViewById(R.id.iv_topAlbums);
        }
    }
}
