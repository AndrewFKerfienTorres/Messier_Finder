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

    private final Observatory observatory;
    private final Telescope telescope;
    private Stage stage;

    public TeleGUI(Observatory observatory) {
        this.observatory = observatory;
        if (observatory.getTelescope() != null) {
            this.telescope = observatory.getTelescope();
        } else {
            this.telescope = new Telescope(90, 45); // default
            observatory.setTelescope(this.telescope);
        }
    }

    public Stage show() {
        stage = new Stage();
        stage.setTitle("Telescope Settings");
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);

        // Labels and fields
        Label fovLabel = new Label("Field of View (°):");
        TextField fovField = new TextField(String.valueOf(telescope.getFieldOfView()));
        fovField.setPrefWidth(120);

        Label aperLabel = new Label("Aperture (mm):");
        TextField aperField = new TextField(String.valueOf(telescope.getAperature()));
        aperField.setPrefWidth(120);

        // Buttons
        Button saveButton = new Button("Save");
        Button resetButton = new Button("Reset");
        saveButton.setPrefWidth(130);
        resetButton.setPrefWidth(130);

        saveButton.setOnAction(e -> saveTelescope(fovField, aperField));
        resetButton.setOnAction(e -> {
            fovField.setText(String.valueOf(telescope.getFieldOfView()));
            aperField.setText(String.valueOf(telescope.getAperature()));
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
        return stage;
    }

    private void saveTelescope(TextField fovField, TextField aperField) {
        try {
            double fov = Double.parseDouble(fovField.getText());
            double aper = Double.parseDouble(aperField.getText());

            telescope.setFieldOfView(fov);
            telescope.setAperture(aper);

            // Already linked to observatory, update persists in memory
            observatory.setTelescope(telescope);

            // Optional: save observatory to file for persistence
            Serializer.save(observatory, "observatory.ser");

            showAlert("Telescope settings saved successfully.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException ex) {
            showAlert("Please enter valid numbers!", Alert.AlertType.ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error saving telescope data!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (stage != null) alert.initOwner(stage);
        alert.showAndWait();
    }
}
