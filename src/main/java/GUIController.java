import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private CheckBox filterNebula;
    @FXML private CheckBox filterGalaxy;
    @FXML private CheckBox filterOpenCluster;
    @FXML private CheckBox filterGlobularCluster;
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
    @FXML private GridPane gridPane;

    Catalogue catalogue = Catalogue.getInstance();
    private Map<String, CelestialObject> objectMap = new HashMap<>();

    @FXML
    public void initialize() {
        filterNebula.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterGalaxy.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterOpenCluster.selectedProperty().addListener((o, old, now) -> applyFilters());
        filterGlobularCluster.selectedProperty().addListener((o, old, now) -> applyFilters());
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

        for(CelestialObject obj: catalogue){
            objectMap.put(obj.getMessierIndex(), obj);
        }
        attachObjectsToVBox();
    }

    private void attachObjectsToVBox() {
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
            CelestialObject obj = objectMap.get(name);
            if(obj!=null){
                cell.setUserData(obj);
            }
        }
    }

    private void applyFilters() {
        String search= searchBar.getText().toLowerCase();

        for(Node n: gridPane.getChildren()){
            if(!(n instanceof VBox cell)) continue;

            CelestialObject obj = (CelestialObject) cell.getUserData();
            if (obj == null) continue;

            boolean matchSearch= obj.getMessierIndex().toLowerCase().contains(search);

           boolean show = matchSearch;
           cell.setVisible(show);
           cell.setManaged(show);
        }
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
