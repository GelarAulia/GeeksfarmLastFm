package com.gelaraulia.geeksfarmlastfm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gelaraulia.geeksfarmlastfm.Adapter.SearchArtistsRecyclerAdapter;
import com.gelaraulia.geeksfarmlastfm.Adapter.TopAlbumsRecyclerAdapter;
import com.gelaraulia.geeksfarmlastfm.AlbumsTop.Album;
import com.gelaraulia.geeksfarmlastfm.AlbumsTop.MainTopAlbums;
import com.gelaraulia.geeksfarmlastfm.ApiInterface.LastFmApi;
import com.gelaraulia.geeksfarmlastfm.ArtistDetail.Artist_;
import com.gelaraulia.geeksfarmlastfm.ArtistDetail.DetailArtist;
import com.gelaraulia.geeksfarmlastfm.ArtistDetail.Similar;
import com.gelaraulia.geeksfarmlastfm.ArtistDetail.Tag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.gelaraulia.geeksfarmlastfm.MainActivity.urlBase;
import static com.gelaraulia.geeksfarmlastfm.MainActivity.apiKey;
import static com.gelaraulia.geeksfarmlastfm.MainActivity.format;

public class ArtistDetailActivity extends AppCompatActivity {
    private String name, image, url;
    Intent mIntent;
    LastFmApi apiBuild;
    Context mContext;
    static private final String method = "artist.getinfo";
    static private final String methodAlbum = "artist.gettopalbums";

    ImageView iv_image;
    TextView tv_name,tv_listener,tv_url,tv_bio,tv_tag;

    RecyclerView rv_simArt;
    RecyclerView rv_topAlb;

    RecyclerView.Adapter rva_simArt;

    ArrayList<String> arrImage = new ArrayList<>();
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrUrl = new ArrayList<>();

    ProgressDialog pDial;

    RecyclerView.Adapter rva_topAlb;

    ArrayList<String> arrImageAlb = new ArrayList<>();
    ArrayList<String> arrNameAlb = new ArrayList<>();
    ArrayList<String> arrArtistAlbum = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);
        mIntent = getIntent();
        name = mIntent.getStringExtra("name");
        image = mIntent.getStringExtra("image");
        url = mIntent.getStringExtra("url");
        mContext = ArtistDetailActivity.this;
        iv_image = (ImageView)findViewById(R.id.iv_detailArtist);

        tv_name = (TextView)findViewById(R.id.tv_dArtistName);
        tv_listener = (TextView)findViewById(R.id.tv_dArtistListener);
        tv_url = (TextView)findViewById(R.id.tv_dArtistUrl);
        tv_bio = (TextView)findViewById(R.id.tv_dArtistBio);
        tv_tag = (TextView)findViewById(R.id.tv_dArtistTag);

        rv_simArt = (RecyclerView)findViewById(R.id.rv_dArtistSimilar);
        rv_topAlb = (RecyclerView)findViewById(R.id.rv_dArtistAlbums);

        rv_simArt.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        rva_simArt = new SearchArtistsRecyclerAdapter(mContext, arrImage,arrName,arrUrl);
        rv_simArt.setAdapter(rva_simArt);


        Gson gsonBuild = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ssZ")
                .create();

        Retrofit retroBuild = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create(gsonBuild))
                .build();

        apiBuild = retroBuild.create(LastFmApi.class);
        Call<DetailArtist> call = apiBuild.detailArtist(method,name,apiKey,format);
        pDial = new ProgressDialog(mContext);
        pDial.setMessage("Loading Artists");
        pDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDial.setCancelable(false);
        pDial.show();
        call.enqueue(new Callback<DetailArtist>() {
            @Override
            public void onResponse(Response<DetailArtist> response, Retrofit retrofit) {
                if(image != null){
                    Picasso.with(mContext).load(image).into(iv_image);
                }else{
                    Picasso.with(mContext).load("@drawable/ic_launcher_background").into(iv_image);
                }
                tv_name.setText(response.body().getArtist().getName());
//                tv_listener.setText(response.body().getArtist().getStats().getListeners());
                NumberFormat nf = NumberFormat.getInstance();
                String numFormated = nf.format(Integer.parseInt(response.body().getArtist().getStats().getListeners()));
                tv_listener.setText(numFormated);

                tv_url.setText(response.body().getArtist().getUrl());
                tv_bio.setText(response.body().getArtist().getBio().getContent());
                tv_tag.setText("");
                for(Tag tag : response.body().getArtist().getTags().getTag()){
                    tv_tag.append(tag.getName()+", ");
                }
                for (Artist_ artSim : response.body().getArtist().getSimilar().getArtist()){
                    arrImage.add(artSim.getImage().get(4).getText());
                    arrName.add(artSim.getName());
                    arrUrl.add(artSim.getUrl());

                    rva_simArt = new SearchArtistsRecyclerAdapter(mContext, arrImage,arrName,arrUrl);
                    rv_simArt.setAdapter(rva_simArt);
                }
                pDial.dismiss();
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ArtistDetailActivity.this, "COULDN'T SHOW ARTIST INFO", Toast.LENGTH_LONG).show();
                tv_name.setText("Data not available");
                tv_listener.setText("Data not available");
//                tv_url.setText("Data not available");
                tv_bio.setText("Data not available");
                tv_tag.setText("Data not available");
                pDial.dismiss();
            }
        });
        Call<MainTopAlbums> callAlbum = apiBuild.topAlbums(methodAlbum,name,apiKey,6,format);
        rv_topAlb.setHasFixedSize(true);
        rv_topAlb.setLayoutManager(new GridLayoutManager(mContext,3));
        rva_topAlb = new TopAlbumsRecyclerAdapter(mContext, arrImageAlb,arrNameAlb,arrArtistAlbum);
        rv_topAlb.setAdapter(rva_topAlb);
        callAlbum.enqueue(new Callback<MainTopAlbums>() {
            @Override
            public void onResponse(Response<MainTopAlbums> response, Retrofit retrofit) {
                for (Album album : response.body().getTopalbums().getAlbum()){
                    arrImageAlb.add(album.getImage().get(3).getText());
                    arrNameAlb.add(album.getName());
                    arrArtistAlbum.add(album.getArtist().getName());

                    rva_topAlb = new TopAlbumsRecyclerAdapter(mContext, arrImageAlb,arrNameAlb,arrArtistAlbum);
                    rv_topAlb.setAdapter(rva_topAlb);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ArtistDetailActivity.this, "COULDN'T SHOW TOP ALBUMS", Toast.LENGTH_LONG).show();
            }
        });
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!url.startsWith("http://") && !url.startsWith("https://")){
//                    url = "http://"+url;
//                }
                Intent browInt = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browInt);
            }
        });
        tv_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browInt = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browInt);
            }
        });
    }
}
