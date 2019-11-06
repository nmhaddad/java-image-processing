package image.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

import image.model.filter.ImageFilterImpl;
import image.model.pattern.FlagImageImpl;
import image.model.pattern.PatternImageImpl;
import image.model.transformation.ImageTransformationImpl;

/**
 * This class represents the operations of the image model, the model interface for this program. It
 * includes operations that transform an array of pixels to the result of an applied operation and
 * methods that apply a filter to an image. These methods include blur, sharpen, converting an image
 * to greyscale and sepia, and drawing horizontal and vertical lines.
 */
public class ImageModelImpl implements ImageModel {

  /**
   * This Image object holds all the information of the image.
   */
  private Image image;

  /**
   * This stack represents actions of this model that can be undone.
   */
  private Stack<Image> undoStack = new Stack<>();

  /**
   * This stack represents actions of this model that can be redone.
   */
  private Stack<Image> redoStack = new Stack<>();

  /**
   * This method constructs an ImageModelImpl object by taking in a file name formatted as
   * foldername/filename. It uses the file information to read the image and store it as an
   * ImageImpl object. If the file cannot be read an IOException is thrown.
   *
   * @param filename the name of the file in the format of folderName/filename
   * @throws IOException this exception is thrown if the file could not be read.
   */
  public ImageModelImpl(String filename) throws IOException {
    try {
      this.image = new ImageImpl(filename);
    } catch (IOException e) {
      throw new IOException("Error: could not read from file.");
    }
  }

  /**
   * This method constructs and ImageModelImpl by creating a new imageImpl object and assigning it a
   * default integer array value of [1][1][3].
   */
  public ImageModelImpl() {
    this.image = new ImageImpl(new int[1][1][3]);
  }

  /**
   * This method gets the integer array value for this image. It is a deep copy of the array stored
   * in the image, which prevents a user from intentionally or unintentionally changing values in
   * the array through access.
   *
   * @return the integer array value for this image in the following format: [[[a,b,c],[e,f,g]],
   *         [[h,i,j],[k,l,m]]].
   */
  public int[][][] getImage() {
    int[][][] deepCopy = new int[this.image.getImageHeight()][this.image.getImageWidth()][3];
    for (int i = 0; i < this.image.getImageHeight(); i++) {
      for (int j = 0; j < this.image.getImageWidth(); j++) {
        deepCopy[i][j] = this.image.getImage()[i][j];
      }
    }
    return deepCopy;
  }

  /**
   * This method gets the integer value for this image's width (how many pixels wide the image is).
   *
   * @return the integer value width of this image.
   */
  public int getImageWidth() {
    return this.image.getImageWidth();
  }

  /**
   * This method gets the integer value for this image's height (how many pixels tall the image
   * is).
   *
   * @return the integer value height of this image.
   */
  public int getImageHeight() {
    return this.image.getImageHeight();
  }

  private void checkImage() {
    if (Arrays.deepEquals(this.getImage(), new int[1][1][3])) {
      throw new IllegalArgumentException("Error: no image loaded yet");
    }
  }

  /**
   * This method applies a blur filter to the pixels of a given image. It does this through matrix
   * multiplication, using a chunk matrix and filter matrix.
   *
   * @throws IllegalArgumentException if the given filter is does not have odd dimensions or it is
   *                                  bigger than the image.
   */
  @Override
  public void blur() throws IllegalArgumentException {
    checkImage();
    double[][] blurFilter = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    undoHelper();
    this.image = new ImageFilterImpl().applyFilter(this.image, blurFilter);
  }

  /**
   * This method applies a sharpen filter to a given image by transforming the provided pixels using
   * the applied filter.
   *
   * @throws IllegalArgumentException if the given filter is does not have odd dimensions or it is
   *                                  bigger than the image.
   */
  @Override
  public void sharpen() throws IllegalArgumentException {
    checkImage();
    double[][] sharpenFilter = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    undoHelper();
    this.image = new ImageFilterImpl().applyFilter(this.image, sharpenFilter);
  }

  /**
   * This method applies the greyscale filter to a given image by transforming each individual
   * pixel's color channels.
   */
  @Override
  public void toGreyscale() {
    checkImage();
    double[][] greyscale = {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126,
            0.7152, 0.0722}};
    undoHelper();
    this.image = new ImageTransformationImpl().applyTransformation(this.image, greyscale);
  }

  /**
   * This method applies the sepia filter to a given image by transforming each individual pixel's
   * color channels.
   */
  @Override
  public void toSepia() {
    checkImage();
    double[][] sepia = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    undoHelper();
    this.image = new ImageTransformationImpl().applyTransformation(this.image, sepia);
  }

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
  @Override
  public void drawRainbowWithHorizontalStripes(int width, int height)
          throws IllegalArgumentException {
    undoHelper();
    this.image = new PatternImageImpl().drawRainbowWithHorizontalStripes(width, height);
  }

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
  @Override
  public void drawRainbowWithVerticalStripes(int width, int height)
          throws IllegalArgumentException {
    undoHelper();
    this.image = new PatternImageImpl().drawRainbowWithVerticalStripes(width, height);
  }

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
  public void drawFlag(String country, int size) throws IllegalArgumentException {
    undoHelper();
    this.image = new FlagImageImpl().drawFlag(country, size);
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
  public void drawCheckerboard(final int size) throws IllegalArgumentException {
    undoHelper();
    this.image = new PatternImageImpl().drawCheckerboard(size);

  }

  /**
   * This method uses a dither effect on an image. Dither takes an image and creates a dot pattern
   * using black and white pixels. It does this by first converting the image to greyscale, and then
   * iterating of the image and changing pixels to greyscale values.
   */
  @Override
  public void dither() {
    checkImage();
    undoHelper();
    this.toGreyscale();
    undoStack.pop();
    this.image = new ImageFilterImpl().applyDitherFilter(this.image);
  }

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
  @Override
  public void mosaic(final int seeds) throws IllegalArgumentException {
    checkImage();
    undoHelper();
    this.image = new ImageFilterImpl().applyMosaicFilter(this.image, seeds);

  }

  /**
   * This method is used to load an image to this model. It takes the given image and assigns it to
   * this image model's image value.
   *
   * @param image the given image value.
   */
  @Override
  public void loadImage(Image image) {
    undoHelper();
    this.image = image;
  }

  private void undoHelper() {
    undoStack.push(this.image);
    redoStack = new Stack<>();
  }

  /**
   * This method is an undo method for applying different types of operations on an image that this
   * model processes. It uses an undo and redo stack to keep track of the images that are
   * manipulated throughout the life of this model.
   *
   * @throws IllegalStateException if the stack is empty.
   */
  @Override
  public void undo() throws IllegalStateException {
    if (undoStack.isEmpty()) {
      throw new IllegalStateException("Error: nothing to undo");
    }
    redoStack.push(this.image);
    this.image = undoStack.pop();
  }

  /**
   * This method is an redo method for applying different types of operations on an image that this
   * model processes. It uses an undo and redo stack to keep track of the images that are
   * manipulated throughout the life of this model.
   *
   * @throws IllegalStateException if the stack is empty.
   */
  @Override
  public void redo() {
    if (redoStack.isEmpty()) {
      throw new IllegalStateException("Error: nothing to redo");
    }
    undoStack.push(this.image);
    this.image = redoStack.pop();
  }

}
