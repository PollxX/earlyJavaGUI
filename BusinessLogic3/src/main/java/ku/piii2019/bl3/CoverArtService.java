/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtArchiveClient;
import fm.last.musicbrainz.coverart.CoverArtImage;
import fm.last.musicbrainz.coverart.impl.DefaultCoverArtArchiveClient;
import java.util.UUID; 
import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;

public interface CoverArtService {

    public CoverArtImage  getImage(String artist, String album);
    public List<CoverArtImage>  getImages(String artist, String album);
    public CoverArtImage  getImage(MediaItem m);  
    public void addImages(MediaItem m);
    
    
/*    public static void main(String args[]) {
        CoverArtArchiveClient client = new DefaultCoverArtArchiveClient();
        UUID mbid = UUID.fromString("76df3287-6cda-33eb-8e9a-044b5e15ffdd");

        CoverArt coverArt = null;
        try {
            coverArt = client.getByMbid(mbid);
            if (coverArt != null) {
                for (CoverArtImage coverArtImage : coverArt.getImages()) {
                    File output = new File(mbid.toString() + "_" + coverArtImage.getId() + ".jpg");
                    FileUtils.copyInputStreamToFile(coverArtImage.getImage(), output);
                    System.out.println("printing file to " + output.getName());
                }
            }
        } catch (Exception e) {
            // ...
        }
    }*/

}
