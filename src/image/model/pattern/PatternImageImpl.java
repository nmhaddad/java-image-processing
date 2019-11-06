package image.model.pattern;

import image.model.Image;
import image.model.ImageImpl;

/**
 * This class represents the implementation of the rainbow image interface. It contains two methods
 * that draw a horizontal or vertical rainbow with given width and height. Each method requires the
 * user to enter a minimum number of pixels since all colors of the rainbow need to be displayed. It
 * also contains a helper method that gets the stripe size using the given width and height.
 */
public class PatternImageImpl implements PatternImage {

  /**
   * The RGB color values for each color of the rainbow, in order of ROYGBIV.
   */
  private int[][] colors = {{255, 0, 0}, {255, 127, 0}, {255, 255, 0}, {0, 255, 0}, {0, 0, 255},
                            {75, 0, 130}, {148, 0, 211}};

  /**
   * The RBG color values for black and white.
   */
  private int[][] blackAndWhite = {{0, 0, 0}, {255, 255, 255}};

  /**
   * This method draws a rainbow with seven equally wide stripes in the order of red, orange,
   * yellow, green, blue, indigo, and violet. If the stripes cannot be of equal width the last
   * stripe (violet) will be up to six pixels smaller than the rest of the stripes.
   *
   * @param width  the desired width in pixels of the image.
   * @param height the desired height in pixels of the image.
   * @throws IllegalArgumentException if the given values for width and height do not allow the
   *                                  method to draw a complete rainbow (e.g. width is less than 1
   *                                  pixel tall, or if height is less than 13 pixels tall).
   */
  @Override
  public Image drawRainbowWithHorizontalStripes(int width, int height)
          throws IllegalArgumentException {
    if (width < 1 || height < 13) {
      throw new IllegalArgumentException("Error: width must be greater than 0 and height must be "
              + "greater than 12");
    }
    int[][][] rainbow = new int[height][width][3];
    int stripeSize = getStripeSize(height);
    int colorIndex = 0;
    for (int i = 0; i < height; i++) {
      if (i == stripeSize * (colorIndex + 1)) {
        colorIndex += 1;
      }
      for (int j = 0; j < width; j++) {
        rainbow[i][j] = colors[colorIndex];
      }
    }
    return new ImageImpl(rainbow);
  }

  /**
   * This method draws a rainbow with seven equally tall stripes in the order of red, orange,
   * yellow, green, blue, indigo, and violet. If the stripes cannot be of equal width the last
   * stripe (violet) will be up to six pixels smaller than the rest of the stripes.
   *
   * @param width  the desired width in pixels of the image.
   * @param height the desired height in pixels of the image.
   * @throws IllegalArgumentException if the given values for width and height do not allow the
   *                                  method to draw a complete rainbow (e.g. width is less than 13
   *                                  pixel tall, or if height is less than 1 pixels tall).
   */
  @Override
  public Image drawRainbowWithVerticalStripes(int width, int height)
          throws IllegalArgumentException {
    if (width < 13 || height < 1) {
      throw new IllegalArgumentException("Error: height must be greater than 0 and width must be "
              + "greater than 12");
    }
    int stripeSize = getStripeSize(width);
    int colorIndex;
    int[][][] rainbow = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      colorIndex = 0;
      for (int j = 0; j < width; j++) {
        if (j <= stripeSize * (colorIndex + 1)) {
          rainbow[i][j] = colors[colorIndex];
        } else {
          if (colorIndex == 7) {
            rainbow[i][j] = colors[colorIndex];
          }
          colorIndex += 1;
          rainbow[i][j] = colors[colorIndex];
        }
      }
    }
    return new ImageImpl(rainbow);
  }

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
  @Override
  public Image drawCheckerboard(int size) throws IllegalArgumentException {
    if (size < 8) {
      throw new IllegalArgumentException("Error: height and width must be over 8");
    }
    int[][][] checkerboard = new int[size * 8][size * 8][3];
    int checkerDown = 1;
    int checkerAcross;
    int colorIndex = 0;
    int colorAcross;
    for (int i = 0; i < checkerboard.length; i++) {
      checkerAcross = 1;
      colorAcross = colorIndex;
      if (i < size * (checkerDown)) {
        for (int j = 0; j < checkerboard[0].length; j++) {
          if (j <= size * (checkerAcross)) {
            checkerboard[i][j] = blackAndWhite[colorAcross];
          } else {
            checkerAcross += 1;
            colorAcross = colorSwitcher(colorAcross);
            checkerboard[i][j] = blackAndWhite[colorAcross];
          }
        }
      } else {
        checkerDown += 1;
        colorIndex = colorSwitcher(colorIndex);
      }
    }
    return new ImageImpl(checkerboard);
  }

  /**
   * This method changes the colorIndex. If the index is 0, it changes to 1. If it is 1, it changes
   * to 0.
   *
   * @param colorIndex an integer that represents the color index.
   * @return an integer, the changed colorIndex.
   */
  private int colorSwitcher(int colorIndex) {
    if (colorIndex == 0) {
      colorIndex = 1;
    } else {
      colorIndex = 0;
    }
    return colorIndex;
  }

  /**
   * This method calculates how big the stripes in a rainbow should be if all 7 stripes have an
   * equal width.
   *
   * @param size the size of the image.
   * @return the integer representing the size of each stripe.
   */
  private int getStripeSize(int size) {
    if (size % 7 != 0) {
      return (size / 7) + 1;
    }
    return size / 7;
  }

}
