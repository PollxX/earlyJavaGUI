package ku.piii2019.gui3;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.stage.Modality;
import ku.piii2019.bl3.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

public class FXMLController implements Initializable {
    
    private static final Logger logger = Logger.getLogger(FXMLController.class.getName());
    List<MediaItem> copier = new ArrayList();

    @FXML
    private Label label;
    @FXML
    private TableView<MediaItem> tableView1;
    @FXML
    private TableView<MediaItem> tableView2;
    @FXML
    private TableView<MediaItem> tableView3;

    String collectionRootAB = "test_folders" + File.separator
            + "original_filenames";
    String collectionRootA = collectionRootAB + File.separator
            + "collection-A";
    String collectionRootB = collectionRootAB + File.separator
            + "collection-B";

    String imageCollection = "test_folders" + File.separator
            + "test_images";

    ObservableList<Image> observableListOfImages;

    @FXML
    void showImageDialog(ActionEvent event) throws IOException {
        System.out.println("about to load fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowImageDialog.fxml"));
        System.out.println("made loader...");
        Parent parent = fxmlLoader.load();
        System.out.println("done");
        ShowImageDialogController dialogController = fxmlLoader.<ShowImageDialogController>getController();

        observableListOfImages = FXCollections.observableArrayList();

        fillListWithTestImages(observableListOfImages, imageCollection);

        dialogController.setAppMainObservableList(observableListOfImages);

        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void handleButton1Action(ActionEvent event) {
        tableView1.getItems().clear();
        tableView2.getItems().clear();
        tableView3.getItems().clear();
    }

    @FXML
    private void delete2(ActionEvent event) {
        System.out.println("You clicked me!");
        List<MediaItem> itemsToDelete = tableView2.getSelectionModel().getSelectedItems();
        tableView2.getItems().removeAll(itemsToDelete);

    }

    @FXML
    private void handleButton2Action(ActionEvent event) {
        tableView1.getSelectionModel().clearSelection();
        tableView2.getSelectionModel().clearSelection();
        tableView3.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleKeyPressed2(KeyEvent event) {
        System.out.println("You clicked me!");
    }
    
    @FXML
    private void folderSetup(ActionEvent event){
        try {
            new File(System.getProperty("user.home")+File.separator+"JMusic").mkdir();
            ObservableList<MediaItem> toBeSaved = tableView3.getItems();
            Playlist tempPlaylist = new Playlist();
            
            for (MediaItem m : toBeSaved){
                tempPlaylist.addSong(m);
            }
            
            tempPlaylist.saveToNewFolder();
            tableView3.setItems(FXCollections.observableArrayList(tempPlaylist.getTrackList()));
            
        } catch(Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
    }
    
    int genreCounter = 0;
    @FXML
    private void selectGenre(ActionEvent event){
        //click will select all of genre (alphabetical), then keep cycling through them
        tableView1.getSelectionModel().clearSelection();
        ObservableList<MediaItem> tableItems = tableView1.getItems();
        ArrayList<String> foundGenres = new ArrayList();
        for (MediaItem m : tableItems){
            if (!foundGenres.contains(m.getGenre())){
                foundGenres.add(m.getGenre());
            }
        }
        for (MediaItem m : tableItems){
            if (m.getGenre().equals(foundGenres.get(genreCounter))){
                tableView1.getSelectionModel().select(m);
            }
        }
        genreCounter++;
        if (genreCounter > foundGenres.size()-1){
            genreCounter = 0;
        }
    }
    
    int artistCounter = 0;
    @FXML
    private void selectArtist(ActionEvent event){
        //click will select all of artist (alphabetical), then keep cycling through them
        tableView1.getSelectionModel().clearSelection();
        ObservableList<MediaItem> tableItems = tableView1.getItems();
        ArrayList<String> foundArtists = new ArrayList();
        for (MediaItem m : tableItems){
            if (!foundArtists.contains(m.getArtist())){
                foundArtists.add(m.getArtist());
            }
        }
        for (MediaItem m : tableItems){
            if (m.getArtist().equals(foundArtists.get(artistCounter))){
                tableView1.getSelectionModel().select(m);
            }
        }
        artistCounter++;
        if (artistCounter > foundArtists.size()-1){
            artistCounter = 0;
        }
    }
    
    @FXML
    private void copy(ActionEvent event){
        ObservableList<MediaItem> m1 = tableView1.getSelectionModel().getSelectedItems();
        ObservableList<MediaItem> m2 = tableView2.getSelectionModel().getSelectedItems();
        
        if (tableView1.getSelectionModel() != null){
            for (MediaItem m : m1){
                if (!copier.contains(m)){
                    copier.add(m);
                }
            }
        }
        if (tableView2.getSelectionModel() != null){
            for (MediaItem m : m2){
                if (!copier.contains(m)){
                    copier.add(m);
                }
            }
        }
    }
    
    @FXML
    private void addAllSelected(ActionEvent event){
        copy(event);
        paster(event);
    }
    
    @FXML
    private void paster(ActionEvent event){
        paste(copier);
        copier.clear();
    }
    
    @FXML
    private void paste(List<MediaItem> copiedMediaItems){
        List<MediaItem> itemsToPaste = tableView3.getItems();
        for (MediaItem m : copiedMediaItems){
            if (!itemsToPaste.contains(m)){
                itemsToPaste.add(m);
            }
        }
        ObservableList<MediaItem> newList
                = FXCollections.observableArrayList(itemsToPaste);
        tableView3.setItems(newList);
    }
    
    @FXML
    private void cut(ActionEvent event){
        ObservableList<MediaItem> selection = tableView3.getSelectionModel().getSelectedItems();
        ObservableList<MediaItem> table3 = tableView3.getItems();
        
        if (tableView3.getSelectionModel() != null){
            for (MediaItem m : selection){
                if (!copier.contains(m)){
                    copier.add(m);
                    
                }
            }
            for (MediaItem rm : copier){
                table3.remove(rm);
            }
        }
    }
    
    
    public void savePlaylist() throws IOException{
        ObservableList<MediaItem> toBeSaved = tableView3.getItems();
        Playlist tempPlaylist = new Playlist();
        for (MediaItem m : toBeSaved){
            tempPlaylist.addSong(m);
        }
        tempPlaylist.saveToFile();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<MediaItemColumnInfo> columns = MediaItemTableViewFactory.makeColumnInfoList();
        MediaItemTableViewFactory.makeTable(tableView1, columns);
        MediaItemTableViewFactory.makeTable(tableView2, columns);
        MediaItemTableViewFactory.makeTable(tableView3, columns);
        
        logger.setLevel(Level.FINE);

        // you won't need any of this selection or drag code for the Nov 20th 
        // mock test, but I thought I'd keep it in for convenience
        addDragListener(tableView1);
        addDragListener(tableView2);
        addDragListener(tableView3);
        tableView1.getSelectionModel().setCellSelectionEnabled(false);
        tableView1.setEditable(true);
        tableView2.setEditable(true);
        tableView3.setEditable(true);
        tableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView3.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    @FXML
    private void editMetaData(ActionEvent event) throws Exception{
        ObservableList<MediaItem> selection = null;
        TableView<MediaItem> tv = null;
        if (tableView1.isFocused()){
            selection = tableView1.getSelectionModel().getSelectedItems();
            tv = tableView1;
        } else if (tableView2.isFocused()){
            selection = tableView2.getSelectionModel().getSelectedItems();
            tv = tableView2;
        } else if (tableView3.isFocused()){
            selection = tableView3.getSelectionModel().getSelectedItems();
            tv = tableView3;
        }
        MediaItem selectedItem = selection.get(0);
        editMultiMeta(tv, selectedItem);
    }
    
    @FXML
    private void editMultiMeta(TableView tableView, MediaItem mItem) throws Exception{
        Dialog<Editor> editingDialog = new Dialog();
        editingDialog.setHeaderText("Editing MediaItem");
        editingDialog.setTitle("Editor");
        
        DialogPane dPane = editingDialog.getDialogPane();
        dPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        Label composer = new Label("Composer");
        Label year = new Label("Year");
        Label bpm = new Label("Bpm");
        Label publisher = new Label("Publisher");
        Label rating = new Label("Rating\n Star rating of 0 to 3, -1 means it is not set");
        Label lyrics = new Label("Lyrics");
        Label comment = new Label("Comment");
                        
        TextField textComp = new TextField(mItem.getComposer());
        TextField textYear = new TextField(mItem.getYear());
        TextField textBpm = new TextField(Integer.toString(mItem.getBpm()));
        TextField textPublisher = new TextField(mItem.getPublisher());
        TextField textRating = new TextField(Integer.toString(mItem.getRating()));
        TextArea textLyrics = new TextArea(mItem.getLyrics());
        TextArea textComment = new TextArea(mItem.getComment());
        
        dPane.setContent(new VBox(8, composer, textComp, year, textYear, bpm, textBpm, publisher, textPublisher, rating, textRating, lyrics, textLyrics, comment, textComment));
        
        Platform.runLater(textComp::requestFocus);
        Platform.runLater(textYear::requestFocus);
        Platform.runLater(textBpm::requestFocus);
        Platform.runLater(textPublisher::requestFocus);
        Platform.runLater(textRating::requestFocus);
        Platform.runLater(textLyrics::requestFocus);
        Platform.runLater(textComment::requestFocus);
        
        editingDialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK){
                return new Editor(textComp.getText(), textYear.getText(), textPublisher.getText(), Integer.parseInt(textRating.getText()), textLyrics.getText(), textComment.getText(), Integer.parseInt(textBpm.getText()));
            }
            return null;
        });
        
        Optional<Editor> result = editingDialog.showAndWait();
        if (result.isPresent()){
            Editor resultEditor = result.get();
            mItem.setBpm(resultEditor.getBpm());
            mItem.setComposer(resultEditor.getComposer());
            mItem.setYear(resultEditor.getYear());
            mItem.setLyrics(resultEditor.getLyrics());
            mItem.setComment(resultEditor.getComment());
            mItem.setPublisher(resultEditor.getPublisher());
            int tempRating = resultEditor.getRating();
            // due to the constraints of the story, only star values of 0,1,2,3 are accepted (although wmpRating has potential to reach up to 5...)
            if (tempRating < 0 || tempRating > 3){
                tempRating = -1;
            }
            mItem.setRating(tempRating);
        }
        MediaInfoSourceFromID3 myInfoSource = new MediaInfoSourceFromID3();
        myInfoSource.updateMediaInfo(mItem);
        tableView.refresh();
        
    }
    
    @FXML
    private void logFileName(){
        FileHandler fh;
        TextInputDialog tid = new TextInputDialog("");
        tid.setTitle("Input filename");
        tid.setHeaderText("Set filename for location");
        Optional<String> op = tid.showAndWait();
        try {
            //this would go to something like C:\Users\USERNAME\testing.log
            String path = System.getProperty("user.home")+File.separator+op.get()+".log";
            System.out.println(path);
            fh = new FileHandler(path);
            logger.addHandler(fh);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            logger.log(Level.FINE, "Filename chosen as {0}", op.get());
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void loggerSevere(){
        logger.setLevel(Level.SEVERE);
        logger.severe("Severe level of logging chosen...");
    }
    
    @FXML
    private void loggerFine(){
        logger.setLevel(Level.FINE);
        logger.fine("Fine level of logging chosen...");
    }

    @FXML
    private void openABIn2(ActionEvent event) {
        open(2, collectionRootAB);
    }

    @FXML
    private void insertTable1In2(ActionEvent event) {

        ObservableList<MediaItem> table1Data
                = tableView1.getItems();
        ObservableList<MediaItem> table2Data
                = tableView2.getItems();
        table2Data.addAll(0, table1Data);

    }

    @FXML
    private void insertTable2In1(ActionEvent event) {

        ObservableList<MediaItem> table1Data
                = tableView1.getItems();
        ObservableList<MediaItem> table2Data
                = tableView2.getItems();
        table1Data.addAll(0, table2Data);

    }

    @FXML
    private void swap(ActionEvent event) {

        Scene scene = tableView1.getScene();
        TableView<MediaItem> tableInFocus = null;
        if (scene.focusOwnerProperty().get() instanceof TableView) {
            tableInFocus = (TableView) scene.focusOwnerProperty().get();
            MediaItem m = tableInFocus.getSelectionModel().getSelectedItem();
            System.out.println("m " + m.getAlbum());
        }
    }

    @FXML
    private void showMissingItems(ActionEvent event) {
        Set<MediaItem> table1Data = new HashSet(tableView1.getItems());
        Set<MediaItem> table2Data = new HashSet(tableView2.getItems());

        DuplicateFinder d = new DuplicateFindFromID3();
        Set<MediaItem> missingItems = d.getMissingItems(table1Data, table2Data);

        ObservableList<MediaItem> dataForTableViewAndModel
                = FXCollections.observableArrayList(missingItems);
        tableView3.setItems(dataForTableViewAndModel);

    }

    @FXML
    private void openIn2(ActionEvent event) {
        open(2, null);
    }

    @FXML
    private void openAIn2(ActionEvent event) {
        open(2, collectionRootA);
    }

    @FXML
    private void openBIn2(ActionEvent event) {
        open(2, collectionRootB);
    }

    @FXML
    private void openABIn1(ActionEvent event) {
        open(1, collectionRootAB);
    }

    @FXML
    private void openIn1(ActionEvent event) {
        open(1, null);
    }

    @FXML
    private void openAIn1(ActionEvent event) {
        open(1, collectionRootA);
    }

    @FXML
    private void openBIn1(ActionEvent event) {
        open(1, collectionRootB);
    }

    @FXML
    private void openABIn3(ActionEvent event) {
        open(3, collectionRootAB);
    }

    @FXML
    private void openIn3(ActionEvent event) {
        open(3, null);
    }

    @FXML
    private void openAIn3(ActionEvent event) {
        open(3, collectionRootA);
    }

    @FXML
    private void openBIn3(ActionEvent event) {
        open(3, collectionRootB);
    }

    private void open(int tableNumber, String collectionRoot) {
        if (collectionRoot == null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Open Media Folder for Table " + tableNumber);
            File path = dirChooser.showDialog(null).getAbsoluteFile();
            collectionRoot = path.toString();
        } else {
            String cwd = System.getProperty("user.dir");
            System.out.println(cwd);
            collectionRoot = Paths.get(cwd,
                    "..",
                    collectionRoot).toString();
        }
        TableView<MediaItem> referenceToEitherTable = null;
        if (tableNumber == 1) {
            referenceToEitherTable = tableView1;
        } else if (tableNumber == 2) {
            referenceToEitherTable = tableView2;
        } else if (tableNumber == 3) {
            referenceToEitherTable = tableView3;
        }

        addContents(referenceToEitherTable, collectionRoot);
    }

    private void addContents(TableView<MediaItem> referenceToEitherTable,
            String collectionRoot) {
        FileService fileService = new FileServiceImpl();
        Set<MediaItem> collectionA = fileService.getAllMediaItems(collectionRoot.toString());

        MediaInfoSource myInfoSource = new MediaInfoSourceFromID3();
        for (MediaItem item : collectionA) {
            try {
                myInfoSource.addMediaInfo(item);
            } catch (Exception e) {

            }
        }

        List<MediaItem> currentItems = referenceToEitherTable.getItems();
        collectionA.addAll(currentItems);

        ObservableList<MediaItem> dataForTableViewAndModel
                = FXCollections.observableArrayList(collectionA);
        referenceToEitherTable.setItems(dataForTableViewAndModel);
    }

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    private void addDragListener(TableView<MediaItem> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<MediaItem> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    MediaItem draggedMediaItem = tableView.getItems().remove(draggedIndex);
                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }
                    tableView.getItems().add(dropIndex, draggedMediaItem);
                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });
            return row;
        });
    }

    private void addSelectListener(TableView<MediaItem> tableView) {

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue,
                    Object oldValue,
                    Object newValue) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {

                    MediaItem m = tableView.getSelectionModel().getSelectedItem();
                    ObservableList selectedCells = tableView.getSelectionModel().getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val = tablePosition.getTableColumn().getCellData(newValue);
                }
            }
        });
    }

    private TableView getTableInFocus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void fillListWithTestImages(ObservableList<Image> observableListOfImages,
            String imageCollectionFolder) {
        
        String cwd = System.getProperty("user.dir");
        System.out.println("Working Directory = " + cwd);
        String totalPath = cwd + File.separator + ".." + File.separator + imageCollectionFolder;

        
        try (Stream<Path> walk = Files.walk(Paths.get(totalPath))) {

            System.out.println("about to look in folder");
            List<String> listOfFileNames = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            System.out.println("found " + listOfFileNames.size() + "files");
            for(String filename : listOfFileNames) {
                FileInputStream inputstream = new FileInputStream(filename); 
                Image image = new Image(inputstream);   
                observableListOfImages.add(image);
                System.out.println("added image to list");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
