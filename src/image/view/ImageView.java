package image.view;

import java.util.function.Consumer;

/**
 * This interface represents the methods that an ImageView can execute. ImageView is intended to be
 * used in tandem with ImageModel and ImageController but can be implemented elsewhere as it is it's
 * own module. This interface contains six methods: setImage, makeVisible, setCommandCallback,
 * getCommand,
 * showErrorMessage, and refresh. These methods setup the view, allow the view to process a command,
 * get a user typed command, show an error message, and redraw the view.
 */
public interface ImageView {

  /**
   * This method makes objects visible in the view. It uses a built in JFrame method called
   * setVisible to make things like panels visible on screen.
   */
  void makeVisible();

  /**
   * This method provides the view with a callback option to process a command.
   *
   * @param callback object
   */
  void setCommandCallback(Consumer<String> callback);

  /**
   * This method gets the command from the input variable for this JFrame.
   *
   * @return the string command to be executed.
   */
  String getCommand();


  /**
   * This method creates a pop-out error message for handling exceptions thrown through the view.
   * Each message is pulled from the controller/model.
   *
   * @param error the error that is thrown.
   */
  void showErrorMessage(String error);

  /**
   * This method is used by the controller to set the image displayed in the view to the current
   * image that is stored within the controllers model. It uses a method within ImageUtil called
   * getBufferedImage() that passes this view an image that can be displayed.
   *
   * @param image an integer array of pixels.
   */
  void setImage(int[][][] image);

  /**
   * This method is used to refresh the view, so that images and other objects can be reset and
   * updated without having to save and load images multiple times.
   */
  void refresh();

}



