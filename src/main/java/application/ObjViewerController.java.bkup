package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
    @FXML private Text spectralText;
 

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
        spectralText.setText("");
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
        dimensionsText.setText("Dimensions: " + (obj.getApparentSize() != null ? obj.getApparentSize() : "Unknown"));
        rightAscensionText.setText("Right Ascension (RA): " + obj.getRightAscension());
        declinationText.setText("Declination: " + obj.getDeclination());

       
        double alt = SkyPosition.getAltitude(java.time.ZonedDateTime.now(), observatory.getLatitude(), observatory.getLongitude(),
                                            obj.getRightAscension(), obj.getDeclination());
        double az = SkyPosition.getAzimuth(java.time.ZonedDateTime.now(), observatory.getLatitude(), observatory.getLongitude(),
                                          obj.getRightAscension(), obj.getDeclination());
        altitudeText.setText(String.format("Altitude: %.2f°", alt));
        azimuthText.setText(String.format("Azimuth: %.2f°", az));

        
        sizeText.setText("Size: " + obj.getSize());
        spectralText.setText("Spectral Content: " + (obj.getSpectralContent() != null ? obj.getSpectralContent() : "Unknown"));

        nextVisibleLabel.setText("Next Visible: " + computeNextVisibility(obj));

        // load image
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

    private String computeNextVisibility(CelestialObject obj) {
        try {
            java.time.ZonedDateTime nowUTC = java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC);
            java.time.ZonedDateTime[] window = SkyPosition.nextVisibilityWindow(
                    nowUTC,
                    observatory.getLatitude(),
                    observatory.getLongitude(),
                    obj.getRightAscension(),
                    obj.getDeclination()
            );
            return window[0].toLocalDateTime() + " to " + window[1].toLocalDateTime();
        } catch (Exception e) {
            return "Not visible within 1 year";
        }
    }

    @FXML
    private void handleBackButton() {
        //back2 main gui
    }
}


