package image.model.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import image.model.Image;
import image.model.ImageImpl;

/**
 * This class represents the implementation of the image filter interface. It is used to apply a
 * series of filters to a given image, represented by an array of integer values. Each index of the
 * array represents the pixel at that location of the image. The filters include blur, sharpen,
 * dither and mosaic.
 */
public class ImageFilterImpl implements ImageFilter {

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
   * @throws IllegalArgumentException if the given filter does not have odd dimensions and/or the
   *                                  given filter does not have equal width and height.
   */
  @Override
  public Image applyFilter(Image image, double[][] filter) throws IllegalArgumentException {
    if (filter.length % 2 == 0) {
      throw new IllegalArgumentException("Error: given filter must have odd dimensions");
    }
    if (filter.length != filter[0].length) {
      throw new IllegalArgumentException("Error: given filter must have equal width and height");
    }
    int width = image.getImageWidth();
    int height = image.getImageHeight();
    int[][][] newImage = new int[height][width][3];
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        newImage[row][column] = createNewPixel(getKernel(row, column, image.getImage(),
                filter.length), filter);
      }
    }
    return new ImageImpl(clamp(newImage));
  }

  /**
   * This helper method creates a new pixel by applying a filter matrix to a pixel matrix. It does
   * this by multiplying each pixel in the given kernel matrix by its corresponding value in the
   * filter matrix, and then summing the products in a new pixel value.
   *
   * @param kernel the pixel matrix.
   * @param filter the filter matrix.
   * @return a three new pixels that represent red, green, and blue values.
   */
  private int[] createNewPixel(int[][][] kernel, double[][] filter) {
    int[] newPixel = new int[3];
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        newPixel[0] += (int) Math.round((kernel[i][j][0] * filter[i][j]));
        newPixel[1] += (int) Math.round((kernel[i][j][1] * filter[i][j]));
        newPixel[2] += (int) Math.round((kernel[i][j][2] * filter[i][j]));
      }
    }
    return newPixel;
  }

  /**
   * This helper method is used to get a matrix of values from an image using given size of the
   * filter and a kernel location. The kernel location is represented by the given row and column
   * parameters.
   *
   * @param row    the given row value.
   * @param column the given column value.
   * @param image  the given image.
   * @param size   the filter that is being applied to the image.
   * @return a matrix with the kernel as the center point of the matrix, and its neighbors.
   */
  private int[][][] getKernel(int row, int column, int[][][] image, int size) {
    int[][][] kernel = new int[size][size][3];
    int bounds = (int) Math.floor(size / 2.0);
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        try {
          kernel[i][j][0] = image[i + (row - bounds)][j + (column - bounds)][0];
          kernel[i][j][1] = image[i + (row - bounds)][j + (column - bounds)][1];
          kernel[i][j][2] = image[i + (row - bounds)][j + (column - bounds)][2];
        } catch (IndexOutOfBoundsException e) {
          kernel[i][j][0] = 0;
          kernel[i][j][1] = 0;
          kernel[i][j][2] = 0;
        }
      }
    }
    return kernel;
  }

  /**
   * This method ensures that a pixel color is never out of range by rounding a value less than 0 to
   * 0 and rounding a number greater than 255 to 255.
   *
   * @param image a integer array of pixels.
   * @return the given integer array of pixels with all values between 0 and 255.
   */
  private int[][][] clamp(int[][][] image) {
    int min = 0;
    int max = 255;
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          if (image[i][j][k] < min) {
            image[i][j][k] = min;
          } else if (image[i][j][k] > max) {
            image[i][j][k] = max;
          }
        }
      }
    }
    return image;
  }

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
  public Image applyMosaicFilter(Image image, final int seeds) throws IllegalArgumentException {
    int[][][] imageDeepCopy = image.getImage();
    int height = image.getImageHeight();
    int width = image.getImageWidth();
    if (seeds > width * height || seeds < 1) {
      throw new IllegalArgumentException("Error: seed number too great or less than 1");
    } else if (seeds == width * height) {
      return image;
    }
    int[][][] newMosaic = new int[height][width][3];
    List<PixelImpl> centroids = getCentroids(image, seeds);
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        ArrayList<Double> distances = new ArrayList<>();
        for (int i = 0; i < seeds; i++) {
          distances.add(euclideanCalculator(column, row, centroids.get(i)));
        }
        int min = distances.indexOf(Collections.min(distances));
        centroids.get(min).giveNewColor(imageDeepCopy[row][column]);
      }
    }
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        ArrayList<Double> distances = new ArrayList<>();
        for (int i = 0; i < seeds; i++) {
          distances.add(euclideanCalculator(column, row, centroids.get(i)));
        }
        int min = distances.indexOf(Collections.min(distances));
        newMosaic[row][column] = centroids.get(min).getAverageColor();
      }
    }
    return new ImageImpl(newMosaic);
  }

  /**
   * This helper method calculates the Euclidean distance between a given data pixel and given
   * centroid. It is used in calculating the distance from each pixel to each seed in the mosaic
   * method above.
   *
   * @param column   the given pixel column.
   * @param row      the given pixel row.
   * @param centroid the given centroid pixel.
   * @return a double value of the distance between the two given pixels.
   */
  private double euclideanCalculator(int column, int row, PixelImpl centroid) {
    double x = Math.pow(column - centroid.getX(), 2);
    double y = Math.pow(row - centroid.getY(), 2);
    return Math.sqrt(x + y);
  }

  /**
   * This method selects random centroids from the given pixels and adds them to an ArrayList of
   * centroids. It is used in selecting the seeds for the mosaic method above.
   *
   * @param seeds an integer number of centroids.
   * @return a list of randomly selected centroids.
   */
  private List<PixelImpl> getCentroids(Image image, final int seeds) {
    List<PixelImpl> centroids = new ArrayList<>();
    Random rand = new Random();
    int randX = rand.nextInt(image.getImageWidth());
    int randY = rand.nextInt(image.getImageHeight());
    PixelImpl randomPixel = new PixelImpl(randX, randY, image.getImage()[randY][randX]);
    centroids.add(randomPixel);
    while (centroids.size() != seeds) {
      randX = rand.nextInt(image.getImageWidth());
      randY = rand.nextInt(image.getImageHeight());
      randomPixel = new PixelImpl(randX, randY, image.getImage()[randY][randX]);
      for (PixelImpl p : centroids) {
        if (!(randomPixel.getX() == p.getX() && randomPixel.getY() == p.getY())) {
          centroids.add(randomPixel);
          break;
        }
      }
    }
    return centroids;
  }

  /**
   * This method uses a dither effect on an image. Dither takes an image and creates a dot pattern
   * using black and white pixels. It does this by first converting the image to greyscale, and then
   * iterating of the image and changing pixels to greyscale values.
   *
   * @param image the image that the dither filter will be applied to.
   * @return the new image produced by this dither filter.
   */
  public Image applyDitherFilter(Image image) {
    int width = image.getImageWidth();
    int height = image.getImageHeight();
    int[][][] newImage = image.getImage();
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        int oldColor = newImage[row][column][0];
        int newColor = colorHelper(oldColor);
        int error = oldColor - newColor;
        newImage[row][column] = new int[]{newColor, newColor, newColor};
        try {
          int errorOne = newImage[row][column + 1][0] + (int) Math.round(0.4375 * error);
          newImage[row][column + 1] = new int[]{errorOne, errorOne, errorOne};
        } catch (ArrayIndexOutOfBoundsException ignored) {
          // No comment necessary.
        }
        try {
          int errorTwo = newImage[row + 1][column - 1][0] + (int) Math.round(0.1875 * error);
          newImage[row + 1][column - 1] = new int[]{errorTwo, errorTwo, errorTwo};
        } catch (ArrayIndexOutOfBoundsException ignored) {
          // No comment necessary.
        }
        try {
          int errorThree = (int) Math.round(0.3125 * error) + newImage[row + 1][column][0];
          newImage[row + 1][column] = new int[]{errorThree, errorThree, errorThree};
        } catch (ArrayIndexOutOfBoundsException ignored) {
          // No comment necessary.
        }
        try {
          int errorFour = (int) Math.round(0.0625 * error) + newImage[row + 1][column + 1][0];
          newImage[row + 1][column + 1] = new int[]{errorFour, errorFour, errorFour};
        } catch (ArrayIndexOutOfBoundsException ignored) {
          // No comment necessary.
        }
      }
    }
    return new ImageImpl(newImage);
  }

  /**
   * This method is a helper method for the dither method. It checks to see if a color is closer to
   * the minimum or maximum color value (0-255) and gets the closest value.
   *
   * @param color the integer value for a color.
   * @return the closest value to the color, minimum or maximum integer value.
   */
  private int colorHelper(int color) {
    if (255 - color > color) {
      return 0;
    }
    return 255;
  }

}
