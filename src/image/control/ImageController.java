package image.control;

import java.io.IOException;

/**
 * This interface represents a controller for the image model. It is used to run the ImageModel
 * using any type of input. For example, a user can pass in a batch file or enter input form the
 * command line. It contains the method run, which takes in an image model and runs it using the
 * user inputted information.
 */
public interface ImageController {

  /**
   * This method runs the program by taking in user input and parsing it. After parsing the method
   * calls a helper that uses the parsed information to run the methods from the image model or the
   * view.
   *
   * @throws IOException if the given file cannot be read or the information in the file is
   *                     invalid.
   */
  void begin() throws IOException;

  /**
   * This method is used by any implementation that uses a Java Swing graphical user interface
   * (GUI). It takes a string as a parameter, which is the command that has been passed from the
   * view to this method through the view's accept method.
   *
   * @param input a string command passed to this controller.
   * @throws IOException if the operation cannot be completed (e.g. error reading file).
   */
  void accept(String input) throws IOException;

}