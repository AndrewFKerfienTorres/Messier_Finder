package application;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Optional;

public class controller {

    @FXML private Button backButton;
    @FXML private ImageView objectImage;
    @FXML private javafx.scene.control.Label commonNameLabel;
    @FXML private javafx.scene.control.Label nextVisibleLabel;

    @FXML private Text messierNumberText;
    @FXML private Text objectTypeText;
    @FXML private Text distanceText;
    @FXML private Text constellationText;
    @FXML private Text magnitudeText;
    @FXML private Text dimensionsText;
    @FXML private Text rightAscensionText;
    @FXML private Text declinationText;
    @FXML private Text altitudeText;
    @FXML private Text azimuthText;
    @FXML private Text sizeText;

    private Observatory observatory;

    @FXML
    private void initialize() {
        clearFields();
        observatory = new Observatory();
    }

    private void clearFields() {
        commonNameLabel.setText("");
        nextVisibleLabel.setText("");
        messierNumberText.setText("");
        objectTypeText.setText("");
        distanceText.setText("");
        constellationText.setText("");
        magnitudeText.setText("");
        dimensionsText.setText("");
        rightAscensionText.setText("");
        declinationText.setText("");
        altitudeText.setText("");
        azimuthText.setText("");
        sizeText.setText("");
        objectImage.setImage(null);
    }

    public void displayCelestialObject(CelestialObject obj) {
        if (obj == null) return;

        commonNameLabel.setText(obj.getCommonName());
        messierNumberText.setText("Messier Number: " + obj.getMessierIndex());
        objectTypeText.setText("Object Type: " + obj.getObjectType());
        distanceText.setText("Distance from Earth: " + obj.getDistance());
        constellationText.setText("Constellation: " + obj.getConstellation());
        magnitudeText.setText("Apparent Magnitude: " + obj.getApparentMagnitude());
        String dimensionsStr = obj.getApparentDimensionsString();
        dimensionsText.setText("Dimensions: " + dimensionsStr);
        sizeText.setText("Size: " + dimensionsStr);

        rightAscensionText.setText("Right Ascension (RA): " + obj.getRightAscension());
        declinationText.setText("Declination: " + obj.getDeclination());

        // altitude + azimuth right now
        ZonedDateTime now = ZonedDateTime.now();
        double alt = SkyPosition.getAltitude(now, observatory.getLatitude(), observatory.getLongitude(),
                                             obj.getRightAscension(), obj.getDeclination());
        double az = SkyPosition.getAzimuth(now, observatory.getLatitude(), observatory.getLongitude(),
                                           obj.getRightAscension(), obj.getDeclination());

        altitudeText.setText(String.format("Altitude: %.2f°", alt));
        azimuthText.setText(String.format("Azimuth: %.2f°", az));
     

       
        nextVisibleLabel.setText(computeMonthlyVisibility(obj));

        // image
        if (obj.getImagePath() != null && !obj.getImagePath().isEmpty()) {
            try {
                Image img = new Image(getClass().getResourceAsStream(obj.getImagePath()));
                objectImage.setImage(img);
            } catch (Exception e) {
                System.err.println("Failed to load image: " + obj.getImagePath());
                objectImage.setImage(null);
            }
        } else {
            objectImage.setImage(null);
        }
    }

 
    private String computeMonthlyVisibility(CelestialObject obj) {
        Month currentMonth = ZonedDateTime.now().getMonth();

        Optional<ZonedDateTime[]> window =
                SkyPosition.visibilityWindowForMonth(
                        observatory.getLatitude(),
                        observatory.getLongitude(),
                        obj.getRightAscension(),
                        obj.getDeclination(),
                        currentMonth
                );

        return window
                .map(w -> "Current visible " + w[0].toLocalDateTime() + " to " + w[1].toLocalDateTime())
                .orElse("Not visible this month");
    }

    @FXML
    private void handleBackButton() {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomeGUI.fxml"));
            Parent homeRoot = loader.load();

        
            Stage stage = (Stage) backButton.getScene().getWindow();

            
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

