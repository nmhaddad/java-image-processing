package image.model.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of the pixel interface. It is used to store an x and y
 * coordinate for a pixel and its three-channel red green blue values. It contains methods to get
 * the x and y coordinates, as well as a getter for the three-channel color array.
 */
public class PixelImpl implements Pixel {

  /**
   * This value represents the x coordinate for this pixel (the column).
   */
  private int x;

  /**
   * This value represents the y coordinate for this pixel (the row).
   */
  private int y;

  /**
   * This value represents the three-channel RGB value for this pixel.
   */
  private int[] color;

  /**
   * This variable represents all of the pixel colors that are closest to this pixel centroid.
   */
  private List<int[]> colors = new ArrayList<>();

  /**
   * This constructor creates a pixel with the given x and y coordinates and a three-channel color
   * array value.
   *
   * @param x     the x coordinate of this pixel.
   * @param y     the y coordinate of this pixel.
   * @param color the three-channel color array for this pixel.
   */
  PixelImpl(int x, int y, int[] color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  /**
   * This method gets the x coordinate of this pixel.
   *
   * @return the integer value x coordinate of this pixel.
   */
  public int getX() {
    return x;
  }

  /**
   * This method gets the y coordinate of this pixel.
   *
   * @return the integer value y coordinate of this pixel.
   */
  public int getY() {
    return y;
  }

  /**
   * This method gets the three-channel color array for this pixel.
   *
   * @return an integer array of three values that represent the red, green and blue values for this
   *         pixel.
   */
  public int[] getColor() {
    return color;
  }

  /**
   * This method gives this pixel a new color value that is the sum of all of the pixels that are
   * closest to it.
   *
   * @param rgbColor the three channel values for a pixel.
   */
  @Override
  public void giveNewColor(int[] rgbColor) {
    this.colors.add(rgbColor);
  }

  /**
   * This method gets the average color of this pixel cluster.
   *
   * @return the integer average color of this pixel cluster.
   */
  @Override
  public int[] getAverageColor() {
    int[] averageColor = {0, 0, 0};
    for (int[] color : colors) {
      averageColor[0] += color[0];
      averageColor[1] += color[1];
      averageColor[2] += color[2];
    }
    averageColor[0] /= (double) colors.size();
    averageColor[1] /= (double) colors.size();
    averageColor[2] /= (double) colors.size();
    return averageColor;
  }

}