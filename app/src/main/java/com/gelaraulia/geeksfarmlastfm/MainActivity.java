package com.gelaraulia.geeksfarmlastfm;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gelaraulia.geeksfarmlastfm.Adapter.SearchArtistsRecyclerAdapter;
import com.gelaraulia.geeksfarmlastfm.ApiInterface.LastFmApi;
import com.gelaraulia.geeksfarmlastfm.ArtistSearch.Artist;
import com.gelaraulia.geeksfarmlastfm.ArtistSearch.SearchArtist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    static final String apiKey = "c87fd2e998db2442bcca8ed22c08dbf0";
    static final String format = "json";
    static final String urlBase = "http://ws.audioscrobbler.com";
    static private final String method = "artist.search";

    private EditText et_search;
    private Button btn_search;

    LastFmApi apiBuild;

    ProgressDialog pDial;

    ArrayList<String> arrImage = new ArrayList<>();
    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrUrl = new ArrayList<>();

    RecyclerView rvSearchArtists;
    RecyclerView.Adapter rvaSearchArtists;

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search = (EditText)findViewById(R.id.et_searchArtist);
        btn_search = (Button)findViewById(R.id.btn_searchArtist);

        mContext = MainActivity.this;

        Gson gsonBuild = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ssZ")
                .create();

        Retrofit retroBuild = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create(gsonBuild))
                .build();

        apiBuild = retroBuild.create(LastFmApi.class);

        rvSearchArtists = (RecyclerView)findViewById(R.id.rv_searchArtist);
        rvSearchArtists.setHasFixedSize(true);
        rvSearchArtists.setLayoutManager(new GridLayoutManager(mContext,2));
        rvaSearchArtists = new SearchArtistsRecyclerAdapter(mContext,arrImage,arrName,arrUrl);
        rvSearchArtists.setAdapter(rvaSearchArtists);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDial = new ProgressDialog(MainActivity.this);
                pDial.setMessage("Loading Artists");
                pDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDial.setCancelable(false);
                pDial.show();
                Call<SearchArtist> callSearch = apiBuild.searchArtist(method,et_search.getText().toString(),apiKey,1,format);
                callSearch.enqueue(new Callback<SearchArtist>() {
                    @Override
                    public void onResponse(Response<SearchArtist> response, Retrofit retrofit) {
                        arrImage.clear();
                        arrName.clear();
                        arrUrl.clear();
                        rvaSearchArtists.notifyDataSetChanged();

                        for(Artist detail : response.body().getResults().getArtistmatches().getArtist()){
                            arrImage.add(detail.getImage().get(4).getText());
                            arrName.add(detail.getName());
                            arrUrl.add(detail.getUrl());
                        }
                        rvaSearchArtists = new SearchArtistsRecyclerAdapter(mContext,arrImage,arrName,arrUrl);
                        rvSearchArtists.setAdapter(rvaSearchArtists);
                        pDial.dismiss();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(mContext,"Couldn't get Artists",Toast.LENGTH_LONG).show();
                        pDial.dismiss();
                    }
                });
            }
        });
    }
}
