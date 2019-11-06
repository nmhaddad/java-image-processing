package image.model;

import java.io.IOException;

import static image.model.ImageUtil.getHeight;
import static image.model.ImageUtil.getWidth;
import static image.model.ImageUtil.readImage;

/**
 * This class represents the implementation of the image interface. It is used to store the pixel
 * values of a given image. It contains methods to get the image value, represented by an integer
 * array of pixels in the following format: [[[a,b,c],[e,f,g]],[[h, * i,j],[k,l,m]]]. It also
 * contains methods to get the image height and width, and a to string method for the image array.
 */
public class ImageImpl implements Image {

  /**
   * The integer array value for this image.
   */
  private int[][][] image;

  /**
   * The integer value width for this image.
   */
  private int width;

  /**
   * The integer value height for this image.
   */
  private int height;

  /**
   * This method constructs an ImageImpl object by taking in a file name formatted as the name of
   * the file in the format of folderName/filename. It uses the file information to read the image
   * and store it as an integer array, find the height and store it as an integer, and find the
   * width and store it as an integer. If the file cannot be read an exception is thrown.
   *
   * @param filename the name of the file in the format of folderName/filename.
   * @throws IOException this exception is thrown if the file could not be loaded.
   */
  public ImageImpl(String filename) throws IOException {
    try {
      this.image = readImage(filename);
      this.width = getWidth(filename);
      this.height = getHeight(filename);
    } catch (IOException e) {
      throw new IOException("Error: could not load image from file");
    }
  }

  /**
   * This method constructs an ImageImpl object by taking in an integer array that represents the
   * pixels of an image. The array is formatted as of [[[a,b,c],[e,f,g]],[[h,i,j],[k,l,m]]], with
   * each sub array representing the height, the width, and the color channels.
   *
   * @param image the integer array that represents the pixels of an image.
   */
  public ImageImpl(int[][][] image) {
    this.image = image;
    this.width = image[0].length;
    this.height = image.length;
  }

  /**
   * This method gets the integer array value for this image.
   *
   * @return the integer array value for this image in the following format: [[[a,b,c],[e,f,g]],
   *         [[h,i,j],[k,l,m]]].
   */
  @Override
  public int[][][] getImage() {
    return this.image;
  }

  /**
   * This method gets the integer value for this image's width (how many pixels wide the image is).
   *
   * @return the integer value width of this image.
   */
  @Override
  public int getImageWidth() {
    return this.width;
  }

  /**
   * This method gets the integer value for this image's height (how many pixels tall the image
   * is).
   *
   * @return the integer value height of this image.
   */
  @Override
  public int getImageHeight() {
    return this.height;
  }

}
