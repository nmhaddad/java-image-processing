import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import image.model.ImageImpl;
import image.model.ImageModel;
import image.model.ImageModelImpl;

import static image.model.ImageUtil.readImage;
import static image.model.ImageUtil.writeImage;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;

/**
 * A JUnit test class for the image model interface and its classes.
 */
public class ImageModelTest {

  private ImageModel test;
  private ImageModel cat;

  private List<String> inFiles = new ArrayList<>();
  private List<String> outFiles = new ArrayList<>();

  @Before
  public void setUp() throws IOException {
    test = new ImageModelImpl();
    cat = new ImageModelImpl("res/cat.jpg");
    inFiles.add("res/cat.jpg");
    inFiles.add("res/grandpa.jpg");
    inFiles.add("res/landscape.jpg");
    inFiles.add("res/light.jpg");
    inFiles.add("res/manhattan-small.png");
    inFiles.add("res/noise.jpg");
    inFiles.add("res/prechickensharpened.jpg");
    inFiles.add("res/prechieckeblur.jpg");
    inFiles.add("res/santa.jpg");
  }

  // ****************
  // TEST CONSTRUCTOR
  // ****************

  @Test
  public void testImageModelImpl() {
    assertEquals(1, test.getImage().length);
    assertEquals(1, test.getImage()[0].length);
    assertEquals(3, test.getImage()[0][0].length);
  }

  @Test
  public void testImageModelImplParameter() throws IOException {
    try {
      int[][][] testImage = readImage("res/manhattan-small.png");
      ImageModel testParameter = new ImageModelImpl();
      testParameter.loadImage(new ImageImpl(testImage));
      assertEquals(200, testParameter.getImage().length);
      assertEquals(500, testParameter.getImage()[0].length);
      assertEquals(3, testParameter.getImage()[0][0].length);
    } catch (IOException e) {
      throw new IOException("Error: could not complete operation", e);
    }
  }

  // *********
  // TEST BLUR
  // *********

  @Test
  public void testBlur() throws IOException {
    outFiles.add("res/blur_cat.jpg");
    outFiles.add("res/blur_grandpa.jpg");
    outFiles.add("res/blur_landscape.jpg");
    outFiles.add("res/blur_light.jpg");
    outFiles.add("res/blur_manhattan-small.jpg");
    outFiles.add("res/blur_noise.jpg");
    outFiles.add("res/blur_prechickensharpened.jpg");
    outFiles.add("res/blur_prechickenblur.jpg");
    outFiles.add("res/blur_santa.jpg");
    for (int i = 0; i < inFiles.size(); i++) {
      try {
        int[][][] testImage = readImage(inFiles.get(i));
        ImageModel test = new ImageModelImpl();
        test.loadImage(new ImageImpl(testImage));
        test.blur();
        writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(), outFiles.get(i));
      } catch (IOException e) {
        throw new IOException("Error: could not complete given operations", e);
      }
      assertTrue(new File(outFiles.get(i)).isFile());
    }
  }

  // ************
  // TEST SHARPEN
  // ************

  @Test
  public void testSharpen() throws IOException {
    outFiles.add("res/sharpen_cat.jpg");
    outFiles.add("res/sharpen_grandpa.jpg");
    outFiles.add("res/sharpen_landscape.jpg");
    outFiles.add("res/sharpen_light.jpg");
    outFiles.add("res/sharpen_manhattan-small.jpg");
    outFiles.add("res/sharpen_noise.jpg");
    outFiles.add("res/sharpen_prechickensharpened.jpg");
    outFiles.add("res/sharpen_prechickenblur.jpg");
    outFiles.add("res/sharpen_santa.jpg");
    for (int i = 0; i < inFiles.size(); i++) {
      try {
        int[][][] testImage = readImage(inFiles.get(i));
        ImageModel test = new ImageModelImpl();
        test.loadImage(new ImageImpl(testImage));
        test.sharpen();
        writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(), outFiles.get(i));
      } catch (IOException e) {
        throw new IOException("Error: could not complete given operations", e);
      }
      assertTrue(new File(outFiles.get(i)).isFile());
    }
  }

  // **********
  // TEST SEPIA
  // **********

  @Test
  public void testToSepia() throws IOException {
    outFiles.add("res/sepia_cat.jpg");
    outFiles.add("res/sepia_grandpa.jpg");
    outFiles.add("res/sepia_landscape.jpg");
    outFiles.add("res/sepia_light.jpg");
    outFiles.add("res/sepia_manhattan-small.jpg");
    outFiles.add("res/sepia_noise.jpg");
    outFiles.add("res/sepia_prechickensharpened.jpg");
    outFiles.add("res/sepia_prechickenblur.jpg");
    outFiles.add("res/sepia_santa.jpg");
    for (int i = 0; i < inFiles.size(); i++) {
      try {
        int[][][] testImage = readImage(inFiles.get(i));
        ImageModel test = new ImageModelImpl();
        test.loadImage(new ImageImpl(testImage));
        test.toSepia();
        writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(), outFiles.get(i));
      } catch (IOException e) {
        throw new IOException("Error: could not complete given operations", e);
      }
      assertTrue(new File(outFiles.get(i)).isFile());
    }
  }

  @Test(expected = IOException.class)
  public void testSepiaException() throws IOException {
    int[][][] testImage = readImage("res/grandpaw.jpg");
    ImageModel test = new ImageModelImpl();
    test.loadImage(new ImageImpl(testImage));
    test.toSepia();
    writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(),
            "res/grandpawsepia.jpg");
    fail("This should not have passed");
  }

  // **************
  // TEST GREYSCALE
  // **************

  @Test
  public void testToGreyscale() throws IOException {
    outFiles.add("res/greyscale_cat.jpg");
    outFiles.add("res/greyscale_grandpa.jpg");
    outFiles.add("res/greyscale_landscape.jpg");
    outFiles.add("res/greyscale_light.jpg");
    outFiles.add("res/greyscale_manhattan-small.jpg");
    outFiles.add("res/greyscale_noise.jpg");
    outFiles.add("res/greyscale_prechickensharpened.jpg");
    outFiles.add("res/greyscale_prechickenblur.jpg");
    outFiles.add("res/greyscale_santa.jpg");
    for (int i = 0; i < inFiles.size(); i++) {
      try {
        int[][][] testImage = readImage(inFiles.get(i));
        ImageModel test = new ImageModelImpl();
        test.loadImage(new ImageImpl(testImage));
        test.toGreyscale();
        writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(), outFiles.get(i));
      } catch (IOException e) {
        throw new IOException("Error: could not complete given operations", e);
      }
      assertTrue(new File(outFiles.get(i)).isFile());
    }
  }

  @Test(expected = IOException.class)
  public void testGreyscaleException() throws IOException {
    int[][][] testImage = readImage("res/grandpaw.jpg");
    ImageModel test = new ImageModelImpl();
    test.loadImage(new ImageImpl(testImage));
    test.toGreyscale();
    writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(),
            "res/grandpagrey.jpg");
    fail("This should not have passed");
  }

  // *****************
  // TEST CHECKERBOARD
  // *****************

  @Test
  public void testDrawCheckerboard() throws IOException {
    try {
      test.drawCheckerboard(1000);
      writeImage(test.getImage(), 1000, 1000, "res/checkerboard.jpg");
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operations", e);
    }
    assertTrue(new File("res/checkerboard.jpg").isFile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawCheckerboardExceptionOne() {
    test.drawCheckerboard(0);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawCheckerboardExceptionTwo() {
    test.drawCheckerboard(7);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawCheckerboardExceptionThree() {
    test.drawCheckerboard(-1);
    fail("This test should not have passed");
  }

  // *********************
  // TEST VERTICAL RAINBOW
  // *********************

  @Test
  public void testDrawRainbowWithVerticalStripes() throws IOException {
    try {
      test.drawRainbowWithVerticalStripes(1000, 1000);
      writeImage(test.getImage(), 1000, 1000, "res/vrainbow.jpg");
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operations", e);
    }
    assertTrue(new File("res/vrainbow.jpg").isFile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawRainbowWithVerticalStripesExceptionOne() {
    test.drawRainbowWithVerticalStripes(0, 0);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawRainbowWithVerticalStripesExceptionTwo() {
    test.drawRainbowWithVerticalStripes(7, 0);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawRainbowWithVerticalStripesExceptionThree() {
    test.drawRainbowWithVerticalStripes(6, 1);
    fail("This test should not have passed");
  }

  // ***********************
  // TEST HORIZONTAL RAINBOW
  // ***********************

  @Test
  public void testDrawRainbowWithHorizontalStripes() throws IOException {
    try {
      test.drawRainbowWithHorizontalStripes(13, 13);
      writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(),
              "res/hrainbow.jpg");
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operations", e);
    }
    assertTrue(new File("res/hrainbow.jpg").isFile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawRainbowWithHorizontalStripesExceptionOne() {
    test.drawRainbowWithHorizontalStripes(0, 0);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawRainbowWithHorizontalStripesExceptionTwo() {
    test.drawRainbowWithHorizontalStripes(0, 7);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawRainbowWithHorizontalStripesExceptionThree() {
    test.drawRainbowWithHorizontalStripes(1, 6);
    fail("This test should not have passed");
  }

  // ****************
  // TEST FRENCH FLAG
  // ****************

  @Test
  public void testDrawFrenchFlag() throws IOException {
    try {
      test.drawFlag("France", 300);
      writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(),
              "res/france.jpg");
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operations", e);
    }
    assertTrue(new File("res/france.jpg").isFile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawFrenchFlagExceptionOne() {
    test.drawFlag("France", 0);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawFrenchFlagExceptionTwo() {
    test.drawFlag("France", 2);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawFrenchFlagExceptionThree() {
    test.drawFlag("France", -1);
    fail("This test should not have passed");
  }

  // ***************
  // TEST GREEK FLAG
  // ***************

  @Test
  public void testDrawGreekFlag() throws IOException {
    try {
      test.drawFlag("Greece", 1000);
      writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(),
              "res/greece.jpg");
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operations", e);
    }
    assertTrue(new File("res/greece.jpg").isFile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawGreekFlagExceptionOne() {
    test.drawFlag("Greece", 0);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawGreekFlagExceptionTwo() {
    test.drawFlag("Greece", 11);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawGreekFlagExceptionThree() {
    test.drawFlag("Greece", -1);
    fail("This test should not have passed");
  }

  // ***************
  // TEST SWISS FLAG
  // ***************

  @Test
  public void testDrawSwissFlag() throws IOException {
    try {
      test.drawFlag("Switzerland", 1000);
      writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(),
              "res/swiss.jpg");
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operations", e);
    }
    assertTrue(new File("res/swiss.jpg").isFile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawSwissFlagExceptionOne() {
    test.drawFlag("Switzerland", 0);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawSwissFlagExceptionTwo() {
    test.drawFlag("Switzerland", 5);
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDrawSwissFlagExceptionThree() {
    test.drawFlag("Switzerland", -1);
    fail("This test should not have passed");
  }

  // ***********
  // TEST DITHER
  // ***********

  @Test
  public void testDither() throws IOException {
    outFiles.add("res/dither_cat.jpg");
    outFiles.add("res/dither_grandpa.jpg");
    outFiles.add("res/dither_landscape.jpg");
    outFiles.add("res/dither_light.jpg");
    outFiles.add("res/dither_manhattan-small.jpg");
    outFiles.add("res/dither_noise.jpg");
    outFiles.add("res/dither_prechickensharpened.jpg");
    outFiles.add("res/dither_prechickenblur.jpg");
    outFiles.add("res/dither_santa.jpg");
    for (int i = 0; i < inFiles.size(); i++) {
      try {
        int[][][] testImage = readImage(inFiles.get(i));
        ImageModel test = new ImageModelImpl();
        test.loadImage(new ImageImpl(testImage));
        test.dither();
        writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(), outFiles.get(i));
      } catch (IOException e) {
        throw new IOException("Error: could not complete given operations", e);
      }
      assertTrue(new File(outFiles.get(i)).isFile());
    }
  }

  // ***********
  // TEST MOSAIC
  // ***********

  @Test
  public void testMosaic() throws IOException {
    outFiles.add("res/mosaic_cat.jpg");
    outFiles.add("res/mosaic_grandpa.jpg");
    outFiles.add("res/mosaic_landscape.jpg");
    outFiles.add("res/mosaic_light.jpg");
    outFiles.add("res/mosaic_manhattan-small.jpg");
    outFiles.add("res/mosaic_noise.jpg");
    outFiles.add("res/mosaic_prechickensharpened.jpg");
    outFiles.add("res/mosaic_prechickenblur.jpg");
    outFiles.add("res/mosaic_santa.jpg");
    for (int i = 0; i < inFiles.size(); i++) {
      try {
        int[][][] testImage = readImage(inFiles.get(i));
        ImageModel test = new ImageModelImpl();
        test.loadImage(new ImageImpl(testImage));
        test.mosaic(100);
        writeImage(test.getImage(), test.getImageWidth(), test.getImageHeight(), outFiles.get(i));
      } catch (IOException e) {
        throw new IOException("Error: could not complete given operations", e);
      }
      assertTrue(new File(outFiles.get(i)).isFile());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicNegativeException() throws IOException {
    int[][][] testImage = readImage("res/manhattan-small.png");
    ImageModel testModel = new ImageModelImpl();
    testModel.loadImage(new ImageImpl(testImage));
    testModel.mosaic(-1);
    fail("This test should not have passed");
  }

  // *********
  // TEST UNDO
  // *********

  @Test
  public void testOneUndo() throws IOException {
    test.loadImage(new ImageImpl("res/cat.jpg"));
    test.blur();
    test.undo();
    int[][][] testArray = test.getImage();
    int[][][] catArray = readImage("res/cat.jpg");
    assertArrayEquals(testArray, catArray);
  }

  @Test
  public void testInductiveUndo() throws IOException {
    test.loadImage(new ImageImpl("res/cat.jpg"));
    test.blur();
    test.mosaic(100);
    test.undo();
    test.undo();
    int[][][] testArray = test.getImage();
    int[][][] catArray = readImage("res/cat.jpg");
    assertArrayEquals(testArray, catArray);
  }

  // *********
  // TEST REDO
  // *********

  @Test
  public void testOneRedo() throws IOException {
    test.loadImage(new ImageImpl("res/cat.jpg"));
    test.blur();
    test.undo();
    test.redo();
    int[][][] testArray = test.getImage();
    cat.blur();
    int[][][] catArray = cat.getImage();
    assertArrayEquals(testArray, catArray);
  }

  @Test
  public void testInductiveRedo() throws IOException {
    test.loadImage(new ImageImpl("res/cat.jpg"));
    test.blur();
    test.undo();
    test.dither();
    test.undo();
    test.redo();
    int[][][] testArray = test.getImage();
    cat.dither();
    int[][][] catArray = cat.getImage();
    assertArrayEquals(testArray, catArray);
  }

}