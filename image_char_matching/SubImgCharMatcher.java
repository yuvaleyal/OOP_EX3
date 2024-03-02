package image_char_matching;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SubImgCharMatcher {
    private TreeMap<Character, Double> charBrightnessMap;
    private TreeMap<Character, Double> defaultBrightness;
    private double maxBrightness;
    private double minBrightness;

    /**
     * Constructs a new SubImgCharMatcher instance with the specified character set.
     *
     * @param charset
     *            The character set used for mapping pixel intensity to characters.
     */
    public SubImgCharMatcher(char[] charset) {
        charBrightnessMap = new TreeMap<Character, Double>();
        defaultBrightness = new TreeMap<Character, Double>();
        for (char c : charset) {
            insertChar(c);
        }
        minBrightness = 1;
        updateMinBrightness();
        maxBrightness = 0;
        updateMaxBrightness();
        normalValues();
    }

    /**
     * Retrieves the character corresponding to the provided image brightness.
     *
     * @param brightness
     *            The brightness value of the image.
     * @return The character corresponding to the provided brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        TreeSet<Double> brightnesSet = new TreeSet<Double>(charBrightnessMap.values());
        double closestBrightnessFloor = brightnesSet.floor(brightness);
        double closestBrightnessCeiling = brightnesSet.ceiling(brightness);
        double newBrightness = getClosestBrightness(brightness, closestBrightnessFloor,
                closestBrightnessCeiling);
        Set<Map.Entry<Character, Double>> charAndBrightnessSet = charBrightnessMap
                .entrySet();
        for (Map.Entry<Character, Double> entry : charAndBrightnessSet) {
            if (entry.getValue() == newBrightness) {
                return entry.getKey();
            }
        }
        return ' ';
    }

    /**
     * Adds a character to the character set and updates brightness values if
     * necessary.
     *
     * @param c
     *            The character to be added to the character set.
     */
    public void addChar(char c) {
        insertChar(c);
        double cBrightness = charBrightnessMap.get(c);
        if (cBrightness > maxBrightness) {
            maxBrightness = cBrightness;
            normalValues();
        }
        if (cBrightness < minBrightness) {
            minBrightness = cBrightness;
            normalValues();
        } else {
            charBrightnessMap.replace(c, normalBrightness(cBrightness));
        }
    }

    /**
     * Removes a character from the character set and updates brightness values if
     * necessary.
     *
     * @param c
     *            The character to be removed from the character set.
     */
    public void removeChar(char c) {
        double cBrightness = charBrightnessMap.get(c);
        charBrightnessMap.remove(c);
        if (cBrightness == maxBrightness) {
            updateMaxBrightness();
            normalValues();
        }
        if (cBrightness == minBrightness) {
            updateMinBrightness();
            normalValues();
        }
    }

    /**
     * Retrieves all characters in the character set.
     *
     * @return A TreeSet containing all characters in the character set.
     */
    public TreeSet<Character> getAllChars() {
        return new TreeSet<Character>(charBrightnessMap.keySet());
    }

    /**
     * Returns the number of characters stored in the charBrightnessMap.
     *
     * @return The number of characters stored in the charBrightnessMap.
     */
    public int getSize(){
        return charBrightnessMap.size();
    }

    /**
     * Removes all characters from the charBrightnessMap and resets other brightness-related attributes.
     */
    public void removeAllChars(){
        charBrightnessMap = new TreeMap<Character, Double>();
        defaultBrightness = new TreeMap<Character, Double>();
        minBrightness = 1;
        maxBrightness = 0;
    }

    private double getCharBrightness(char c) {
        boolean[][] cImg = CharConverter.convertToBoolArray(c);
        int trueCounter = 0;
        for (boolean[] row : cImg) {
            for (boolean pixel : row) {
                if (pixel) {
                    trueCounter++;
                }
            }
        }
        return (double) trueCounter / CharConverter.DEFAULT_PIXEL_RESOLUTION;
    }

    private void insertChar(char c) {
        if (!defaultBrightness.containsKey(c)) {
            defaultBrightness.put(c, getCharBrightness(c));
        }
        charBrightnessMap.put(c, defaultBrightness.get(c));
    }

    private double normalBrightness(double brightness) {
        return (brightness - minBrightness) / (maxBrightness - minBrightness);
    }

    private void updateMinBrightness() {
        for (double value : charBrightnessMap.values()) {
            if (value < minBrightness) {
                minBrightness = value;
            }
        }
    }

    private void updateMaxBrightness() {
        for (double value : charBrightnessMap.values()) {
            if (value > maxBrightness) {
                maxBrightness = value;
            }
        }
    }

    private void normalValues() {
        for (Map.Entry<Character, Double> entry : charBrightnessMap.entrySet()) {
            charBrightnessMap.replace(entry.getKey(), normalBrightness(entry.getValue()));
        }
    }

    private double getClosestBrightness(double brightness, double floor, double ceiling) {
        double floorDistanse = Math.abs(brightness - floor);
        double ceilingDistanse = Math.abs(brightness - ceiling);
        if (floorDistanse <= ceilingDistanse) {
            return floor;
        }
        return ceiling;
    }


}