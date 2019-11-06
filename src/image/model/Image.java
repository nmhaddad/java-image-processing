package image.model;

/**
 * This interface represents an image. It is used to store the pixel values of a given image. It
 * contains methods to get the image value, represented by an integer array of pixels in the
 * following format: [[[a,b,c],[e,f,g]],[[h, * i,j],[k,l,m]]]. It also contains methods to get the
 * image height and width, and a to string method for the image array.
 */
public interface Image {

  /**
   * This method gets the integer array value for this image.
   *
   * @return the integer array value for this image in the following format: [[[a,b,c],[e,f,g]],
   * [[h,i,j],[k,l,m]]].
   */
  int[][][] getImage();

  /**
   * This method gets the integer value for this image's width (how many pixels wide the image is).
   *
   * @return the integer value width of this image.
   */
  int getImageWidth();

  /**
   * This method gets the integer value for this image's height (how many pixels tall the image
   * is).
   *
   * @return the integer value height of this image.
   */
  int getImageHeight();

}
