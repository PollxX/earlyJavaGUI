/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static ku.piii2019.bl3.SearchTestHelper.getStandardSet;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author James
 */
public class PlaylistTest {
    @Test
    public void testPlaylistCreation() throws Exception{
        System.out.println("playlist creation");
        ArrayList<MediaItem> arrayList = new ArrayList(getStandardSet());
        
        Playlist itemToTest = new Playlist();
        itemToTest.setTrackList(arrayList);
        itemToTest.saveToFile();
        
        File file1 = new File("test.m3u");
        File file2 = new File(Paths.get("").toAbsolutePath().toString()+File.separator+"tempPlaylist.m3u");
        
        Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
    }
    
    @Test
    public void testHalfPlaylistCreation() throws Exception{
        System.out.println("half playlist creation");
        Set<MediaItem> halfSet = new HashSet();
        halfSet.add(SearchTestHelper.budPowell1());
        halfSet.add(SearchTestHelper.billEvans1());
        
        ArrayList<MediaItem> arrayList = new ArrayList(halfSet);
        
        Playlist itemToTest = new Playlist();
        itemToTest.setTrackList(arrayList);
        itemToTest.saveToFile();
        
        File file1 = new File("test2.m3u");
        File file2 = new File(Paths.get("").toAbsolutePath().toString()+File.separator+"tempPlaylist.m3u");
        
        Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
    }
}