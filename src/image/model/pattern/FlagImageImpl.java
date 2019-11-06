package image.model.pattern;

import image.model.Image;
import image.model.ImageImpl;

/**
 * This class represents the implementation of the flag image interface. It contains methods that
 * draw the given flag. Flags include Switzerland, France, and Greece. Helper methods abstract
 * repeating code.
 */
public class FlagImageImpl implements FlagImage {

  /**
   * This method draws three possible flags, France, Switzerland, and Greece. Countries must be
   * input staring with a capital letter followed by all lowercase letters. Each flag will be drawn
   * in proportion to the size given.
   *
   * @param country the country's flag to be drawn, can only be France, Switzerland, or Greece.
   * @param size    the desired width in pixels of the image.
   * @throws IllegalArgumentException if the given value for size does not allow the method to draw
   *                                  the given country's flag (these differ between countries - see
   *                                  the implementation for more information.
   */
  @Override
  public Image drawFlag(String country, int size) throws IllegalArgumentException {
    if (country.equals("France")) {
      return this.drawFrenchFlag(size);
    } else if (country.equals("Greece")) {
      return this.drawGreekFlag(size);
    } else if (country.equals("Switzerland")) {
      return this.drawSwissFlag(size);
    } else {
      throw new IllegalArgumentException("Error: invalid country name.");
    }
  }

  /**
   * This method draws the French flag. It takes in size which denotes the height or the width. Only
   * one size is taken in o the method can calculate proportions.
   *
   * @param size the width of the flag.
   * @throws IllegalArgumentException if the size is not a multiple of three and size is less than
   *                                  three.
   */
  private Image drawFrenchFlag(int size) throws IllegalArgumentException {
    if (size % 3 != 0 || size < 3) {
      throw new IllegalArgumentException("Error: width must be a multiple of 3 and at least 3");
    }
    int stripes = (int) Math.ceil(size / 3.0);
    int height = (int) Math.ceil(size * 0.66);
    int[][][] flag = new int[height][size][3];
    int[][] colors = {{0, 35, 149}, {255, 255, 255}, {237, 41, 57}};
    int colorIndex;
    int barColor;
    for (int i = 0; i < height; i++) {
      barColor = 1;
      colorIndex = 0;
      for (int j = 0; j < size; j++) {
        if (j < stripes * (barColor)) {
          flag[i][j] = colors[colorIndex];
        } else {
          barColor += 1;
          colorIndex += 1;
          flag[i][j] = colors[colorIndex];
        }
      }
    }
    return new ImageImpl(flag);
  }

  /**
   * This method draws the Greek flag. It takes in size which denotes the height or the width. Only
   * one size is taken in o the method can calculate proportions.
   *
   * @param size the width of the flag.
   * @throws IllegalArgumentException if the size is less than 12.
   */
  private Image drawGreekFlag(int size) throws IllegalArgumentException {
    if (size < 12) {
      throw new IllegalArgumentException("Error: width must be over 12");
    }
    int height = (int) Math.ceil(size * 0.66);
    int stripes = (int) Math.floor(height / 9.0);
    height = stripes * 9;
    int[][][] flag = new int[height][size][3];
    int[][] colors = {{13, 94, 175}, {255, 255, 255}};
    int colorIndex = 0;
    int barColor = 1;
    for (int i = 0; i < height; i++) {
      if (i < stripes * (barColor)) {
        for (int j = 0; j < size; j++) {
          flag[i][j] = colors[colorIndex];
        }
      } else {
        if (barColor < 9) {
          barColor += 1;
          colorIndex = colorSwitcher(colorIndex);
          for (int j = 0; j < size; j++) {
            flag[i][j] = colors[colorIndex];
          }
        }
      }
    }
    for (int i = 0; i < stripes * 5.0; i++) {
      colorIndex = 0;
      for (int j = 0; j < stripes * 5.0; j++) {
        if (j < stripes * (barColor)) {
          flag[i][j] = colors[colorIndex];
        }
      }
    }
    for (int i = 0; i < stripes * 3; i++) {
      colorIndex = 1;
      if (i >= stripes * 2 && i < stripes * 5) {
        for (int j = 0; j < stripes * 5; j++) {
          flag[i][j] = colors[colorIndex];
        }
      }
    }
    for (int i = 0; i < stripes * 5; i++) {
      colorIndex = 1;
      for (int j = 0; j < stripes * 3; j++) {
        if (j >= stripes * 2 && j < stripes * 3) {
          flag[i][j] = colors[colorIndex];
        }
      }
    }
    return new ImageImpl(flag);
  }

  /**
   * This method draws the Swiss flag. It takes in size which denotes the height or the width. Only
   * one size is taken in o the method can calculate proportions.
   *
   * @param size the width or the height of the flag.
   * @throws IllegalArgumentException if the size is less than g.
   */
  private Image drawSwissFlag(int size) throws IllegalArgumentException {
    if (size < 6) {
      throw new IllegalArgumentException("Error: width must be over 6");
    }
    int stripeStart = (int) Math.floor(size / 6.0);
    int[][][] flag = new int[size][size][3];
    int[][] colors = {{232, 27, 0}, {255, 255, 255}};
    for (int i = 0; i < flag.length; i++) {
      for (int j = 0; j < flag[0].length; j++) {
        flag[i][j] = colors[0];
      }
    }
    for (int i = 0; i < flag.length; i++) {
      if (i >= stripeStart && i < size - stripeStart) {
        for (int j = 0; j < flag[0].length; j++) {
          if (j >= stripeStart * 2.5 && j < size - stripeStart * 2.5) {
            flag[i][j] = colors[1];
          }
        }
      }
    }
    for (int i = 0; i < flag.length; i++) {
      if (i >= stripeStart * 2.5 && i < size - (stripeStart * 2.5)) {
        for (int j = 0; j < flag[0].length; j++) {
          if (j >= stripeStart && j < size - stripeStart) {
            flag[i][j] = colors[1];
          }
        }
      }
    }
    return new ImageImpl(flag);
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

}
