package com.gelaraulia.geeksfarmlastfm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gelaraulia.geeksfarmlastfm.ArtistDetailActivity;
import com.gelaraulia.geeksfarmlastfm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by G_Aulia on 29 Des 2017.
 */

public class SearchArtistsRecyclerAdapter extends RecyclerView.Adapter<SearchArtistsRecyclerAdapter.ViewHolder> {
    ArrayList<String> arrImage = new ArrayList<>();
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrUrl = new ArrayList<>();
    Context mContext;
    Intent mIntent;
    public SearchArtistsRecyclerAdapter(Context context, ArrayList<String> image, ArrayList<String> name, ArrayList<String> url){
        arrImage = image;
        arrName = name;
        arrUrl = url;
        mContext = context;
    }
    @Override
    public SearchArtistsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.content_search_artists, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SearchArtistsRecyclerAdapter.ViewHolder holder, final int position) {
//        Glide.with(mContext).load(arrImage.get(position)).into(holder.iv_imageHolder);
        if(arrImage.get(position) != ""){
            Picasso.with(mContext).load(arrImage.get(position)).into(holder.iv_imageHolder);
            holder.tv_artistNull.setText("");
        }else{
            Picasso.with(mContext).load("@drawable/ic_launcher_background").into(holder.iv_imageHolder);
            holder.tv_artistNull.setText("Artist not available");
        }
        if(arrName.get(position) != ""){
            holder.tv_nameHolder.setText(arrName.get(position));
        }else{
            holder.tv_nameHolder.setText("Artist not available");
        }

        holder.iv_imageHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(mContext, ArtistDetailActivity.class);
                mIntent.putExtra("name",arrName.get(position));
                mIntent.putExtra("image",arrImage.get(position));
                mIntent.putExtra("url",arrUrl.get(position));
                mContext.startActivity(mIntent);
            }
        });
        holder.tv_nameHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(mContext, ArtistDetailActivity.class);
                mIntent.putExtra("name",arrName.get(position));
                mIntent.putExtra("image",arrImage.get(position));
                mIntent.putExtra("url",arrUrl.get(position));
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_imageHolder;
        TextView tv_nameHolder, tv_artistNull;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_imageHolder = (ImageView)itemView.findViewById(R.id.iv_searchArtist);
            tv_nameHolder = (TextView)itemView.findViewById(R.id.tv_searchArtist);
            tv_artistNull = (TextView)itemView.findViewById(R.id.tv_searchNull);
        }
    }
}
