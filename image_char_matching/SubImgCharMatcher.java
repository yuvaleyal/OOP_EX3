package image_char_matching;

import java.util.TreeMap;

class SubImgCharMatcher {
    private TreeMap<Character, Double> charBrightnessMap;

    public SubImgCharMatcher(char[] charset) {
        charBrightnessMap = new TreeMap<Character, Double>();
        for (char c : charset) {
            insertChar(c);
        }
        normalValues();
    }

    public char getCharByImageBrightness(double brightness) {
        return ' ';
    }

    public void addChar(char c) {
        insertChar(c);
        normalValues();
    }

    public void removeChar(char c) {
        charBrightnessMap.remove(c);
        normalValues();
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
        charBrightnessMap.put(c, getCharBrightness(c));
    }

    private void normalValues() {
        double minBrightness = 1;
        double maxBrightness = 0;
        for (double value : charBrightnessMap.values()) {
            if (value < minBrightness) {
                minBrightness = value;
            }
            if (value > maxBrightness) {
                maxBrightness = value;
            }
        }
        for (double value : charBrightnessMap.values()) {
            value = (value - minBrightness) / (maxBrightness - minBrightness);
        }
    }
}