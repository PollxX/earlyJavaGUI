/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author James
 */
public class MediaInfoSourceFromID3Test {
    
    public MediaInfoSourceFromID3Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addMediaInfo method, of class MediaInfoSourceFromID3.
     */
    @Test
    public void testAddMediaInfoID3v1() throws Exception {
        System.out.println("addMediaInfo");
        FileStore fileStore = new FileStoreShortNames();
        Path testPath = Paths.get(Worksheet8TestHelper.TEST_SRC_FOLDER,
                                  fileStore.getRootFolder(), 
                                  "collection-A" + File.separator + "file2.mp3")
                              .toAbsolutePath();
                
        MediaItem itemToTest = new MediaItem().setAbsolutePath(testPath.toString());        
        MediaInfoSourceFromID3 instance = new MediaInfoSourceFromID3();
        instance.addMediaInfo(itemToTest);
        
        MediaItem expectedItem = new MediaItem().setAbsolutePath(testPath.toString());
        expectedItem.setTitle("PERFECT WORLD (AMBIENT)");
        expectedItem.setAlbum("PERFECT WORLD");
        expectedItem.setArtist("DARKPOP BAND ANGELIQUE");
        
        assertEquals("checking album:", expectedItem.getAlbum(), itemToTest.getAlbum());
        assertEquals("checking title:", expectedItem.getTitle(), itemToTest.getTitle());
        assertEquals("checking artist:", expectedItem.getArtist(), itemToTest.getArtist());
        
        
    }
    
    @Ignore
    @Test
    public void testUpdateMediaInfo() throws Exception{
        System.out.println("updatingMediaInfo");   
        
        MediaItem itemToTest = new MediaItem();        
        MediaInfoSourceFromID3 instance = new MediaInfoSourceFromID3();
        
        itemToTest.setAbsolutePath(Paths.get("").toAbsolutePath()+File.separator+"temp.mp3");
        itemToTest.setAlbum("The Preservatives");
        itemToTest.setTitle("Jammin'");
        itemToTest.setArtist("Jamco");
        instance.addMediaInfo(itemToTest);
        
        itemToTest.setPublisher("Jam Corp Ltd");
        instance.updateMediaInfo(itemToTest);
        
        Mp3File mp3 = new Mp3File("test.mp3");
        ID3v2 tag = mp3.getId3v2Tag();
        tag.setPublisher("Jam Corp Ltd");
        tag.setTitle("Jammin");
        tag.setArtist("Jamco");
        mp3.setId3v2Tag(tag);
        mp3.save("temp.mp3");
        
        assertEquals("checking publisher:", mp3.getId3v2Tag().getPublisher(), itemToTest.getPublisher());
        assertEquals("checking artist:", mp3.getId3v2Tag().getArtist(), itemToTest.getArtist());
        assertEquals("checking title:", mp3.getId3v2Tag().getTitle(), itemToTest.getTitle());
        assertEquals("checking album:", mp3.getId3v2Tag().getAlbum(), itemToTest.getAlbum());
    }
    
}
