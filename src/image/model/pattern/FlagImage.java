package image.model.pattern;

import image.model.Image;

/**
 * This interface represents a flag image. It contains a method to draw a flag with the given
 * country and size. Helper methods in its implementation draw the given country.
 */
public interface FlagImage {

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
  Image drawFlag(String country, int size) throws IllegalArgumentException;

}
