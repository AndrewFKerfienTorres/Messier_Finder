package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class controller {
//ignore all the @fxml i cant get it to work :(
    @FXML private Button backButton;

    @FXML private ImageView objectImage;
    @FXML private Label commonNameLabel;
    @FXML private Label nextVisibleLabel;

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
    @FXML private Text descriptionText;

    @FXML
    private void initialize() {
        // leave blank — values will be set by the main gooey
    }
 

    public void setCommonName(String name) { commonNameLabel.setText(name); }
    public void setNextVisible(String text) { nextVisibleLabel.setText(text); }

    public void setMessierNumber(String text) { messierNumberText.setText("Messier Number: " + text); }
    public void setObjectType(String text) { objectTypeText.setText("Object Type: " + text); }
    public void setDistance(String text) { distanceText.setText("Distance from Earth: " + text); }
    public void setConstellation(String text) { constellationText.setText("Constellation: " + text); }
    public void setMagnitude(String text) { magnitudeText.setText("Apparent Magnitude: " + text); }
    public void setDimensions(String text) { dimensionsText.setText("Dimensions: " + text); }
    public void setRightAscension(String text) { rightAscensionText.setText("Right Ascension (RA): " + text); }
    public void setDeclination(String text) { declinationText.setText("Declination: " + text); }
    public void setAltitude(String text) { altitudeText.setText("Altitude: " + text); }
    public void setAzimuth(String text) { azimuthText.setText("Azimuth: " + text); }
    public void setSize(String text) { sizeText.setText("Size: " + text); }
    public void setSpectralContent(String text) { spectralText.setText("Spectral Content: " + text); }
    public void setDescription(String text) { descriptionText.setText("Description: " + text); }

    @FXML
    private void handleBackButton() {
        // blank so it can connect back to main gui
    }
}
