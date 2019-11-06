import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import image.control.ImageController;
import image.control.ImageControllerImpl;
import image.model.ImageImpl;
import image.model.ImageModel;
import image.model.ImageModelImpl;

import static image.model.ImageUtil.readImage;
import static image.model.ImageUtil.writeImage;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;

/**
 * A JUnit test class for the image controller interface and its classes.
 */
public class ImageControllerImplTest {

  private ImageModel grandpaM;
  private ImageModel catM;
  private ImageModel testModel;

  @Before
  public void setup() throws IOException {
    grandpaM = new ImageModelImpl();
    int[][][] grandpaImage = readImage("res/grandpa.jpg");
    grandpaM.loadImage(new ImageImpl(grandpaImage));

    catM = new ImageModelImpl();
    int[][][] catImage = readImage("res/cat.jpg");
    catM.loadImage(new ImageImpl(catImage));

    testModel = new ImageModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionLoad() throws IOException {
    Reader testInput = new FileReader("res/exceptionLoad.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    test.begin();
    fail("This test should not have passed");
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionSave() throws IOException {
    Reader testInput = new FileReader("res/exceptionSave.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    test.begin();
    fail("Test should have thrown an exception");
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionUnsupportedAction() throws IOException {
    Reader testInput = new FileReader("res/exceptionSave.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    test.begin();
    fail("Test should have thrown an exception");
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionFlag() throws IOException {
    Reader testInput = new FileReader("res/exceptionFlag.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    test.begin();
    fail("Test should have thrown an exception");
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionRainbow() throws IOException {
    Reader testInput = new FileReader("res/exceptionRainbow.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    test.begin();
    fail("Test should have thrown an exception");
  }


  @Test
  public void oneMethodTest() throws IOException {
    grandpaM.toGreyscale();
    writeImage(grandpaM.getImage(), grandpaM.getImageWidth(), grandpaM.getImageHeight(),
            "res/grandpaTest.jpg");
    Reader testInput = new FileReader("res/oneMethodTest.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/grandpaTest.jpg");
    int[][][] controllerArray = readImage("res/grandpagrey2.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void oneMethodTestCat() throws IOException {
    catM.toGreyscale();
    writeImage(catM.getImage(), catM.getImageWidth(), catM.getImageHeight(),
            "res/catTest.jpg");
    Reader testInput = new FileReader("res/oneMethodTestCat.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/catTest.jpg");
    int[][][] controllerArray = readImage("res/catgrey2.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void twoMethodsTest() throws IOException {
    grandpaM.toSepia();
    grandpaM.sharpen();
    writeImage(grandpaM.getImage(), grandpaM.getImageWidth(), grandpaM.getImageHeight(),
            "res/grandpaTwoTest.jpg");
    Reader testInput = new FileReader("res/twoMethodsTest.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/grandpaTwoTest.jpg");
    int[][][] controllerArray = readImage("res/grandpa2.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void multipleMethodsTest() throws IOException {
    grandpaM.toSepia();
    grandpaM.blur();
    grandpaM.sharpen();
    grandpaM.dither();
    grandpaM.toGreyscale();
    writeImage(grandpaM.getImage(), grandpaM.getImageWidth(), grandpaM.getImageHeight(),
            "res/grandpaMultiMethodTest.jpg");
    Reader testInput = new FileReader("res/multipleMethodsTest.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/grandpaMultiMethodTest.jpg");
    int[][][] controllerArray = readImage("res/grandpamulti.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void allOneMethodTest() throws IOException {
    grandpaM.blur();
    grandpaM.blur();
    grandpaM.blur();
    grandpaM.blur();
    writeImage(grandpaM.getImage(), grandpaM.getImageWidth(), grandpaM.getImageHeight(),
            "res/grandpaBlurTest.jpg");
    Reader testInput = new FileReader("res/allOneMethodTest.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/grandpaBlurTest.jpg");
    int[][][] controllerArray = readImage("res/grandpablurall.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void multiImageTest() throws IOException {
    grandpaM.blur();
    writeImage(grandpaM.getImage(), grandpaM.getImageWidth(), grandpaM.getImageHeight(),
            "res/grandpamulti.jpg");
    catM.sharpen();
    writeImage(catM.getImage(), catM.getImageWidth(), catM.getImageHeight(),
            "res/catmulti.jpg");
    Reader testInput = new FileReader("res/multiImageTest.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArrayGrandpa = readImage("res/grandpamulti.jpg");
    int[][][] controllerArrayGrandpa = readImage("res/grandpamultiimage.jpg");
    int[][][] modelArrayCat = readImage("res/catmulti.jpg");
    int[][][] controllerArrayCat = readImage("res/catmultiimage.jpg");
    assertArrayEquals(controllerArrayGrandpa, modelArrayGrandpa);
    assertArrayEquals(controllerArrayCat, modelArrayCat);
  }

  @Test
  public void onlyFlag() throws IOException {
    ImageModel testFlag = new ImageModelImpl();
    testFlag.drawFlag("Greece", 13);
    writeImage(testFlag.getImage(), testFlag.getImageWidth(), testFlag.getImageHeight(),
            "res/greekFlagModel.jpg");
    Reader testInput = new FileReader("res/onlyFlag.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/greekFlagModel.jpg");
    int[][][] controllerArray = readImage("res/greekflag.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void flagPlus() throws IOException {
    ImageModel testFlag = new ImageModelImpl();
    testFlag.drawFlag("France", 60);
    testFlag.toGreyscale();
    testFlag.toSepia();
    testFlag.dither();
    writeImage(testFlag.getImage(), testFlag.getImageWidth(), testFlag.getImageHeight(),
            "res/frenchFlagModel.jpg");
    Reader testInput = new FileReader("res/flagPlus.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/frenchFlagModel.jpg");
    int[][][] controllerArray = readImage("res/frenchFlag.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void onlyRainbowVertical() throws IOException {
    ImageModel testRainbow = new ImageModelImpl();
    testRainbow.drawRainbowWithVerticalStripes(50, 75);
    writeImage(testRainbow.getImage(), testRainbow.getImageWidth(),
            testRainbow.getImageHeight(), "res/verticalRainbowModel.jpg");
    Reader testInput = new FileReader("res/onlyRainbow.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/verticalRainbowModel.jpg");
    int[][][] controllerArray = readImage("res/verticalRainbow.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void onlyRainbowHorizontal() throws IOException {
    ImageModel testRainbow = new ImageModelImpl();
    testRainbow.drawRainbowWithHorizontalStripes(75, 50);
    writeImage(testRainbow.getImage(), testRainbow.getImageWidth(),
            testRainbow.getImageHeight(), "res/horizontalRainbowModel.jpg");
    Reader testInput = new FileReader("res/onlyRainbowH.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/horizontalRainbowModel.jpg");
    int[][][] controllerArray = readImage("res/horizontalRainbowTest.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void rainbowHorizontalPlus() throws IOException {
    ImageModel testRainbow = new ImageModelImpl();
    testRainbow.drawRainbowWithHorizontalStripes(75, 50);
    testRainbow.sharpen();
    testRainbow.toGreyscale();
    writeImage(testRainbow.getImage(), testRainbow.getImageWidth(), testRainbow.getImageHeight(),
            "res/horizontalModel.jpg");
    Reader testInput = new FileReader("res/rainbowHorizontalPlus.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/horizontalModel.jpg");
    int[][][] controllerArray = readImage("res/horizontalEdits.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void rainbowVerticalPlus() throws IOException {
    ImageModel testRainbow = new ImageModelImpl();
    testRainbow.drawRainbowWithVerticalStripes(750, 500);
    testRainbow.sharpen();
    testRainbow.toGreyscale();
    writeImage(testRainbow.getImage(), testRainbow.getImageWidth(), testRainbow.getImageHeight(),
            "res/verticalModel.jpg");
    Reader testInput = new FileReader("res/rainbowVerticalPlus.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/verticalModel.jpg");
    int[][][] controllerArray = readImage("res/verticalEdits.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

  @Test
  public void checkerboardTest() throws IOException {
    ImageModel testCheck = new ImageModelImpl();
    testCheck.drawCheckerboard(30);
    writeImage(testCheck.getImage(), testCheck.getImageWidth(),
            testCheck.getImageHeight(),
            "res/checkerboardControllerModel.jpg");
    Reader testInput = new FileReader("res/checkerboard.txt");
    ImageController test = new ImageControllerImpl(testInput, testModel);
    try {
      test.begin();
    } catch (IOException e) {
      throw new IOException("Error: could not complete given operation", e);
    }
    int[][][] modelArray = readImage("res/checkerboardControllerModel.jpg");
    int[][][] controllerArray = readImage("res/checkerboardEdits.jpg");
    assertArrayEquals(controllerArray, modelArray);
  }

}