/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author James
 */
public class Playlist {
    
    private ArrayList<MediaItem> trackList = new ArrayList();
    private String title;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<MediaItem> getTrackList() {
        return trackList;
    }

    public void setTrackList(ArrayList<MediaItem> trackList) {
        this.trackList = trackList;
    }
    
    public void newPlaylist(String path, String title, ArrayList<MediaItem> songsToAdd) throws FileNotFoundException{
        try (PrintWriter pWriter = new PrintWriter(new FileOutputStream(path))) {
            pWriter.println("#EXTM3U");
            for (MediaItem m : songsToAdd){
                pWriter.println("#EXTINF:"+m.getLength()+","+m.getArtist()+" - "+m.getTitle());
                pWriter.println("file:///"+m.getAbsolutePath());
            }
        }
    }
    
    public void addSong(MediaItem m){
        this.getTrackList().add(m);
    }
    
    public void removeSong(MediaItem m){
        if (this.getTrackList().contains(m)){
            this.getTrackList().remove(m);
        }
    }
    
    public void saveToFile() throws IOException{
        //this would go to something like projectfolder...\Assignment\GraphicalUserInterface3\tempPlaylist.m3u
        Path path = Paths.get(Paths.get("").toAbsolutePath().toString()+File.separator+"tempPlaylist.m3u");
        newPlaylist(path.toString(),"tempPlaylist",this.getTrackList());
    }
    
    public void saveToNewFolder() throws IOException{
        //this would go to something like C:\Users\USERNAME\JMusic\testPlaylist.m3u
        Path path = Paths.get(System.getProperty("user.home")+File.separator+"JMusic"+File.separator+"testPlaylist.m3u");
        try{
            copyFromPlaylist();
            updateTracklist();
            newPlaylist(path.toString(),"testPlaylist",this.getTrackList());
        } catch (IndexOutOfBoundsException e){
            System.out.println("IndexOutOfBoundsException from saveToNewFolder method");
        }
    }
    
    public void updateTracklist(){
        for (MediaItem m : this.getTrackList()){
            ArrayList<String> strings = new ArrayList();
            for (String out: m.getAbsolutePath().split("\\\\")){
                strings.add(out);
            }
            String str = strings.get(strings.size()-1);
            String path = System.getProperty("user.home")+File.separator+"JMusic"+File.separator+str;
            m.setAbsolutePath(path);
        }
    }
    
    public void copyFromPlaylist() throws IOException{
        Path newP = Paths.get(System.getProperty("user.home")+File.separator+"JMusic");
        for (MediaItem m : this.getTrackList()){
            Path oldP = Paths.get(m.getAbsolutePath());
            Path tempP = Paths.get(newP+File.separator+m.getTitle().trim()+".mp3");
            try{
                Files.copy(oldP, tempP);
            } catch (IOException FileAlreadyExistsException){
                System.out.println("File already exists: "+m.getTitle().trim()+".mp3"); 
            }
        }
    }
}
