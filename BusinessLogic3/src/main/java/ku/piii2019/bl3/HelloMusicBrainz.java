/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.util.List;
import java.util.UUID;
import org.musicbrainz.controller.Artist;
import org.musicbrainz.model.entity.ArtistWs2;
import org.musicbrainz.model.searchresult.ArtistResultWs2;


import org.apache.commons.io.FileUtils;

public class HelloMusicBrainz {

    public static void main(String args[]) {
        
        doArtistSearch();
        //getCoverArt();
        
    }

    private static void printArtistResults(List<ArtistResultWs2> results) {
        int index = 0;
        for(ArtistResultWs2 artist : results) {
            ArtistWs2 a = artist.getArtist();
            System.out.println("index " + index++ + ": " + a.getName());
           
        }
    }

    private static void doArtistSearch() {
        Artist artist = new Artist();

        artist.getSearchFilter().setLimit((long) 30);
        artist.getSearchFilter().setMinScore((long) 50);

        artist.search("pink floyd");
        List<ArtistResultWs2> results = artist.getFirstSearchResultPage();
        printArtistResults(results);

        while (artist.hasMore()) {

            results = artist.getNextSearchResultPage();
            printArtistResults(results);
        }
    }

}
