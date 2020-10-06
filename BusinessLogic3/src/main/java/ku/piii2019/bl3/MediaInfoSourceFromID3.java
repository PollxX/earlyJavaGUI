/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import com.mpatric.mp3agic.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class MediaInfoSourceFromID3 implements MediaInfoSource {

    public void addMediaInfo(MediaItem m) throws Exception {
        Mp3File mp3 = new Mp3File(m.getAbsolutePath());
        ID3v2 tag = mp3.getId3v2Tag();
        if(tag==null){
            throw new Exception();
        }
        try {
            m.setTitle(tag.getTitle());
            m.setAlbum(tag.getAlbum());
            m.setArtist(tag.getArtist());
            m.setGenre(tag.getGenreDescription());
            m.setLength(mp3.getLengthInSeconds());
            m.setBpm(tag.getBPM());
            m.setComposer(tag.getComposer());
            m.setYear(tag.getYear());
            m.setLyrics(tag.getLyrics());
            m.setComment(tag.getComment());
            m.setPublisher(tag.getPublisher());
            m.setRating(tag.getWmpRating());
            
            if (m.getGenre() == null || m.getGenre().equals("")){
                m.setGenre("Other");
            }
            
            updateMediaInfo(m);

        } catch (Exception ex) {
            Logger.getLogger(MediaInfoSourceFromID3.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public void updateMediaInfo(MediaItem m) throws Exception{
        Mp3File mp3 = new Mp3File(m.getAbsolutePath());
        ID3v2 tag = mp3.getId3v2Tag();
        if(tag==null){
            throw new Exception();
        }
        try {
            tag.setTitle(m.getTitle());
            tag.setAlbum(m.getAlbum());
            tag.setArtist(m.getArtist());
            tag.setGenreDescription(m.getGenre());    
            tag.setBPM(m.getBpm());
            tag.setComposer(m.getComposer());
            tag.setYear(m.getYear());
            tag.setLyrics(m.getLyrics());
            tag.setComment(m.getComment());
            tag.setPublisher(m.getPublisher());
            tag.setWmpRating(m.getRating());
        } catch (Exception ex){
            ex.getMessage();
        }
        mp3.setId3v2Tag(tag);
        mp3.save("temp.mp3");
        Path tempPath = Paths.get("temp.mp3");
        Path copying = Paths.get(m.getAbsolutePath());
        Files.copy(tempPath, copying, StandardCopyOption.REPLACE_EXISTING);
    }

}
