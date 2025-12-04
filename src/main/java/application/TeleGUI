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

    // Put your telescope data fields here
    // Example:
    // private Telescope telescope;

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Telescope Settings");
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);

        // CREATE INPUT FIELDS HERE
        Label nameLabel = new Label("Telescope Name:");
        TextField nameField = new TextField();

        Label apertureLabel = new Label("Aperture (mm):");
        TextField apertureField = new TextField();

        Label focalLengthLabel = new Label("Focal Length (mm):");
        TextField focalLengthField = new TextField();

        // BUTTONS
        Button saveButton = new Button("Save / Update");
        saveButton.setPrefWidth(130);

        Button cancelButton = new Button("Close");
        cancelButton.setPrefWidth(130);

        saveButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                double aperture = Double.parseDouble(apertureField.getText());
                double focal = Double.parseDouble(focalLengthField.getText());

                if (name.isEmpty()) {
                    showAlert("Name cannot be empty.");
                    return;
                }
                if (aperture <= 0 || focal <= 0) {
                    showAlert("Values must be positive.");
                    return;
                }

                //Apply the data to telescope object or pass it back

                stage.close();

            } catch (Exception ex) {
                showAlert("Enter valid numeric values.");
            }
        });

        cancelButton.setOnAction(e -> stage.close());

        // LAYOUT
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setPadding(new Insets(15));
        grid.setAlignment(Pos.CENTER);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(apertureLabel, 0, 1);
        grid.add(apertureField, 1, 1);

        grid.add(focalLengthLabel, 0, 2);
        grid.add(focalLengthField, 1, 2);

        HBox buttonRow = new HBox(10, saveButton, cancelButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12, grid, buttonRow);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 330, 250);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



