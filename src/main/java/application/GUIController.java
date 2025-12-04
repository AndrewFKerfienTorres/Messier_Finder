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
import java.time.Month;
import java.time.ZoneOffset;
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

        if (filterNebula != null) {filterNebula.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterGalaxy != null){filterGalaxy.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterOpenCluster != null){filterOpenCluster.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterGlobularCluster != null){filterGlobularCluster.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterPlanetaryNebula != null){filterPlanetaryNebula.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterSupernovaRemnant != null){filterSupernovaRemnant.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterStar != null){filterStar.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterAsterism != null){filterAsterism.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterOther != null){filterOther.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterJan != null){filterJan.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterFeb != null){filterFeb.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterMar != null){filterMar.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterApr != null){filterApr.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterMay != null){filterMay.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterJune != null){filterJune.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterJuly != null){filterJuly.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterAug != null){filterAug.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterSept!= null){filterSept.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterOct != null){filterOct.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterNov != null){filterNov.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterDec != null){filterDec.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (filterIsVis != null){filterIsVis.selectedProperty().addListener((o, old, now) -> applyFilters());}
        if (searchBar != null){searchBar.textProperty().addListener((o, old, now) -> applyFilters());}



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
        if(gridPane!=null){
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



            boolean matchSearch= ((search.isBlank())||obj.toString().toLowerCase().contains(search));


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

            double tempLat= obsTemp.getLatitude();
            double tempLong= obsTemp.getLongitude();
            double Dec=obj.getDeclination();
            double RA=obj.getRightAscension();
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
          
            boolean matchVisibility=((filterIsVis.isSelected()&&obj.isVisible(obsTemp, now ,scopeTemp))
            ||(filterJan.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.JANUARY).isPresent())
            ||(filterFeb.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.FEBRUARY).isPresent())
            ||(filterMar.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.MARCH).isPresent())
            ||(filterApr.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.APRIL).isPresent())
            ||(filterMay.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.MAY).isPresent())
            ||(filterJune.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.JUNE).isPresent())
            ||(filterJuly.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.JULY).isPresent())
            ||(filterAug.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.AUGUST).isPresent())
            ||(filterSept.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.SEPTEMBER).isPresent())
            ||(filterOct.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.OCTOBER).isPresent())
            ||(filterNov.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.NOVEMBER).isPresent())
            ||(filterDec.isSelected()&&SkyPosition.visibilityWindowForMonth(tempLat,tempLong,RA,Dec, Month.DECEMBER).isPresent())
            ||(!filterJan.isSelected()&&!filterFeb.isSelected()&&!filterMar.isSelected()&&!filterApr.isSelected()&&!filterMay.isSelected()
            &&!filterJune.isSelected()&&!filterJuly.isSelected()&&!filterAug.isSelected()&&!filterSept.isSelected()&&!filterOct.isSelected()
            &&!filterNov.isSelected()&&!filterDec.isSelected()));

           boolean show = matchSearch && matchType && matchVisibility;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = loader.load();

        GUIController controller = loader.getController();

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchObstruction(ActionEvent event) throws IOException{
        new ObGUI().showPopup();
    }
}