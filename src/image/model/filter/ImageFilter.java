package image.model.filter;

import image.model.Image;

/**
 * This interface represents an image filter. It is used to apply a series of filters to a given
 * image, represented by an array of integer values. Each index of the array represents the pixel at
 * that location of the image. The filters include blur and sharpen, mosaic, and dither. Blur and
 * sharpen use the applyFilter method that can take in any matrix filter, while dither and mosaic
 * have unique applications with their own methods.
 */
public interface ImageFilter {

  /**
   * This method applies a blur or a sharpen filter to a given image by transforming the provided
   * pixels using the given filter. Using helper methods, the apply filter method gets the kernel of
   * an part of an image and creates a matrix of its neighboring values. It then multiples each item
   * in the matrix with the corresponding value in the filter matrix. The new pixel is created from
   * the sum of this operation. This method creates a new image from the operation performed.
   *
   * @param image  the image that the filter will be applied to.
   * @param filter the filter that is applied to the image (e.g. blur or sharpen).
   * @return a new image object that includes an array of pixels that represent the blurred or
   *         sharpened image formatted as [[[a,b,c],[e,f,g]],[[h,i,j],[k,l,m]]].
   */
  Image applyFilter(Image image, double[][] filter);

  /**
   * This method uses a dither effect on an image. Dither takes an image and creates a dot pattern
   * using black and white pixels. It does this by first converting the image to greyscale, and then
   * iterating of the image and changing pixels to greyscale values.
   *
   * @param image the image that the dither filter will be applied to.
   * @return the new image produced by this dither filter.
   */
  Image applyDitherFilter(Image image);

  /**
   * This method uses a mosaic effect on an existing image to create a new image. It uses a seed
   * value to select a number of random pixels in an image, and then performs a "closest-neighbor"
   * algorithm on the entire image. The pixels that are closest to the randomized seeds take on the
   * color values of that seed. The result that is produced is a collection of colored tiles made up
   * from the randomized seed colors.
   *
   * @param seeds the number of tiles the image will use to create the mosaic.
   * @return a new image with the mosaic effect applied.
   * @throws IllegalArgumentException if the area of the image is smaller than the number of seeds
   *                                  or if the number of seeds is less than one. The algorithm
   *                                  cannot proceed if there are more seeds than available pixels.
   */
  Image applyMosaicFilter(Image image, final int seeds) throws IllegalArgumentException;

}
