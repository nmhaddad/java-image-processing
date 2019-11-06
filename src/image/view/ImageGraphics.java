package image.view;

//AWT imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Insets;

//IO imports.
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

//Until import.
import java.util.function.Consumer;

//Image file ending import.
import javax.imageio.ImageIO;

//JSwing imports.
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

//ImageUtil import.
import image.model.ImageUtil;

/**
 * This is an implementation of the ImageView interface and shows a graphical representation of the
 * program Imager. It has a menu with four options: File, Filter, Transform, and Generate. Each of
 * these has a submenu containing relevant actions (load, save, undo, redo, blur, sharpen, sepia,
 * greyscale, mosaic, dither, vertical rainbow, horizontal rainbow, checkerboard. It also contains
 * buttons that are shortcuts to these menu items. It includes a JTextArea so that the user can type
 * batch scripts out into the view, on top of being able to load them in through the File submenu.
 */
public class ImageGraphics extends JFrame implements ImageView {

  /**
   * This variable represents a button that is used to send commands.
   */
  private JButton commandButton;

  /**
   * This variable represents a button panel that will host buttons and a text area input field.
   */
  private JPanel featurePanel;

  /**
   * This variable represents a menu that will be displayed at the top of the screen.
   */
  private JMenuBar menu = new JMenuBar();

  /**
   * This variable is used to store a JScrollPane object to make areas of the GUI scrollable.
   */
  private JScrollPane scrollPane;

  /**
   * This variable is used to store input for batch commands.
   */
  private JTextArea input;

  /**
   * This variable is used to store image labels that are used to display images in the program.
   */
  private JLabel imageLabel;

  /**
   * This variable is used to create a save display.
   */
  private JLabel fileSaveDisplay = new JLabel();

  /**
   * This variable creates a new button called "Load batch".
   */
  private JButton loadBatchButton = new JButton("Load batch");

  /**
   * This variable is used to send commands.
   */
  private Consumer<String> commandCallback;

  /**
   * This constructor creates a JFrame representation of the image processor program. It contains a
   * scrollable image, buttons for running and quitting the program, a menu with each operation that
   * can be applied to the image, a text input area for batch commands, and hidden parameter
   * variables for different methods. It is designed to stop on exit. Many of the features are built
   * in helper methods as opposed to a giant constructor.
   */
  public ImageGraphics() {
    super();
    this.setBackground(Color.GRAY);
    this.setTitle("Cassidy and Nate Present...Imager!");
    this.setSize(1200, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    buildMenuBar();
    buildImageDisplay();
    buildButtons();
    commandCallback = null;
    this.pack();
  }

  /**
   * This helper method creates the menu bar. The menu bar holds the following main menus: file,
   * filter, transform, and generate. Each has a sub menu. File contains load, save, undo, redo.
   * Filter contains blur, sharpen, mosaic, and dither. Transform contains greyscale ad sepia.
   * Generate contains vertical rainbow, horizontal rainbow, and checkerboard.
   */
  private void buildMenuBar() {
    buildFileSubmenu();
    buildFilterSubmenu();
    buildTransformSubmenu();
    buildGenerateSubmenu();
  }

  /**
   * This helper method creates the File submenu for this view. It contains options to load, save,
   * undo and redo. When called, it adds a submenu to the menu variable declared above that will be
   * displayed at the top of the program when it runs.
   */
  private void buildFileSubmenu() {
    JMenu fileSubmenu = new JMenu("File");
    JMenuItem load = new JMenuItem("Load Picture");
    final JFileChooser loader =
            new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    load.addActionListener((ActionEvent e) -> {
      FileFilter image = new FileNameExtensionFilter("Image files",
              ImageIO.getReaderFileSuffixes());
      loader.setFileFilter(image);
      int returnValue = loader.showOpenDialog(null);
      if (commandCallback != null && returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = loader.getSelectedFile();
        commandCallback.accept("load " + selectedFile.toString());
      }
    });
    fileSubmenu.add(load);
    JMenuItem loadBatch = new JMenuItem("Load Batch");
    loadBatch.addActionListener((ActionEvent e) -> readBatch(input));
    fileSubmenu.add(loadBatch);
    JMenuItem save = new JMenuItem("Save Picture");
    save.addActionListener((ActionEvent e) -> {
      final JFileChooser saver = new JFileChooser(".");
      int returnValue = saver.showSaveDialog(ImageGraphics.this);
      if (commandCallback != null && returnValue == JFileChooser.APPROVE_OPTION) {
        File imageToSave = saver.getSelectedFile();
        commandCallback.accept("save " + imageToSave.toString());
        fileSaveDisplay.setText(imageToSave.getAbsolutePath());
      }
    });
    fileSubmenu.add(save);

    JMenuItem undo = new JMenuItem("Undo");
    undo.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("undo");
      }
    });
    fileSubmenu.add(undo);
    JMenuItem redo = new JMenuItem("Redo");
    redo.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("redo");
      }
    });
    fileSubmenu.add(redo);
    menu.add(fileSubmenu);
  }

  /**
   * This helper method creates the Filter submenu for this view. It contains options to blur,
   * sharpen, dither, and mosaic an image. When called, it adds a submenu to the menu variable
   * declared above that will be displayed at the top of the program when it runs.
   */
  private void buildFilterSubmenu() {
    JMenu filterSubmenu = new JMenu("Filter");
    JMenuItem blur = new JMenuItem("Blur");
    blur.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("blur");
      }
    });
    filterSubmenu.add(blur);
    JMenuItem sharpen = new JMenuItem("Sharpen");
    sharpen.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("sharpen");
      }
    });
    filterSubmenu.add(sharpen);
    JMenuItem dither = new JMenuItem("Dither");
    dither.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("dither");
      }
    });
    filterSubmenu.add(dither);
    JMenuItem mosaic = new JMenuItem("Mosaic");
    mosaic.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        String s = (String) JOptionPane.showInputDialog(this, "Number of seeds: ",
                "Mosaic", JOptionPane.PLAIN_MESSAGE, null, null,
                "1");
        if ((s != null) && (s.length() > 0)) {
          commandCallback.accept("mosaic " + s);
        }
        commandCallback.accept(this.input.getText());
      }
    });
    filterSubmenu.add(mosaic);
    menu.add(filterSubmenu);
  }

  /**
   * This helper method creates the Transform submenu for this view. It contains options to make an
   * image greyscale and sepia. When called, it adds a submenu to the menu variable declared above
   * that will be displayed at the top of the program when it runs.
   */
  private void buildTransformSubmenu() {
    JMenu transformSubmenu = new JMenu("Transform");
    JMenuItem greyscale = new JMenuItem("Greyscale");
    greyscale.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("greyscale");
      }
    });
    transformSubmenu.add(greyscale);
    JMenuItem sepia = new JMenuItem("Sepia");
    sepia.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("sepia");
      }
    });
    transformSubmenu.add(sepia);
    menu.add(transformSubmenu);
  }

  /**
   * This helper method creates the Generate submenu for this view. It contains options to generate
   * a vertical rainbow, a horizontal rainbow, and a checkerboard pattern. When called, it adds a
   * submenu to the menu variable declared above that will be displayed at the top of the program
   * when it runs.
   */
  private void buildGenerateSubmenu() {
    JMenu drawSubmenu = new JMenu("Generate");
    JMenuItem vrainbow = new JMenuItem("Vertical Rainbow");
    vrainbow.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        String w = (String) JOptionPane.showInputDialog(this, "Width: ",
                "Draw Vertical Rainbow", JOptionPane.PLAIN_MESSAGE, null, null,
                "1");
        String h = (String) JOptionPane.showInputDialog(this, "Height: ",
                "Draw Vertical Rainbow", JOptionPane.PLAIN_MESSAGE, null, null,
                "1");
        if ((w != null) && (w.length() > 0) && (h != null) && (h.length() > 0)) {
          commandCallback.accept("draw vertical rainbow " + w + " " + h);
        }
        commandCallback.accept(this.input.getText());
      }
    });
    drawSubmenu.add(vrainbow);

    JMenuItem hrainbow = new JMenuItem("Horizontal Rainbow");
    hrainbow.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        String w = (String) JOptionPane.showInputDialog(this, "Width: ",
                "Draw Horizontal Rainbow", JOptionPane.PLAIN_MESSAGE, null, null,
                "1");
        String h = (String) JOptionPane.showInputDialog(this, "Height: ",
                "Draw Horizontal Rainbow", JOptionPane.PLAIN_MESSAGE, null, null,
                "1");
        if (((w != null) && (w.length() > 0)) && ((h != null) && (h.length() > 0))) {
          commandCallback.accept("draw horizontal rainbow " + w + " " + h);
        }
        commandCallback.accept(this.input.getText());
      }
    });
    drawSubmenu.add(hrainbow);
    JMenuItem checkerboard = new JMenuItem("Checkerboard");
    checkerboard.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        String s = (String) JOptionPane.showInputDialog(this, "Size: ",
                "Draw Checkerboard", JOptionPane.PLAIN_MESSAGE, null, null,
                "1");
        if ((s != null) && (s.length() > 0)) {
          commandCallback.accept("checkerboard " + s);
        }
        commandCallback.accept(this.input.getText());
      }
    });
    drawSubmenu.add(checkerboard);
    menu.add(drawSubmenu);
    this.setJMenuBar(menu);
  }

  /**
   * This helper method builds the image display. The image display is set to the dimensions of 600
   * by 600, and includes a scroll pane (this allows for images that are larger than the 600 by 600
   * frame to be scrollable).
   */
  private void buildImageDisplay() {
    imageLabel = new JLabel();
    imageLabel.setMinimumSize(new Dimension(10, 10));
    scrollPane = new JScrollPane(imageLabel);
    scrollPane.setPreferredSize(new Dimension(600, 600));
    this.add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * This method builds the button panel and it's contents. It contains a title to help the user
   * understand what to do, a text area for the user to enter input, and five buttons: run, save
   * batch, undo, redo, quit.
   */
  private void buildButtons() {
    buildButtonPanel();
    buildTextArea();
    buildBatchButtons();
    buildClearButton();
    buildUndoButton();
    buildRedoButton();
    buildQuitButton();
  }

  /**
   * This helper method creates the initial button panel to which buttons for this program will be
   * added. It uses a simple box layout, with a set border on the left side of the screen.
   */
  private void buildButtonPanel() {
    featurePanel = new JPanel();
    BoxLayout boxlayout = new BoxLayout(featurePanel, BoxLayout.Y_AXIS);
    featurePanel.setLayout(boxlayout);
    featurePanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    this.add(featurePanel, BorderLayout.WEST);
  }

  /**
   * This helper method creates a text entry area for entering batch commands line by line in the
   * view. It uses a JTextArea object to create the entry area, which is added to a scrollable panel
   * so that the user can enter as much text as they like. The text area is added to the button
   * panel. It then creates a run button, that sends input to the controller so that it can process
   * the text into commands to be applied to the model.
   */
  private void buildTextArea() {
    JLabel instructions = new JLabel("");
    instructions.setText("Type commands here or choose from menu above");
    featurePanel.add(instructions);
    input = new JTextArea();
    scrollPane = new JScrollPane(input);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(20, 10));
    featurePanel.add(scrollPane);
    commandButton = new JButton("Run");
    commandButton.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept(input.getText());
      }
    });
    featurePanel.add(commandButton);
  }

  /**
   * This helper method allows the user to load and save batch commands using txt files. Both load
   * and save call helper methods for opening and saving txt files to the view.
   */
  private void buildBatchButtons() {
    loadBatchButton.addActionListener((ActionEvent e) -> readBatch(input));
    featurePanel.add(loadBatchButton);
  }

  /**
   * This helper method is used to clear the text input area of all text. It creates a button that
   * when called replaces all text in the input to an empty string.
   */
  private void buildClearButton() {
    commandButton = new JButton("Clear");
    commandButton.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        input.setText("");
      }
    });
    featurePanel.add(commandButton);
  }

  /**
   * This helper method creates a undo button that allows the user to undo any and all actions taken
   * since the program has begun.
   */
  private void buildUndoButton() {
    commandButton = new JButton("Undo");
    commandButton.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("undo");
      }
    });
    featurePanel.add(commandButton);
  }

  /**
   * This helper method creates a redo button that allows the user to redo actions that they have
   * undone. This is a linear process, and if the user does something new to the program, the redo
   * stack is emptied because there is nothing more to redo.
   */
  private void buildRedoButton() {
    commandButton = new JButton("Redo");
    commandButton.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("redo");
      }
    });
    featurePanel.add(commandButton);
  }

  /**
   * This helper method creates a quit button. Just like the exit button on the top of the menu,
   * this button allows the user to quit the program by simply pushing a button. It uses system exit
   * with a status of 0 since no error is reported.
   */
  private void buildQuitButton() {
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> System.exit(0));
    featurePanel.add(quitButton);
  }

  /**
   * This method reads from a .txt file into a JTextArea. Inside this .txt should be a pre written
   * batch script. This allows the user to run a pre-written script from the view.
   *
   * @param area a JTextArea that the file will be read into
   */
  private void readBatch(JTextArea area) {
    JFileChooser choice = new JFileChooser();
    FileFilter text = new FileNameExtensionFilter(".txt files", "txt");
    choice.setFileFilter(text);
    int dialogBox = choice.showOpenDialog(loadBatchButton);
    if (dialogBox == JFileChooser.APPROVE_OPTION) {
      File file = choice.getSelectedFile();
      if (file == null) {
        return;
      }
      try {
        Reader output = new FileReader(file);
        area.read(output, area);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * This method makes objects visible in the view. It uses a built in JFrame method called
   * setVisible to make things like panels visible on screen.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * This method provides the view with a callback option to process a command.
   *
   * @param callback object a consumer object of type string that represents a callback.
   */
  @Override
  public void setCommandCallback(Consumer<String> callback) {
    commandCallback = callback;
  }

  /**
   * This method gets the command from the input variable for this JFrame.
   *
   * @return the string command to be executed.
   */
  @Override
  public String getCommand() {
    String command = this.input.getText();
    this.input.setText("");
    return command;
  }

  /**
   * This method is used by the controller to set the image displayed in the view to the current
   * image that is stored within the controllers model. It uses a method within ImageUtil called
   * getBufferedImage() that passes this view an image that can be displayed.
   *
   * @param image an integer array of pixels.
   */
  @Override
  public void setImage(int[][][] image) {
    try {
      BufferedImage bufferedImage = ImageUtil.getBufferedImage(image);
      imageLabel.setIcon(new ImageIcon(bufferedImage));
    } catch (IOException e) {
      this.showErrorMessage(e.toString());
    }
  }

  /**
   * This method is used to refresh the view, so that images and other objects can be reset and
   * updated without having to save and load images multiple times.
   */
  @Override
  public void refresh() {
    this.input.requestFocus();
    this.repaint();
  }

  /**
   * This method creates a pop-out error message for handling exceptions thrown through the view.
   * Each message is pulled from the controller/model.
   *
   * @param error the error that is thrown.
   */
  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

}
