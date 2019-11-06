package image.model.transformation;

import image.model.Image;

/**
 * This interface represents transformation operations that can be made to a image. It contains one
 * method that applies a matrix to the given matrix.
 */
public interface ImageTransformation {

  /**
   * This method applies a matrix to an image that transforms each pixel.
   *
   * @param image  the given image.
   * @param matrix the given matrix that will be applied to the given image.
   * @return the result of the transformation represented as a new image array of pixels.
   */
  Image applyTransformation(Image image, double[][] matrix);

}
