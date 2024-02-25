package ascii_art;
import ascii_art.KeyboardInput;

/**
1 exit - יציאה מהתוכנה.
.2 chars -צפייה במאגר התווים הנוכחי.
.3 add - הוספה תווים לסט התווים הנוכחי.
.4 remove - הסרה של תווים מסט התווים הנוכחי.
.5 res - שליטה ברזולוציה של התמונה שלנו – הגדלה והקטנה של הרזולוציה.
.6 image - בחירת קובץ תמונה.
.7 output - בחירת ה-Output – אם התוצר יודפס ל-console או לחילופין יוחזר כקובץ html
.8 asciiArt - הרצת האלגוריתם על הפרמטרים הנוכחיים.
 */


public class Shell {
    // make it an array / arraylist or any other java collection
    private String[] workingChars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final String PROMPT_STRING =          ">>> ";
    private final String EXIT_STRING =          "exit";
    private final String CHARS_STRING =         "chars";
    private final String ADD_STRING_PREFIX =    "add";
    private final String REMOVE_STRING_PREFIX = "remove";
    private final String RES_STRING_PREFIX =    "res";
    private final String IMAGE_STRING =         "image";
    private final String OUTPUT_STRING_PREFIX = "output";
    private final String START_STRING =         "asciiArt";
    private enum ERROR_CODES {
        TOO_MANY_ARGS,
        INVALID_ARGS,
    }

    public Shell() {

    }
    public void run() {
        System.out.print(PROMPT_STRING);
        String inputString = KeyboardInput.readLine();
        while (!(inputString.equals(EXIT_STRING))) {
            try {
                System.out.println();
                if (inputString.startsWith(CHARS_STRING)) {
                    for(int i = 0; i < workingChars.length; i++) {
                        System.out.print(workingChars[i]);
                        if (i + 1 < workingChars.length) System.out.print(" ");
                    }
                    System.out.println();                    
                }
                
                if (inputString.startsWith(ADD_STRING_PREFIX)) {
                    String[] args = inputString.split(" ");
                    if (args.length > 2) throw new Exception(ERROR_CODES.TOO_MANY_ARGS + "");
                    String mainArg = args[1];
                    if (mainArg.charAt(1) == '-') {
                        for (int i = 0; i < args.length; i++) {
                            workingChars.
                        }
                    }
                } else {
                    
                }                
            } catch (Exception e) {
                System.out.println("Error accoured");

            }
            System.out.print(PROMPT_STRING);
        }
    }
}
