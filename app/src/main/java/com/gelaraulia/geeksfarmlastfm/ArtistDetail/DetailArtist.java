
package com.gelaraulia.geeksfarmlastfm.ArtistDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailArtist {

    @SerializedName("artist")
    @Expose
    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}
