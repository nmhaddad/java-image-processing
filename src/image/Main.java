package image;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import image.control.ImageController;
import image.control.ImageControllerImpl;
import image.control.ImageControllerImplInteractive;
import image.model.ImageModel;
import image.model.ImageModelImpl;
import image.view.ImageGraphics;
import image.view.ImageView;

/**
 * This class utilizes ImageUtil and ImageFilter to read and image, write a new image, blur an
 * image, sharpen an image, convert an image to sepia and grey scale, and to create a new image that
 * is rainbow stripes (vertical and horizontal) or black & white checkerboard.
 */
public class Main {

  /**
   * This method is a driver for this program. It loads a image from a filename and modifies the
   * image with the given arguments. Each try/catch block represents a different example of the
   * driver, meaning that each can be run separately (we do this in the test suite). Our goal was to
   * create a independent model that could be passed to a controller in the future.
   *
   * @param args the arguments passed to main via the command line.
   * @throws IOException if the operation cannot be completed.
   * @throws IllegalArgumentException if the command entered is invalid.
   */
  public static void main(String[] args) throws IOException, IllegalArgumentException {
    if (args[0].equals("-interactive")) {
      try {
        ImageModel model = new ImageModelImpl();
        ImageView view = new ImageGraphics();
        ImageController controller = new ImageControllerImplInteractive(view, model);
        controller.begin();
      } catch (IOException e) {
        throw new IOException(e.getMessage());
      }
    } else if (args[0].equals("-script")) {
      try {
        Reader input = new FileReader(args[1]);
        ImageModel model = new ImageModelImpl();
        ImageController controller = new ImageControllerImpl(input, model);
        controller.begin();
      } catch (IOException e) {
        throw new IOException(e.getMessage());
      }
    } else {
      throw new IllegalArgumentException("Error: invalid command");
    }
  }

}
