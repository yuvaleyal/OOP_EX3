package ascii_art;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import ascii_art.KeyboardInput;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;
import image.ImageSetter;

/**
 * 1 exit - יציאה מהתוכנה.
 * .2 chars -צפייה במאגר התווים הנוכחי.
 * .3 add - הוספה תווים לסט התווים הנוכחי.
 * .4 remove - הסרה של תווים מסט התווים הנוכחי.
 * .5 res - שליטה ברזולוציה של התמונה שלנו – הגדלה והקטנה של הרזולוציה.
 * .6 image - בחירת קובץ תמונה.
 * .7 output - בחירת ה-Output – אם התוצר יודפס ל-console או לחילופין יוחזר כקובץ
 * html
 * .8 asciiArt - הרצת האלגוריתם על הפרמטרים הנוכחיים.
 */

public class Shell {
    // make it an array / arraylist or any other java collection
    private SubImgCharMatcher workingChars = new SubImgCharMatcher(
            new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
    private AsciiArtAlgorithm asciiArtAlgorithm;
    private boolean validCommandFlag = false;
    private ImageSetter currentImage;
    private final String OUTPUT_CONSOLE_STRING = "console";
    private final String OUTPUT_HTML_STRING = "html";
    private String outputString = OUTPUT_CONSOLE_STRING;
    private final int START_VALUE = 32;
    private final int END_VALUE = 126;
    private final char START_CHAR = (char) START_VALUE;
    private final char END_CHAR = (char) END_VALUE;
    private final String DEFAULT_IMAGE = "cat.jpeg";
    private final String SPACE_STRING = "space";
    private final String ALL_STRING = "all";
    private final String PROMPT_STRING = ">>> ";
    private final String EXIT_STRING = "exit";
    private final String CHARS_STRING = "chars";
    private final String ADD_STRING_PREFIX = "add";
    private final String REMOVE_STRING_PREFIX = "remove";
    private final String RES_STRING_PREFIX = "res";
    private final String RES_DOWN = "down";
    private final String RES_UP = "up";
    private final String IMAGE_STRING = "image";
    private final String OUTPUT_STRING_PREFIX = "output";
    private final String START_STRING = "asciiArt";
    private final String CMD_ERROR = "Did not execute due to incorrect command.";
    private final String ADD_ERROR = "Did not add due to incorrect format.";
    private final String REMOVE_ERROR = "Did not remove due to incorrect format.";
    private final String RES_BOUNDARIES_ERROR = "Did not change resolution due to exceeding boundaries.";
    private final String RES_FORMAT_ERROR = "Did not change resolution due to incorrect format.";
    private final String SET_RES_MESSEGE = "Resolution set to ";
    private final String IMAGE_CMD_ERROR = "Did not execute due to problem with image file.";
    private final String OUTPUT_ERROR = "Did not change output method due to incorrect format.";
    private final String EMPTY_CHARSET_ERROR = "Did not execute. Charset is empty.";
    private final String DEFAULTFONT = "Courier New";
    private final String DEFAULTHTMLFILENAME = "out.html";
    private static final int DEFAULT_RESOLUTION = 128;
    private Image image;

    private enum ERROR_CODES {
        TOO_MANY_ARGS, INVALID_ARGS,
    }

    /**
     * The main method to execute the program.
     *
     * @param args
     *            Command-line arguments passed to the program.
     * @throws Exception
     *             If an error occurs during program execution.
     */
    public static void main(String[] args) throws Exception {
        Shell s = new Shell();
        s.run();
    }

    /**
     * Constructs a new Shell object with a default image.
     * 
     * @throws IOException
     *             If an I/O error occurs while reading the default image.
     */
    public Shell() throws IOException {
        this.currentImage = new ImageSetter(new Image(DEFAULT_IMAGE));
        this.currentImage.updateResulotion(DEFAULT_RESOLUTION);
        this.image = new Image(DEFAULT_IMAGE);
    }

    /**
     * Executes the main loop of the program, waiting for user input and processing
     * commands.
     * 
     * @throws Exception
     *             If an error occurs during program execution.
     */
    public void run() throws Exception {
        System.out.print(PROMPT_STRING);
        String inputString = KeyboardInput.readLine();
        while (!(inputString.equals(EXIT_STRING))) {
            try {
                // chars command
                if (inputString.startsWith(CHARS_STRING)) {
                    validCommandFlag = true;
                    chars_cmd(workingChars.getAllChars());
                }
                // add command
                if (inputString.startsWith(ADD_STRING_PREFIX)) {
                    validCommandFlag = true;
                    int flag = 0;
                    flag = add_cmd(inputString);
                    if (flag == 1) {
                        continue;
                    }
                }
                // remove command
                if (inputString.startsWith(REMOVE_STRING_PREFIX)) {
                    validCommandFlag = true;
                    int flag = remove_cmd(inputString);
                    if (flag == 1) {
                        continue;
                    }
                }
                // res command
                if (inputString.startsWith(RES_STRING_PREFIX)) {
                    validCommandFlag = true;
                    res_cmd(inputString);
                }
                // image command
                if (inputString.startsWith(IMAGE_STRING)) {
                    validCommandFlag = true;
                    image_cmd(inputString);
                }
                // change output command
                if (inputString.startsWith(OUTPUT_STRING_PREFIX)) {
                    validCommandFlag = true;
                    output_cmd(inputString);
                }
                // starting command
                if (inputString.equals(START_STRING)) {
                    validCommandFlag = true;
                    if (start_cmd() == 0) {
                        continue;
                    }

                }

                if (!validCommandFlag) {
                    System.out.println(CMD_ERROR);
                }
            } catch (Exception e) {
                System.out.println(CMD_ERROR);
            }
            System.out.print(PROMPT_STRING);
            inputString = KeyboardInput.readLine();
            validCommandFlag = false;
        }
    }

    private void chars_cmd(TreeSet<Character> treeSet) {
        for (Character ch : treeSet) {
            System.out.print(ch);
            System.out.print(" ");
        }
        System.out.println();
    }

    private int add_cmd(String inputString) throws Exception {
        String[] args = inputString.split("\\s+");
        if (args.length > 2)
            throw new Exception(ERROR_CODES.TOO_MANY_ARGS + "");
        String mainArg = args[1];
        if (mainArg.length() > 1 && mainArg.charAt(1) == '-') {
            char first = mainArg.charAt(0);
            char last = mainArg.charAt(2);
            if (first == last) {
                System.out.println(ADD_ERROR);
                return 1;
            }
            else {
                if (first < last) {
                    for (char letter = first; letter <= last; letter++) {
                        workingChars.addChar(letter);
                    }
                }
                else {
                    for (char letter = first; letter >= last; letter--) {
                        workingChars.addChar(letter);
                    }
                }
            }
        }
        else if (mainArg.equals(ALL_STRING)) {
            // 32-126
            for (char letter = START_CHAR; letter <= END_CHAR; letter++) {
                workingChars.addChar(letter);
            }
        }
        else if (mainArg.equals(SPACE_STRING)) {
            workingChars.addChar(' ');
        }
        else {
            if (mainArg.length() != 1 || !(mainArg.charAt(0) <= END_CHAR)
                    || !(mainArg.charAt(0) >= START_CHAR)) {
                System.out.println(ADD_ERROR);
            }
            else {
                char letter = mainArg.charAt(0);
                workingChars.addChar(letter);
            }

        }
        return 0;
    }

    private int remove_cmd(String inputString) throws Exception {
        String[] args = inputString.split(" ");
        if (args.length > 2)
            throw new Exception(ERROR_CODES.TOO_MANY_ARGS + "");
        String mainArg = args[1];
        if (mainArg.length() > 1 && mainArg.charAt(1) == '-') {
            for (int i = 0; i < args.length; i++) {
                char first = mainArg.charAt(0);
                char last = mainArg.charAt(2);
                if (first == last) {
                    System.out.println(REMOVE_ERROR);
                    return 1;
                }
                else {
                    if (first < last) {
                        for (char letter = first; letter <= last; letter++) {
                            workingChars.removeChar(letter);
                        }
                    }
                    else {
                        for (char letter = first; letter >= last; letter--) {
                            workingChars.removeChar(letter);
                        }
                    }
                }
            }
        }
        else if (mainArg.equals(ALL_STRING)) {
            // 32-124
            workingChars.removeAllChars();
        }
        else if (mainArg.equals(SPACE_STRING)) {
            workingChars.removeChar(' ');
        }
        else {
            if (mainArg.length() != 1 && !(mainArg.charAt(0) <= END_CHAR)
                    && !(mainArg.charAt(0) >= START_CHAR)) {
                System.out.println(REMOVE_ERROR);
            }
            else {
                char letter = mainArg.charAt(0);
                workingChars.removeChar(letter);
            }
        }
        return 0;
    }

    private void res_cmd(String inputString) throws Exception {
        String[] args = inputString.split(" ");
        if (args.length > 2)
            throw new Exception(ERROR_CODES.TOO_MANY_ARGS + "");
        String mainArg = args[1];
        int curResolution = currentImage.getResolution();
        // Resolution set to
        if (mainArg.equals(RES_DOWN)) {
            if (curResolution / 2 <= getMinWidthVal()) {
                curResolution /= 2;
                currentImage.updateResulotion(curResolution);
                System.out.println(SET_RES_MESSEGE + curResolution);
            }
            else {
                System.out.println(RES_BOUNDARIES_ERROR);
            }
        }
        else if (mainArg.equals(RES_UP)) {
            if (curResolution * 2 <= this.currentImage.getWidth()) {
                curResolution *= 2;
                currentImage.updateResulotion(curResolution);
                System.out.println(SET_RES_MESSEGE + curResolution);
            }
            else {
                System.out.println(RES_BOUNDARIES_ERROR);
            }
        }
        else {
            System.out.println(RES_FORMAT_ERROR);
        }
    }

    private void image_cmd(String inputString) throws Exception {
        String[] args = inputString.split(" ");
        if (args.length > 2)
            throw new Exception(ERROR_CODES.TOO_MANY_ARGS + "");
        String mainArg = args[1];
        try {
            Image test = new Image(mainArg);
            this.image = test;
            this.currentImage = new ImageSetter(test);
            this.currentImage.updateResulotion(DEFAULT_RESOLUTION);
        } catch (Exception e) {
            System.out.println(IMAGE_CMD_ERROR);
        }
    }

    private void output_cmd(String inputString) throws Exception {
        String[] args = inputString.split(" ");
        if (args.length > 2)
            throw new Exception(ERROR_CODES.TOO_MANY_ARGS + "");
        String mainArg = args[1];
        if (mainArg.equals(OUTPUT_CONSOLE_STRING)) {
            outputString = OUTPUT_CONSOLE_STRING;
        }
        else if (mainArg.equals(OUTPUT_HTML_STRING)) {
            outputString = OUTPUT_HTML_STRING;
        }
        else {
            System.out.println(OUTPUT_ERROR);
        }
    }

    private int start_cmd() throws IOException {
        if (workingChars.getSize() == 0) {
            System.out.println(EMPTY_CHARSET_ERROR);
            return 0;
        }
        this.asciiArtAlgorithm = new AsciiArtAlgorithm(this.image,
                currentImage.getResolution(),
                workingChars);
        char[][] result = this.asciiArtAlgorithm.run();
        AsciiOutput output;
        if (outputString == OUTPUT_CONSOLE_STRING) {
            output = new ConsoleAsciiOutput();
        }
        else {
            output = new HtmlAsciiOutput(DEFAULTHTMLFILENAME, DEFAULTFONT);
        }
        output.out(result);
        return 1;
    }

    private int getMinWidthVal() {
        return Math.min(this.currentImage.getWidth(), this.currentImage.getHeight());
    }
}
