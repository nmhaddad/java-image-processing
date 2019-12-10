# Imager

## README

This README is for the Imager program created for Northeastern University’s CS-5004 Object Oriented Design course, a part of the Align MSCS program. Imager is a program that takes an image or creates an image and performs an operation on the image’s pixels to produce a desired outcome. This README is specifically written for part two. 

This is the third release of the Imager program. The Imager program now includes a model, controller and view (new with this release). As with the previous release, the user can use the command line interface to start the program that allows the user to send a file directly to the controller. In keeping up with solid design principles, we kept everything (mostly) the same. You can read more about what changes me made and did not make in the changelog below. 

To start this program using the GUI, run the JAR file included in the res/ folder of this submission with -interactive (See last sentence of this paragraph). After a moment, the GUI will start right up. The interactive controller is called with the following script: java -jar Program.jar -interactive.

If you would like to use the previous release of this program (the one without the GUI), you will need to enter -script in the command line (see the last sentence of this paragraph. What happens then is this: For this, we decided to use the Reader interface. We decided it was best to use a FileReader implementation of this interface. The file is then read to a Reader. This is passed to the controller along with a model that will be used throughout the entire duration of the program. The program then parses the Reader input for commands. The user must enter valid commands in order for this program to work. The commands are as follows: `load [filename]`, `save [filename]`, `blur`, `sharpen`, `greyscale`, `sepia`, `draw [vertical/horizontal/flag] [int/string country] [int]`, `checkerboard`, `dither`, and `mosaic`. The user must enter the commands as written above with the substituted items in brackets. Otherwise, the program will generate an illegal argument exception.  The script controller is called with the following script: `java -jar Program.jar -script path-of-script-file`.


## Authors

- Cassidy Bayen, ALIGN MSCS, Northeastern University
- Nathaniel Haddad, ALIGN MSCS, Northeastern University

## Acknowledgements

This program could not have been written without the help of the CS5004 professors and Teaching Assistants, whose guidance and wisdom helped us produce what we believe to be a pretty well organized program. 

## Changelog

This is the changelog for the third release of the Imager program. It includes two controllers, a model, a GUI view. The following changelog will provide detailed updates about changes made to existing code and new additions to the Imager program.

The biggest change in this installment of Imager is the inclusion of a graphical user interface, used as the view of the program. The GUI contains a menu and submenus for  "File", "Filter", "Transform", and "Generate". Every action is accessible through these submenus. We designed it this way not only because Assignment 10 required it, but because we thought it would be good for user experience to have everything in one place. We did not want people searching all over for stuff, and other programs (like IntelliJ IDEA) have all of their actions accessible through the the menu bar and its submenus with shortcuts to commonly used features available as buttons and other features throughout the GUI.

That being said, we did create a couple shortcuts. We felt that the user will commonly want to "Run", "Load Batch", "Clear", "Undo", "Redo", "Quit", so we created buttons for each of these. There is also a scrollable `JTextArea` for the user to manually write commands. This is where the "Run" and "Clear" buttons come into play. First, the user enters text into the `JTextArea` in the format from the previous assignment (they can also enter undo and redo now too!). Then, they can click run, and this sends a signal to the controller to run the typed batch script from the input variable inside of the view. They can use clear afterwards to clear the `JTextArea` of any text. This just simplifies things, and makes it easier for the user to do multiple batches in one session. The "Load Batch" button allows the user to open a `JFileChooser` menu and select a .txt file to load a batch from. Just like in the previous assignment, the batch is sent over to the controller and processed by the scanner, where methods are then executed by the model. These are all included in a `JPanel` called `featurePanel`.

The Imager GUI also includes a scrollable image. This was required in Assignment 10. We also updated the image each time changes were made to it. This required a few changes. In the controller, we needed to update the view after changes were made to the image in the model. We created a `setImage` method that refreshes the image with each action to its current status so the user can see exactly what actions they have taken so far. We also needed to make changes to the ImageUtil class that was provided with this assignment. Understanding the methods in the `ImageUtil` class was critical to getting this feature to work. We added a new method to this class called `getBufferedImage` that takes in a `int[][][]` and returns a `BufferedImage` that can be passed to a Java Swing component.

This release of Imager includes two controllers: an interactive controller and a script controller. The interactive controller is called with the following script: `java -jar Program.jar -interactive`. The script controller is called with the following script: `java -jar Program.jar -script path-of-script-file`. The interactive controller that includes the GUI, and the controller from the previous release that allows the user to use scripts. Basically, the interactive controller is an improved version of the previous release's controller that takes in a view instead of a Reader object. By giving the view responsibilities for getting input from the user and giving it to the controller, it more accurately follows the MVC design pattern. It has a new method to help out with this: `accept`. `accept` has been added to the `ImageController` interface and its implementations. It allows the controller to get commands from the view in the for of a string, which can be processed by the parser implemented in the previous assignment. It does this through modifications that we made to the `begin` method. `accept` takes in a string from the view, sends it over to a new method called `processCommands`, that is basically a helper method that parses the string that it is passed. `accept` also displays error messages to the view, refreshes, and repaints the view with each calling.

For extra credit, Assignment 10 asked us to create undo and redo methods that allow the user to undo and redo actions taken by the image processor (the model). To implement these, we first added an undo and redo method to the `ImageModel` interface, and then added their implementations to the `ImageModelImpl` implementation of the `ImageModel` interface. We decided to include undo and redo in the model since they are technically actions called on the image. Our understanding of this is that the user is calling undo `blur` on the image, so it is similar to applying a filter that does the opposite of `blur`. We also needed to make changes to each body of each method by adding an `undoHelper` method that adds the current image to a stack of previous images. We do this so we can undo operations. When the user wants to undo, the image at the top of the stack becomes the current image, while the current image is pushed into a redo stack. We also allow the user to undo loading a picture. We decided to do this since many other applications allow the user to undo pasting images into an image editor. While loading an image is not exactly the same, the way our image window is set up makes it appear as an editing window, an image processor (that's how we designed our model!). Since our model is not an image but an image processor, we decided to allow the user to revert to the first stage of the `ImageModelImpl`...a blank canvas.

In this release, we modified our main so a user could access the program from the command line. We have two modes, interactive and script. These modes run the respective controllers for the interactive GUI (`ImageCntrollerImplInteractive`)  and the batch script (`ImageControllerImpl`).  To know which version to run main checks for two flags, `-interactive` and `-script`.  If `–interactive` is found main sets up a new image model (`ImageModelImpl`), a new view (`ImageGraphics`), and a new controller (`ImageControllerImplInteractive`), passing in the model and the view. It then calls the `begin` method on the controller to start the program. This pulls up the interactive interface and the user can proceed. If, for some reason, these steps do not work, an `IOException` will be thrown. If –script is found main reads the file (`fileReader`), creates a new model (`ImageModelImpl`), and a new controller (`ImageControllerImpl`), passing in the string read from the file and the model. It then calls the `begin` on the controller to start the program.  This reads the batch script and then executes the commands in the file. If the user enters any other input besides the commands noted in the README introduction and install, an illegal argument exception will be thrown.

We needed to make a couple changes to our mosaic method. In Assignment 9, we forgot to average the color for the cluster, so we included a new method `getAverageColor` that does this for us. It is included in the Pixel interface and its implementations. We also added a new conditional that returns the current image for this method if the seeds is equal to the number of pixels in the image, since pixels can only be selected once, the returned image would be exactly the same. This just improves efficiency. 

## News

With this release, the user can now undo and redo operations. The program also uses a GUI. Just like before, the user can blur and sharpen images. The user can also transform images to greyscale and sepia styles. This release includes operations to draw vertical and horizontal rainbows, a French flag, a Swiss flag, and a Greek flag. The user can also use a dither or mosaic filter. 

## Install

After the files are downloaded, open the desired program, load the files into the program, and run them! For more information, check out Google for how to run .java files on your operating system.

## Copying / License

*The following images are owned by the author and are authorized for use:*
- Bayen, Cassidy. “cat.”
- Bayen, Cassidy. “grandpa.”
- Bayen, Cassidy. “landscape.”
- Bayen, Cassidy. “light.”
- Bayen, Cassidy. “noise.”
- Bayen, Cassidy. “prechickenSharpened.”
- Bayen, Cassidy. “preCHIECKEBLUR.”
- Bayen, Cassidy. “santa”.

*The following image was found online:*
Cadiomals. “File: LowerManhattanSept2013.Png.” Wikipedia, Wikipedia, 2 Oct. 2013, 4:17, es.wikipedia.org/wiki/Archivo:LowerManhattanSept2013.png.

## Bugs

None – but if you find one, please let us know!

## Constributions / Hacking

Feel free to let us know how we can improve this program! We are always looking for guidance because we want to be the best we can be. Thanks in advance!
