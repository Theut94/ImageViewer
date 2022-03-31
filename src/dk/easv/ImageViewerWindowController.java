package dk.easv;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewerWindowController implements Initializable
{
    private final List<Image> images = new ArrayList<>();


    private int currentImageIndex = 0;
    private int speed = 2;
    private ExecutorService es;
    private ImageSwapper is;

    @FXML
    private Label lblPictureTitel;
    @FXML
    private Slider sliderSpeed;
    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    private void handleBtnLoadAction()
    {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }


    }

    @FXML
    private void handleBtnPreviousAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnNextAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void displayImage()
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
            lblPictureTitel.setText(images.get(currentImageIndex).getUrl());
        }


    }

    public void handleBtnStartAction(ActionEvent actionEvent) {
        if(is == null)
        {
        imageView.setImage(images.get(currentImageIndex));

        is = new ImageSwapper(images, speed);
        is.valueProperty().addListener( (observable,oldv, newv) ->{
            imageView.setImage(newv);

        } );
        is.messageProperty().addListener((observable, oldValue, newValue) -> {
            lblPictureTitel.setText(newValue);
            System.out.println(newValue);
        });

        es.execute(is);
        is.setRunning(true);
        }
        else if(!is.Running())
        {
            es.execute(is);
            is.valueProperty().addListener( (observable,oldv, newv) ->{
                imageView.setImage(newv);

            } );
            is.messageProperty().addListener((observable, oldValue, newValue) -> {
                lblPictureTitel.setText(newValue);
                System.out.println(newValue);
            });
            is.setRunning(true);
        }
        else{

            is.setRunning(false);}

        System.out.println(is.Running());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sliderSpeed.valueProperty().addListener((observable, oldValue, newValue) -> {
            speed = newValue.intValue();
            System.out.println(speed);
        });
        es = Executors.newSingleThreadExecutor();


    }

    public void handleSliderSpeed(DragEvent dragEvent) {
    }
}