import sun.applet.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Matthew on 2017-01-20.
 */
public class Texture {
    private String path;
    public int[] pixels;
    public Texture(String path){
        setPath(path);
        pixels = new int[MainGame.texWidth * MainGame.texWidth];
    }

    public void load() { //loads the image into the array
        try {
            BufferedImage image = ImageIO.read(new File(getPath()));  //create a buffered image at from the file at that path
            int w = image.getWidth();  //get the image width
            int h = image.getHeight(); //get the image height
            image.getRGB(0, 0, w, h, pixels, 0, w);  //Get the RGB value of each pixel in the image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
