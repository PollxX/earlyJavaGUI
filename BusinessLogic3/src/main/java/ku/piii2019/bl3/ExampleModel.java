/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ku.piii2019.bl3;

import java.io.File;
import java.util.Set;

/**
 *
 * @author ku14009
 */
class ExampleModel {
    
    Set<MediaItem> firstCollection;
    Set<MediaItem> secondCollection;
    
    public ExampleModel()
    {
        firstCollection = null;
        secondCollection = null;
    }

    boolean haveFirstCollection() {
        
        if(firstCollection==null)
            return false;
        else
            return true;
        
    }

    boolean haveSecondCollection() {
        if(secondCollection==null)
            return false;
        else
            return true;
    }

    void clearFirstCollection() {
        firstCollection = null;
    }

    void loadFirstCollection(String rootFolderOfCollection) {
        
        FileService f = new FileServiceImpl();
        firstCollection = f.getAllMediaItems(rootFolderOfCollection);
        
    }

    void saveFirstCollection(String filename) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Set<MediaItem> getFirstCollection() {
        return firstCollection;
    }

    void loadSecondCollection(String rootFolderOfCollection) {
        FileService f = new FileServiceImpl();
        secondCollection = f.getAllMediaItems(rootFolderOfCollection);
    }

    void saveSecondCollection(String filename) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void clearSecondCollection() {
        secondCollection = null;
    }

    public Set<MediaItem> getSecondCollection() {
        return secondCollection;
    }
            

}
