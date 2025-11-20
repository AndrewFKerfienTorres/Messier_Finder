import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class HomeGUI extends Application {
    @Override
    public void start(Stage stage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home2.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}

