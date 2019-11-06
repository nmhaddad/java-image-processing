package image.model;

/**
 * This interface represents the model for this program. It includes operations that transform an
 * array of pixels to the result of an applied operation and methods that apply a filter to an
 * image. These methods include blur, sharpen, converting an image to greyscale and sepia, and
 * drawing horizontal and vertical lines of various colors.
 */
public interface ImageModel {

  /**
   * This method gets the image in an integer array. The integer array is formatted as
   * [[[a,b,c],[e,f,g]], [[h,i,j],[k,l,m]]].
   *
   * @return integer array formatted as [[[a,b,c],[e,f,g]], [[h,i,j],[k,l, m]]].
   */
  int[][][] getImage();

  /**
   * This method gets the width of the image as an integer.
   *
   * @return an integer representing the width of an integer.
   */
  int getImageWidth();

  /**
   * This method gets the height of the image as an integer.
   *
   * @return an integer representing the height of an integer.
   */
  int getImageHeight();

  /**
   * This method applies a blur filter to a given image by transforming the provided pixels using
   * the applied filter.
   *
   * @throws IllegalArgumentException if the given filter is does not have odd dimensions or it is
   *                                  bigger than the image.
   */
  void blur() throws IllegalArgumentException;

  /**
   * This method applies a sharpen filter to a given image by transforming the provided pixels using
   * the applied filter.
   *
   * @throws IllegalArgumentException if the given filter is does not have odd dimensions or it is
   *                                  bigger than the image.
   */
  void sharpen() throws IllegalArgumentException;

  /**
   * This method applies the greyscale filter to a given image by transforming each individual
   * pixel's color channels.
   */
  void toGreyscale();

  /**
   * This method applies the sepia filter to a given image by transforming each individual pixel's
   * color channels.
   */
  void toSepia();

  /**
   * This method draws a rainbow with seven equally wide stripes in the order of red, orange,
   * yellow, green, blue, indigo, and violet. If the stripes cannot be of equal width the last
   * stripe (violet) will be up to six pixels smaller than the rest of the stripes.
   *
   * @param width  the desired width in pixels of the image.
   * @param height the desired height in pixels of the image.
   * @throws IllegalArgumentException if the given values for width and height do not allow the
   *                                  method to draw a complete rainbow (e.g. width is less than 1
   *                                  pixel tall, or if height is less than seven pixels tall.
   */
  void drawRainbowWithHorizontalStripes(int width, int height) throws IllegalArgumentException;

  /**
   * This method draws a rainbow with seven equally tall stripes in the order of red, orange,
   * yellow, green, blue, indigo, and violet. If the stripes cannot be of equal width the last
   * stripe (violet) will be up to six pixels smaller than the rest of the stripes.
   *
   * @param width  the desired width in pixels of the image.
   * @param height the desired height in pixels of the image.
   * @throws IllegalArgumentException if the given values for width and height do not allow the
   *                                  method to draw a complete rainbow (e.g. width is less than 7
   *                                  pixel tall, or if height is less than 1 pixels tall.
   */
  void drawRainbowWithVerticalStripes(int width, int height) throws IllegalArgumentException;

  /**
   * This method draws three possible flags, France, Switzerland, and Greece. Each flag will be
   * drawn in proportion to the size given.
   *
   * @param country the country's flag to be drawn, can only be France, Switzerland, or Greece.
   * @param size    the desired width in pixels of the image.
   * @throws IllegalArgumentException if the given value for size does not allow the method to draw
   *                                  the given country's flag (these differ between countries - see
   *                                  the implementation for more information.
   */
  void drawFlag(String country, int size) throws IllegalArgumentException;

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
  void drawCheckerboard(final int size) throws IllegalArgumentException;

  /**
   * This method uses a dither effect on an image. Dither takes an image and creates a dot pattern
   * using black and white pixels. It does this by first converting the image to greyscale, and then
   * iterating of the image and changing pixels to greyscale values.
   */
  void dither();

  /**
   * This method uses a mosaic effect on an existing image to create a new image. It uses a seed
   * value to select a number of random pixels in an image, and then performs a "closest-neighbor"
   * algorithm on the entire image. The pixels that are closest to the randomized seeds take on the
   * color values of that seed. The result that is produced is a collection of colored tiles made up
   * from the randomized seed colors.
   *
   * @param seeds the number of tiles the image will use to create the mosaic.
   * @throws IllegalArgumentException if the area of the image is smaller than the number of seeds.
   *                                  The algorithm cannot proceed if there are more seeds than
   *                                  available pixels.
   */
  void mosaic(final int seeds) throws IllegalArgumentException;

  /**
   * This method is used to load an image to this model. It takes the given image and assigns it to
   * this image model's image value.
   *
   * @param image the given image value.
   */
  void loadImage(Image image);

  /**
   * This method is an undo method for applying different types of operations on
   * an image that this model processes. It uses an undo and redo stack to keep
   * track of the images that are manipulated throughout the life of this
   * model.
   *
   * @throws IllegalStateException if the stack is empty.
   */
  void undo();

  /**
   * This method is an redo method for applying different types of operations on
   * an image that this model processes. It uses an undo and redo stack to keep
   * track of the images that are manipulated throughout the life of this
   * model.
   *
   * @throws IllegalStateException if the stack is empty.
   */
  void redo();


}