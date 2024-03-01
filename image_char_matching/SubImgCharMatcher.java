package image_char_matching;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.RowFilter.Entry;

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
        TreeSet<Double> brightnesSet = new TreeSet<Double>(charBrightnessMap.values());
        double closestBrightnessFloor = brightnesSet.floor(brightness);
        double closestBrightnessCeiling = brightnesSet.ceiling(brightness);
        double newBrightness = getClosestBrightness(brightness, closestBrightnessFloor, closestBrightnessCeiling);
        Set<Map.Entry<Character, Double>> charAndBrightnessSet = charBrightnessMap.entrySet();
        for (Map.Entry<Character, Double> entry : charAndBrightnessSet) {
            if (entry.getValue() == newBrightness) {
                return entry.getKey();
            }
        }
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

    private double getClosestBrightness(double brightness, double floor, double ceiling) {
        double floorDistanse = Math.abs(brightness - floor);
        double ceilingDistanse = Math.abs(brightness - ceiling);
        if (floorDistanse <= ceilingDistanse) {
            return floor;
        }
        return ceiling;
    }

}