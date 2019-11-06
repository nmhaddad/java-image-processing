package image.model.transformation;

import image.model.Image;
import image.model.ImageImpl;

/**
 * This class represents the implementation of the image filter interface. It is used to apply a
 * series of filters to a given image, represented by an array of integer values. Each index of the
 * array represents the pixel at that location of the image. The transformations include greyscale
 * and sepia. The class can also draw horizontal and vertical stripes and a checkerboard pattern.
 */
public class ImageTransformationImpl implements ImageTransformation {

  @Override
  public Image applyTransformation(Image image, double[][] matrix) {
    int width = image.getImageWidth();
    int height = image.getImageHeight();
    int[][][] newImage = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < 3; k++) {
          newImage[i][j][k] = clamp((int) Math.round(transform(image.getImage()[i][j], matrix[k])));
        }
      }
    }
    return new ImageImpl(newImage);
  }

  /**
   * This helper method is used to apply a color transformation to a pixel. Transformations are
   * applied using matrix multiplication with the given pixel integer, and filter integer matrix.
   *
   * @param pixel  the integer value for the given pixel.
   * @param filter the integer matrix that will be used to transform the value of the given pixel.
   * @return the new value of the given pixel after transformation.
   */
  private double transform(int[] pixel, double[] filter) {
    double newPixel = 0;
    for (int i = 0; i < filter.length; i++) {
      newPixel += pixel[i] * filter[i];
    }
    return newPixel;
  }

  /**
   * This method ensures that a pixel color is never out of range by rounding a value less than 0 to
   * 0 and rounding a number greater than 255 to 255.
   *
   * @param pixel the pixel that will be clamped.
   */
  private int clamp(int pixel) {
    int min = 0;
    int max = 255;
    if (pixel < min) {
      pixel = min;
    } else if (pixel > max) {
      pixel = max;
    }
    return pixel;
  }

}
