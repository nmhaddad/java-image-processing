package image.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import image.model.ImageImpl;
import image.model.ImageModel;
import image.view.ImageView;

import static image.model.ImageUtil.readImage;
import static image.model.ImageUtil.writeImage;

/**
 * This class represents the ImageController, the controller for this program . It includes nested
 * classes for each method that an ImageModel can perform, and a begin function that starts the
 * parsing of an input.
 */
public class ImageControllerImplInteractive implements ImageController {

  /**
   * This variable represents the view of this program.
   */
  private ImageView view;

  /**
   * This variable is a String[] object representing the current commands being run.
   */
  private String[] commands;

  /**
   * This variable is a integer that keeps count of the number of commands in a line.
   */
  private int counter;

  /**
   * This variable is an ImageModel that represents the model.
   */
  private ImageModel model;

  /**
   * This variable is a HashMap that holds all the possible executable classes.
   */
  private HashMap<String, Runnable> executables;

  /**
   * This constructs an ImageController object. ImageControllerImplInteractive takes in a Readable
   * input. The ImageController also takes in an ImageModel. The ImageModel holds all the methods
   * that can be done to transform or filter an image. It has a method, begin, that parses the
   * Readable input and executes the methods on the ImageModel object. Valid methods from input are:
   * load fileName, save file name, dither, blur, sharpen, greyscale, sepia, Mosaic seedNumber,
   * checkerboard tileSize, draw flag Country Size, draw vertical rainbow width height, draw
   * horizontal rainbow width height . More information on these methods can be found in
   * ImageModel.
   *
   * @param view  a view for this program.
   * @param model an ImageModelImpl object
   */
  public ImageControllerImplInteractive(ImageView view, ImageModel model) {
    this.view = view;
    this.model = model;
    this.commands = new String[100];
    this.executables = new HashMap<>();

    executables.put("load", new Load());
    executables.put("save", new Save());
    executables.put("dither", new Dither());
    executables.put("blur", new Blur());
    executables.put("sharpen", new Sharpen());
    executables.put("greyscale", new Greyscale());
    executables.put("sepia", new Sepia());
    executables.put("mosaic", new Mosaic());
    executables.put("checkerboard", new Checkerboard());
    executables.put("draw", new Draw());
    executables.put("undo", new Undo());
    executables.put("redo", new Redo());
  }

  /**
   * This method runs the program by taking in user input and parsing it. After parsing the method
   * calls a helper that uses the parsed information to run the methods from the image model or the
   * view.
   */
  @Override
  public void begin() {
    this.view.setCommandCallback(this::accept);
    this.view.makeVisible();
  }

  /**
   * This method processes the commands from the user. It parses on a space an separates them into
   * individual slots in an array. THis array can then be accessed to run the commands.
   *
   * @param input the input from the user, a string.
   * @throws IOException if the input cannot be read.
   */
  private void processCommand(String input) throws IOException {
    Scanner scan = new Scanner(input);
    while (scan.hasNextLine()) {
      counter = 0;
      this.commands = new String[100];
      Scanner scannedLine = new Scanner(scan.nextLine());
      while (scannedLine.hasNext()) {
        if (counter == 0) {
          this.commands[counter] = scannedLine.next();
          counter++;
        } else if (this.commands[0].equals("save") || this.commands[0].equals(
                "load")) {
          this.commands[1] = scannedLine.nextLine().trim();
          counter++;
        } else {
          this.commands[counter] = scannedLine.next();
          counter++;
        }
      }
      if (executables.containsKey(this.commands[0])) {
        this.executables.get(this.commands[0]).run();
      } else {
        throw new IOException("Error: cannot run program");
      }
    }
  }

  /**
   * This method accepts the input and calls the method processCommand to start the processing of
   * the input. If there is an error in reading the input, it sends s signal tp the view to show an
   * error message. If there is no error, it sets the image.
   *
   * @param input the input from the user, a string.
   */
  @Override
  public void accept(String input) {
    String status = "";
    try {
      processCommand(input);
    } catch (Exception exception) {
      view.showErrorMessage(exception.getMessage());
    }
    view.setImage(model.getImage());
    view.refresh();
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that calls the loadImage
   * method on the model.
   */
  private class Load implements Runnable {
    public void run() {
      if (counter != 2) {
        throw new IllegalArgumentException("Error: cannot load image");
      }
      try {
        int[][][] image = readImage(commands[1]);
        model.loadImage(new ImageImpl(image));
      } catch (IOException e) {
        e.getMessage();
      }
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that writes the image
   * held in the image model.
   */
  private class Save implements Runnable {
    public void run() {
      if (counter < 2) {
        throw new IllegalArgumentException("Error: cannot load image");
      }
      try {
        writeImage(model.getImage(), model.getImageWidth(), model.getImageHeight(), commands[1]);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the dither
   * method on the model.
   */
  private class Dither implements Runnable {
    public void run() {
      model.dither();
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the blur
   * method on the model.
   */
  private class Blur implements Runnable {
    public void run() {
      model.blur();
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the sharpen
   * method on the model.
   */
  private class Sharpen implements Runnable {
    public void run() {
      model.sharpen();
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the
   * toGreyscale method on the model.
   */
  private class Greyscale implements Runnable {
    public void run() {
      model.toGreyscale();
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the toSepia
   * method on the model.
   */
  private class Sepia implements Runnable {
    public void run() {
      model.toSepia();
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the mosaic
   * method on the model.
   */
  private class Mosaic implements Runnable {
    public void run() {
      model.mosaic(Integer.parseInt(commands[1]));
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the
   * drawCheckerboard method on the model.
   */
  private class Checkerboard implements Runnable {
    public void run() {
      model.drawCheckerboard(Integer.parseInt(commands[1]));
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run. The run method runs 3
   * commands, drawFlag, drawRainbowWithVerticalStripes, and drawRainbowWithHorizontalStripes.
   */
  private class Draw implements Runnable {
    public void run() {
      switch (commands[1]) {
        case "flag":
          model.drawFlag(commands[2], Integer.parseInt(commands[3]));
          break;
        case "vertical":
          try {
            if (commands[2].equals("rainbow")) {

              model.drawRainbowWithVerticalStripes(Integer.parseInt(commands[3]),
                      Integer.parseInt(commands[4]));
            }
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: cannot complete "
                    + "vertical rainbow drawing");
          }
          break;
        case "horizontal":
          try {
            if (commands[2].equals("rainbow")) {
              model.drawRainbowWithHorizontalStripes(Integer.parseInt(commands[3]),
                      Integer.parseInt(commands[4]));
            }
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: cannot complete "
                    + "horizontal rainbow drawing");
          }
          break;
        default:
          throw new IllegalArgumentException("Error: cannot draw");
      }
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the undo
   * method on the model.
   */
  private class Undo implements Runnable {
    public void run() {
      model.undo();
    }
  }

  /**
   * This class implements the Runnable interface. It has one method, run, that executes the redo
   * method on the model.
   */
  private class Redo implements Runnable {
    public void run() {
      model.redo();
    }
  }
}
