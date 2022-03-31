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
    private boolean isRunning = false;

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
        }


    }

    public void handleBtnStartAction(ActionEvent actionEvent) {
        if( !es.isShutdown() || !isRunning)
        {
        imageView.setImage(images.get(currentImageIndex));

        ImageSwapper imageSwapper = new ImageSwapper(images, speed);
        imageSwapper.valueProperty().addListener( (observable,oldv, newv) ->{
            imageView.setImage(newv);

        } );
        imageSwapper.messageProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });

        es.execute(imageSwapper);
        isRunning = true;}
        else{
            es.shutdownNow();
            System.out.println("thread has stopped");
            isRunning=false;}

        System.out.println(isRunning);

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