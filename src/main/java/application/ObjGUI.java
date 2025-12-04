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

import java.util.ArrayList;
import java.util.List;

public class ObjGUI {

    private List<Obstruction> obstructions = new ArrayList<>();
    private ListView<Obstruction> obstructionListView;
    private Stage stage;

    public void show() {
        stage = new Stage();
        stage.setTitle("Add Telescope Obstruction");
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);

        loadObstructions();

        // Input fields
        Label labelLabel = new Label("Label:");
        TextField labelField = new TextField();
        labelField.setPrefWidth(120);

        Label fromLabel = new Label("Range From (°):");
        TextField fromField = new TextField();

        Label toLabel = new Label("Range To (°):");
        TextField toField = new TextField();

        Label altitudeLabel = new Label("Altitude (°):");
        TextField altitudeField = new TextField();

        // Buttons
        Button addButton = new Button("+ Add / Update");
        addButton.setPrefWidth(130);
        Button deleteButton = new Button("Delete Selected");
        deleteButton.setPrefWidth(130);

        obstructionListView = new ListView<>();
        obstructionListView.setPrefHeight(160);
        obstructionListView.getItems().addAll(obstructions);

        // Add / Update
        addButton.setOnAction(e -> addOrUpdateObstruction(labelField, fromField, toField, altitudeField));

        // Delete
        deleteButton.setOnAction(e -> deleteSelected());

        // Double-click to edit
        obstructionListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                Obstruction selected = obstructionListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    labelField.setText(selected.getDescription());
                    fromField.setText(String.valueOf(selected.getBeginAzimuth()));
                    toField.setText(String.valueOf(selected.getEndAzimuth()));
                    altitudeField.setText(String.valueOf(selected.getBeginAltitude()));
                }
            }
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setPadding(new Insets(15));
        grid.setAlignment(Pos.CENTER);

        grid.add(labelLabel, 0, 0);
        grid.add(labelField, 1, 0);
        grid.add(fromLabel, 0, 1);
        grid.add(fromField, 1, 1);
        grid.add(toLabel, 0, 2);
        grid.add(toField, 1, 2);
        grid.add(altitudeLabel, 0, 3);
        grid.add(altitudeField, 1, 3);

        HBox buttonRow = new HBox(10, addButton, deleteButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12, grid, buttonRow, obstructionListView);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 330, 390);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> saveObstructions());
    }

    private void addOrUpdateObstruction(TextField labelField, TextField fromField, TextField toField, TextField altitudeField) {
        try {
            String label = labelField.getText().trim();
            if (label.isEmpty()) { showAlert("Please enter a label!"); return; }

            double from = Double.parseDouble(fromField.getText());
            double to = Double.parseDouble(toField.getText());
            double alt = Double.parseDouble(altitudeField.getText());

            if (from < 0 || from > 360 || to < 0 || to > 360) { showAlert("Range must be between 0° and 360°!"); return; }
            if (from >= to) { showAlert("Range From must be LESS than Range To!"); return; }
            if (alt < 0 || alt > 90) { showAlert("Altitude must be between 0° and 90°!"); return; }

            Obstruction o = new Obstruction(label, from, to, 0, alt);

            int selectedIndex = obstructionListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                obstructions.set(selectedIndex, o);
                obstructionListView.getItems().set(selectedIndex, o);
            } else {
                obstructions.add(o);
                obstructionListView.getItems().add(o);
            }

            labelField.clear();
            fromField.clear();
            toField.clear();
            altitudeField.clear();
            obstructionListView.getSelectionModel().clearSelection();
        } catch (NumberFormatException ex) {
            showAlert("Please enter valid numbers!");
        }
    }

    private void deleteSelected() {
        int index = obstructionListView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            obstructions.remove(index);
            obstructionListView.getItems().remove(index);
        } else { showAlert("No item selected!"); }
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

    private void loadObstructions() {
        try {
            Object obj = Serializer.load("obstructions.ser");
            if (obj instanceof List<?>) { obstructions = (List<Obstruction>) obj; }
        } catch (Exception e) { obstructions = new ArrayList<>(); }
    }

    private void saveObstructions() {
        try { Serializer.save(obstructions, "obstructions.ser"); } catch (Exception e) { e.printStackTrace(); }
    }
}

