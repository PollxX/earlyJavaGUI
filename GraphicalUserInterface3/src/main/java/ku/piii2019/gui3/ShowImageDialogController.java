/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.gui3;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ShowImageDialogController  {
    
    int currentIndex;
    
    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAge;
    
    @FXML
    private ImageView imageView1;
    
    private ObservableList<Image> appMainObservableList;

    @FXML
    void previousImage(ActionEvent event) {
        currentIndex -=1;
        currentIndex %= appMainObservableList.size();
        imageView1.setImage(appMainObservableList.get(currentIndex));
        System.out.println("previousImage Clicked - currentIndex is " + currentIndex);
        
     }
    @FXML
    void nextImage(ActionEvent event) {
        currentIndex +=1;
        currentIndex %= appMainObservableList.size();
        imageView1.setImage(appMainObservableList.get(currentIndex));
        System.out.println("nextImage Clicked - currentIndex is " + currentIndex);
    }

    public void setAppMainObservableList(ObservableList<Image> tvObservableList) {
        this.appMainObservableList = tvObservableList;
        imageView1.setImage(appMainObservableList.get(currentIndex));
        
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
