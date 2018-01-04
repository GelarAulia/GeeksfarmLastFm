package com.gelaraulia.geeksfarmlastfm.ApiInterface;

import com.gelaraulia.geeksfarmlastfm.AlbumsTop.MainTopAlbums;
import com.gelaraulia.geeksfarmlastfm.ArtistDetail.DetailArtist;
import com.gelaraulia.geeksfarmlastfm.ArtistSearch.SearchArtist;
import com.gelaraulia.geeksfarmlastfm.InfoAlbum.AlbumDetail;

import retrofit.Call;
import retrofit.http.*;

/**
 * Created by G_Aulia on 28 Des 2017.
 */

public interface LastFmApi {
//    artists search
//    http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=cher&api_key=c87fd2e998db2442bcca8ed22c08dbf0&page=2&format=json
    @GET("/2.0/")
    Call<SearchArtist> searchArtist(@Query("method") String method,
                                    @Query("artist") String name,
                                    @Query("api_key") String key,
                                    @Query("page") int page,
                                    @Query("format") String format);
//    artist info
//    http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Cher&api_key=c87fd2e998db2442bcca8ed22c08dbf0&format=json
    @GET("/2.0/")
    Call<DetailArtist> detailArtist(@Query("method") String method,
                                    @Query("artist") String name,
                                    @Query("api_key") String key,
                                    @Query("format") String format);
//    top albums
//    http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=cher&api_key=c87fd2e998db2442bcca8ed22c08dbf0&limit=10&format=json
    @GET("/2.0/")
    Call<MainTopAlbums> topAlbums(@Query("method") String method,
                                  @Query("artist") String name,
                                  @Query("api_key") String key,
                                  @Query("limit") int limit,
                                  @Query("format") String format);
//    album info
//    http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=c87fd2e998db2442bcca8ed22c08dbf0&artist=Cher&album=Believe&format=json
    @GET("/2.0/")
    Call<AlbumDetail> detailAlbum(@Query("method") String method,
                                  @Query("api_key") String key,
                                  @Query("artist") String name,
                                  @Query("album") String album,
                                  @Query("format") String format);

//    artist similar
//    http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=cher&api_key=c87fd2e998db2442bcca8ed22c08dbf0&limit=6&format=json
    @GET("/2.0/")
    Call<DetailArtist> similarArtist(@Query("method") String method,
                                     @Query("artist") String name,
                                     @Query("api_key") String key,
                                     @Query("limit") int limit,
                                     @Query("format") String format);

}
