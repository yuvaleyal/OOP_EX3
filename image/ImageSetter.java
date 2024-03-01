package image;

import java.awt.Color;

public class ImageSetter {
    private Image image;
    private int height;
    private int width;
    private int subImageSize;
    private int imageResolution;
    private double[][] brightnessArray;

    private static final double RED_MULTIPICATOR = 0.2126;
    private static final double GREEN_MULTIPICATOR = 0.7152;
    private static final double BLUE_MULTIPICATOR = 0.0722;
    private static final int MAX_RGB_VALUE = 255;

    public ImageSetter(Image img) {
        height = getClosestPowerOfTwo(img.getHeight());
        width = getClosestPowerOfTwo(img.getWidth());
        image = padImage(img);
    }

    public void updateResulotion(int res) {
        subImageSize = width / res;
        imageResolution = res;
        brightnessArray = new double[res][height / subImageSize];
        for (double[] row : brightnessArray) {
            for (double cell : row) {
                cell = -1;
            }
        }
    }

    public double getSubImageBrightness(int x, int y) {
        if (brightnessArray[x][y] >= 0) {
            return brightnessArray[x][y];
        }
        return calculateSubImageBrightness(x, y);
    }

    private int getClosestPowerOfTwo(int num) {
        double logNum = Math.log(num) / Math.log(2);
        return (int) Math.ceil(Math.pow(2, logNum));
    }

    private Image padImage(Image image) {
        int ogWidth = image.getWidth();
        int ogHeight = image.getHeight();
        Color[][] pixelArray = new Color[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixelArray[row][col] = Color.WHITE;
            }
        }
        int heightBuffer = (height - ogHeight) / 2;
        int widthBuffer = (width - ogWidth) / 2;
        for (int row = 0; row < ogHeight; row++) {
            for (int col = 0; col < ogWidth; col++) {
                pixelArray[row + heightBuffer][col + widthBuffer] = image.getPixel(row,
                        col);
            }
        }
        return new Image(pixelArray, width, height);
    }

    private Image getSubImage(int x, int y) {
        Color[][] pixelArray = new Color[subImageSize][subImageSize];
        int firstCoordinateX = x * subImageSize;
        int firstCoordinateY = y * subImageSize;
        for (int row = 0; row < subImageSize; row++) {
            for (int col = 0; col < subImageSize; col++) {
                pixelArray[row][col] = image.getPixel(firstCoordinateX + row,
                        firstCoordinateY + col);
            }
        }
        return new Image(pixelArray, subImageSize, subImageSize);
    }

    private double getGrey(Color c) {
        return c.getRed() * RED_MULTIPICATOR + c.getGreen() * GREEN_MULTIPICATOR
                + c.getBlue() * BLUE_MULTIPICATOR;
    }

    private double calculateSubImageBrightness(int x, int y) {
        Image subImnage = getSubImage(x, y);
        double greySum = 0;
        for (int row = 0; row < subImnage.getHeight(); row++) {
            for (int col = 0; col < subImnage.getWidth(); col++) {
                greySum += getGrey(subImnage.getPixel(row, col));
            }
        }
        int pixelsInImag = subImnage.getHeight() * subImnage.getWidth();
        brightnessArray[x][y] = greySum / (pixelsInImag * MAX_RGB_VALUE);
        return brightnessArray[x][y];
    }

    public static class BrightnessMemento {
        private final double[][] brightnessArray;

        public BrightnessMemento(double[][] brightnessArray) {
            this.brightnessArray = brightnessArray;
        }
    }
}
