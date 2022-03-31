package dk.easv;

import javafx.concurrent.Task;
import javafx.scene.image.Image;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ImageSwapper extends Task<Image> {

    private List<Image> listOfImages = new ArrayList<>();
    private int imagenr = 0;
    private int speed;
    private boolean isRunning = true;

    public ImageSwapper(List<Image> listOfImages, int speed) {
        this.listOfImages = listOfImages;
        this.speed = speed;

    }
    public boolean Running()
    {
        return isRunning;
    }
    public void setRunning(boolean isRunning)
    {
        this.isRunning = isRunning;
    }

    @Override
    protected Image call() throws Exception {

        Image imageToShow = listOfImages.get(0);
              while(isRunning){

                  if (imagenr < listOfImages.size()) {
                     imageToShow = listOfImages.get(imagenr++);
                      updateValue(imageToShow);
                     updateMessage("picture: " +imageToShow.getUrl());
                      TimeUnit.SECONDS.sleep(speed);

                  }
                  else if ( imagenr == listOfImages.size()){
                      imagenr = 0;

                  }

              }

                  return listOfImages.get(imagenr);
                  }

}
    /**
     *  Image imageToShow = listOfImages.get(0);
     *         while(imagenr!= -1){
     *             TimeUnit.SECONDS.sleep(4);
     *             if (imagenr < listOfImages.size()) {
     *                 imageToShow = listOfImages.get(imagenr++);
     *                 updateValue(imageToShow);
     *                 updateMessage("picture nr: " +imagenr);
     *
     *             }
     *             else if ( imagenr == listOfImages.size()){
     *                 imagenr = 0;
     *                 updateValue(listOfImages.get(imagenr));}}
     *
     *
     *             return listOfImages.get(imagenr);
     *             }
     */

