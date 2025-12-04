package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TeleGUI {

    private Telescope telescope;
    private Stage stage;  

    public void show() {
        stage = new Stage();
        stage.setTitle("Telescope Settings");
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
        stage.setResizable(true);

        loadTelescope();

        // Input fields
        Label fovLabel = new Label("Field of View (°):");
        TextField fovField = new TextField();
        fovField.setPrefWidth(120);

        Label aperLabel = new Label("Aperture (mm):");
        TextField aperField = new TextField();

        // Prefill if telescope exists
        if (telescope != null) {
            fovField.setText(String.valueOf(telescope.getFieldOfView()));
            aperField.setText(String.valueOf(telescope.getAperature()));
        }

        // Buttons
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(130);
        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(130);

        saveButton.setOnAction(e -> saveTelescope(fovField, aperField));
        resetButton.setOnAction(e -> {
            fovField.clear();
            aperField.clear();
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setPadding(new Insets(15));
        grid.setAlignment(Pos.CENTER);

        grid.add(fovLabel, 0, 0);
        grid.add(fovField, 1, 0);
        grid.add(aperLabel, 0, 1);
        grid.add(aperField, 1, 1);

        HBox buttonRow = new HBox(10, saveButton, resetButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12, grid, buttonRow);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 300, 150);
        stage.setScene(scene);
        stage.show();
    }

    private void saveTelescope(TextField fovField, TextField aperField) {
        try {
            double fov = Double.parseDouble(fovField.getText());
            double aper = Double.parseDouble(aperField.getText());

            telescope = new Telescope(fov, aper);
            Serializer.save(telescope, "telescope.ser");
        } catch (NumberFormatException ex) {
            showAlert("Please enter valid numbers!");
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error saving telescope data!");
        }
    }

    private void loadTelescope() {
        try {
            Object obj = Serializer.load("telescope.ser");
            if (obj instanceof Telescope) {
                telescope = (Telescope) obj;
            }
        } catch (Exception e) {
            telescope = null; // no saved telescope yet
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (stage != null) {
        alert.initOwner(stage);
    }
    alert.showAndWait();
    }
}
