package com.gelaraulia.geeksfarmlastfm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gelaraulia.geeksfarmlastfm.Adapter.AlbumTracksRecyclerAdapter;
import com.gelaraulia.geeksfarmlastfm.ApiInterface.LastFmApi;
import com.gelaraulia.geeksfarmlastfm.InfoAlbum.AlbumDetail;
import com.gelaraulia.geeksfarmlastfm.InfoAlbum.Track;
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

import static com.gelaraulia.geeksfarmlastfm.MainActivity.apiKey;
import static com.gelaraulia.geeksfarmlastfm.MainActivity.format;
import static com.gelaraulia.geeksfarmlastfm.MainActivity.urlBase;

public class AlbumDetailActivity extends AppCompatActivity {
    Intent intent;
    String name, image, artist, url = "";

    ImageView iv_album;
    TextView tv_name,tv_artist,tv_listen,tv_count;

    RecyclerView rv_tracks;
    RecyclerView.Adapter rva_tracks;

    Context mContext;

    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrDuration = new ArrayList<>();
    ArrayList<String> arrUrl = new ArrayList<>();

    LastFmApi apiBuild;

    String method = "album.getinfo";
    ProgressDialog pDial;
    AlertDialog aDial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        intent = getIntent();
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
        artist = intent.getStringExtra("artist");

        mContext = AlbumDetailActivity.this;

        iv_album = (ImageView)findViewById(R.id.iv_detailAlbum);

        tv_name = (TextView)findViewById(R.id.tv_dAlbumName);
        tv_artist = (TextView)findViewById(R.id.tv_dAlbumArtist);
        tv_listen = (TextView)findViewById(R.id.tv_dAlbumListen);
        tv_count = (TextView)findViewById(R.id.tv_dAlbumCount);

        rv_tracks = (RecyclerView)findViewById(R.id.rv_dAlbumTracks);
        rv_tracks.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rva_tracks = new AlbumTracksRecyclerAdapter(mContext,arrName,arrDuration,arrUrl);
        rv_tracks.setAdapter(rva_tracks);

        Gson gsonBuild = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ssZ")
                .create();

        Retrofit retroBuild = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create(gsonBuild))
                .build();

        apiBuild = retroBuild.create(LastFmApi.class);

        Call<AlbumDetail> call = apiBuild.detailAlbum(method,apiKey,artist,name,format);
        pDial = new ProgressDialog(mContext);
        pDial.setMessage("Loading Artists");
        pDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDial.setCancelable(false);
        pDial.show();
        call.enqueue(new Callback<AlbumDetail>() {
            @Override
            public void onResponse(Response<AlbumDetail> response, Retrofit retrofit) {
                if(image != null){
                    Picasso.with(mContext).load(image).into(iv_album);
                }else{
                    Picasso.with(mContext).load("@drawable/ic_launcher_background").into(iv_album);
                }
                Picasso.with(mContext).load(image).into(iv_album);
                tv_name.setText(name);
                tv_artist.setText(artist);
//                tv_listen.setText(response.body().getAlbum().getListeners());
                NumberFormat nf = NumberFormat.getInstance();
                String numFormated = nf.format(Integer.parseInt(response.body().getAlbum().getListeners()));
                tv_listen.setText(numFormated);

//                tv_count.setText(response.body().getAlbum().getPlaycount());
                numFormated = nf.format(Integer.parseInt(response.body().getAlbum().getPlaycount()));
                tv_count.setText(numFormated);

                for(Track track : response.body().getAlbum().getTracks().getTrack()){
                    arrName.add(track.getName());
                    arrDuration.add(track.getDuration());
                    arrUrl.add(track.getUrl());
                }
                url = response.body().getAlbum().getUrl();
                rva_tracks = new AlbumTracksRecyclerAdapter(mContext,arrName,arrDuration,arrUrl);
                rv_tracks.setAdapter(rva_tracks);
                pDial.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext, "COULDN'T SHOW TRACKS", Toast.LENGTH_LONG).show();
                tv_name.setText("Data not available");
                tv_artist.setText("Data not available");
                tv_listen.setText("Data not available");
                tv_count.setText("Data not available");
                pDial.dismiss();
            }
        });
        iv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browInt = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browInt);
            }
        });
    }
}
