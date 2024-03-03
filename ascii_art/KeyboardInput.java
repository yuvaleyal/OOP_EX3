package ascii_art;

import java.util.Scanner;

class KeyboardInput {
    private static KeyboardInput keyboardInputObject = null;
    private Scanner scanner;

    private KeyboardInput() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Retrieves the singleton instance of the KeyboardInput class.
     *
     * @return The singleton instance of KeyboardInput.
     */
    public static KeyboardInput getObject() {
        if (KeyboardInput.keyboardInputObject == null) {
            KeyboardInput.keyboardInputObject = new KeyboardInput();
        }
        return KeyboardInput.keyboardInputObject;
    }

    /**
     * Reads a line of text input from the console.
     *
     * @return The line of text input.
     */

    public static String readLine() {
        return KeyboardInput.getObject().scanner.nextLine().trim();
    }
}