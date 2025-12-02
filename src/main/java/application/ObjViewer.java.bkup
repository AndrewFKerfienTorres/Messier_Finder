package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
 
    private static CelestialObject selectedObject;

    public static void setSelectedObject(CelestialObject obj) {
        selectedObject = obj;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root = loader.load();

          
            controller detailController = loader.getController();
            if (selectedObject != null) {
                detailController.displayCelestialObject(selectedObject);
            }

            Scene scene = new Scene(root);
            primaryStage.setTitle("Messier Object Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

    public static void main(String[] args) {
        launch(args);
    }
}

