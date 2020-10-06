/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

/**
 *
 * @author James
 */
public class MediaItem {

    private String absolutePath;
    private String title;
    private String album;
    private String artist;
    private String genre;
    private String composer;
    private String year;
    private String lyrics;
    private String comment;
    private String publisher;
    private long length;
    private int bpm;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }
            
    public String getAbsolutePath() {
        return absolutePath;
    }

    public MediaItem setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.absolutePath.equals(((MediaItem) obj).getAbsolutePath());
    }
    @Override
    public int hashCode() {
        return this.absolutePath.hashCode();
    }

    public MediaItem  setTitle(String title) {
        this.title = title;
        return this;
    }

    public MediaItem  setAlbum(String album) {
        this.album = album;
        return this;
    }

    public MediaItem setArtist(String artist) {
        this.artist = artist;
        return this;
    }
    
}
