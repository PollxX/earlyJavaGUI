/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

/**
 *
 * @author ku14009
 */
public class MainApp {
    public static void main(String[] args) {
        System.out.println("hello world!");
        FileService f = new FileServiceImpl();
        final String inputPath =  ".." +  File.separator + 
                                                  "test_folders";
        Set<MediaItem> items = f.getAllMediaItems(inputPath);
        for(MediaItem m : items) {
            System.out.println("item " + Paths.get(m.getAbsolutePath()).getFileName());
        }
        
    }
    
}
