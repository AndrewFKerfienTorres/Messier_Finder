package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.ZonedDateTime;


public class GUIController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private CheckBox filterNebula;
    @FXML private CheckBox filterGalaxy;
    @FXML private CheckBox filterOpenCluster;
    @FXML private CheckBox filterGlobularCluster;
    @FXML private CheckBox filterPlanetaryNebula;
    @FXML private CheckBox filterStar;
    @FXML private CheckBox filterSupernovaRemnant;
    @FXML private CheckBox filterAsterism;
    @FXML private CheckBox filterOther;
    @FXML private CheckBox filterJan;
    @FXML private CheckBox filterFeb;
    @FXML private CheckBox filterMar;
    @FXML private CheckBox filterApr;
    @FXML private CheckBox filterMay;
    @FXML private CheckBox filterJune;
    @FXML private CheckBox filterJuly;
    @FXML private CheckBox filterAug;
    @FXML private CheckBox filterSept;
    @FXML private CheckBox filterNov;
    @FXML private CheckBox filterDec;
    @FXML private CheckBox filterOct;
    @FXML private CheckBox filterIsVis;
    @FXML private TextField searchBar;
    @FXML private TilePane gridPane;

    @FXML
    public void initialize() {

        filterNebula.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterGalaxy.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterOpenCluster.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterGlobularCluster.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterPlanetaryNebula.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterSupernovaRemnant.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterStar.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterAsterism.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterOther.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterJan.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterFeb.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterMar.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterApr.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterMay.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterJune.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterJuly.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterAug.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterSept.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterOct.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterNov.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterDec.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterIsVis.selectedProperty().addListener((o, old, now) -> applyFilters());
        searchBar.textProperty().addListener((o, old, now) -> applyFilters());



        attachObjectsToVBox();

    }

    private void attachObjectsToVBox() {

        try{
            Catalogue.loadCatalogue();
            Catalogue c =Catalogue.getInstance();
        }
        catch(Exception e){
            System.out.println("An error has occurred: "+e);
        }
        Catalogue c =Catalogue.getInstance();
        for(Node n: gridPane.getChildren()){
            if(!(n instanceof VBox cell)) continue;
            String name=null;

            for(Node child: cell.getChildren()){
                if(child instanceof Button btn){
                    name=btn.getText();
                    break;
                }
            }
            if(name == null) continue;
            CelestialObject obj = c.get(name);
            if(obj!=null){
                cell.setUserData(obj);
            }

        }
    }

    public void applyFilters() {
        String search= searchBar.getText().toLowerCase();
        Observatory obsTemp=new Observatory();
        Telescope scopeTemp=new Telescope(2,16);

        VBox[] nodes= new VBox[110];
        int count=0;
        for(Node n: gridPane.getChildren()){
            if(!(n instanceof VBox cell)) continue;

            CelestialObject obj = (CelestialObject) cell.getUserData();
            if (obj == null) continue;



            boolean matchSearch= ((obj.getMessierIndex().toLowerCase().contains(search))
            ||(search.isBlank()));

            String type=obj.getObjectType().toString();
            boolean matchType=(filterNebula.isSelected()&&(type.equals("Nebula"))
            || (filterGlobularCluster.isSelected()&&type.equals("Globular Cluster"))||
                    (filterOpenCluster.isSelected()&&type.equals("Open Cluster"))||
                    (filterGalaxy.isSelected()&&type.equals("Galaxy"))||
                    (filterOther.isSelected()&&type.equals("Other"))||
                    (filterPlanetaryNebula.isSelected()&&type.equals("Planetary Nebula"))||
                    (filterStar.isSelected()&&type.equals("Star"))||
                    (filterSupernovaRemnant.isSelected()&&type.equals("Supernova Remnant"))||
                    (filterAsterism.isSelected()&&type.equals("Asterism"))||
                    (!filterOther.isSelected()&&!filterNebula.isSelected()&&!filterGlobularCluster.isSelected()
                    &&!filterGalaxy.isSelected()&&!filterOpenCluster.isSelected()
                    &&!filterSupernovaRemnant.isSelected()&&!filterStar.isSelected()&&!filterAsterism.isSelected()
                    &&!filterPlanetaryNebula.isSelected()));

            boolean matchVisibility=((filterIsVis.isSelected()&&obj.isVisible(obsTemp, ZonedDateTime.now(),scopeTemp))
            ||(filterJan.isSelected()));

           boolean show = matchSearch && matchType;
           cell.setVisible(show);
           cell.setManaged(show);
        }

    }
    private void format(VBox[] nodes){
        int colIndex=0;
        int rowIndex=0;
        gridPane.getChildren().clear();
        gridPane.getChildren().addAll(nodes);
    }
    public void switchHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home2.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchObject(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}