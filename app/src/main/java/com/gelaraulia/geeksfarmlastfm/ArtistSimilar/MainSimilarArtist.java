
package com.gelaraulia.geeksfarmlastfm.ArtistSimilar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainSimilarArtist {

    @SerializedName("similarartists")
    @Expose
    private Similarartists similarartists;

    public Similarartists getSimilarartists() {
        return similarartists;
    }

    public void setSimilarartists(Similarartists similarartists) {
        this.similarartists = similarartists;
    }

}
