package image.model.pattern;

import image.model.Image;

/**
 * This interface represents a pattern image. It contains four methods that draw a rainbow image
 * using the given width and height, and draws a checkerboard pattern. Since a rainbow is seven
 * colors, each method requires a minimum amount of pixels so that all colors of the rainbow can be
 * displayed,or so that all rows in an 8x8 checkerboard can be displayed.
 */
public interface PatternImage {

  /**
   * This method draws a rainbow with seven equally wide stripes in the order of red, orange,
   * yellow, green, blue, indigo, and violet. If the stripes cannot be of equal width the last
   * stripe (violet) will be up to six pixels smaller than the rest of the stripes.
   *
   * @param width  the desired width in pixels of the image.
   * @param height the desired height in pixels of the image.
   * @throws IllegalArgumentException if the given values for width and height do not allow the
   *                                  method to draw a complete rainbow (e.g. width is less than 1
   *                                  pixel tall, or if height is less than seven pixels tall).
   */
  Image drawRainbowWithHorizontalStripes(int width, int height) throws IllegalArgumentException;

  /**
   * This method draws a rainbow with seven equally tall stripes in the order of red, orange,
   * yellow, green, blue, indigo, and violet. If the stripes cannot be of equal width the last
   * stripe (violet) will be up to six pixels smaller than the rest of the stripes.
   *
   * @param width  the desired width in pixels of the image.
   * @param height the desired height in pixels of the image.
   * @throws IllegalArgumentException if the given values for width and height do not allow the
   *                                  method to draw a complete rainbow (e.g. width is less than 7
   *                                  pixel tall, or if height is less than 1 pixels tall).
   */
  Image drawRainbowWithVerticalStripes(int width, int height) throws IllegalArgumentException;

  /**
   * This method draws a new image with a black and white checkerboard pattern. It is drawn with
   * specified square dimensions to the specified image dimensions. If the checks do not fit equally
   * in the image the bottom and right side will be trimmed. These squares will be at least one
   * pixel big.
   *
   * @param size the width and height of the individual checks.
   * @throws IllegalArgumentException if the given size is less than 8 pixels wide, since a
   *                                  checkerboard it 8 squares wide/tall.
   */
  Image drawCheckerboard(int size) throws IllegalArgumentException;

}
