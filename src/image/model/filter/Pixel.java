package image.model.filter;

/**
 * This class represents the methods of the pixel interface. It is used to store an x and y
 * coordinate for a pixel and its three-channel red green blue values. It contains methods to get
 * the x and y coordinates, as well as a getter for the three-channel color array.
 */
public interface Pixel {

  /**
   * This method gets the x coordinate of this pixel.
   *
   * @return the integer value x coordinate of this pixel.
   */
  int getX();

  /**
   * This method gets the y coordinate of this pixel.
   *
   * @return the integer value y coordinate of this pixel.
   */
  int getY();

  /**
   * This method gets the three-channel color array for this pixel.
   *
   * @return an integer array of three values that represent the red, green and blue values for this
   *         pixel.
   */
  int[] getColor();

  /**
   * This method gives this pixel a new color value that is the sum of all of the pixels that are
   * closest to it.
   *
   * @param rgbColor the three channel values for a pixel.
   */
  void giveNewColor(int[] rgbColor);

  /**
   * This method gets the average color of this pixel cluster.
   *
   * @return the integer average color of this pixel cluster.
   */
  int[] getAverageColor();

}