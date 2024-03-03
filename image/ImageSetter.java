package image;

import java.awt.Color;

/**
 * Represents a class responsible for processing and managing images for ASCII art generation.
 */
public class ImageSetter {
    private Image image;
    private int height;
    private int width;
    private int subImageSize;
    private int imageResolution;
    private double[][] brightnessArray;
    private SubImagesHistory history;

    private static final double RED_MULTIPICATOR = 0.2126;
    private static final double GREEN_MULTIPICATOR = 0.7152;
    private static final double BLUE_MULTIPICATOR = 0.0722;
    private static final int MAX_RGB_VALUE = 255;
    private static final int DEFAULT_RESOLUTION = 128;

    /**
     * Constructs a new ImageSetter instance with the specified image.
     *
     * @param img
     *            The image to be processed.
     */
    public ImageSetter(Image img) {
        height = getClosestPowerOfTwo(img.getHeight());
        width = getClosestPowerOfTwo(img.getWidth());
        image = padImage(img);
        history = new SubImagesHistory();
        imageResolution = DEFAULT_RESOLUTION;
    }

    /**
     * Updates the resolution of the image for processing.
     *
     * @param res
     *            The new resolution of the image.
     */
    public void updateResulotion(int res) {
        if (res == imageResolution) {
            return;
        }
        history.saveSubImage(imageResolution, new BrightnessMemento(brightnessArray));
        subImageSize = width / res;
        imageResolution = res;
        BrightnessMemento brightnessArrayMemento = history.getSubImageByResolution(res);
        if (brightnessArrayMemento == null) {
            brightnessArray = new double[res][height / subImageSize];
            updateBrightnessArray();
        } else {
            brightnessArray = brightnessArrayMemento.brightnessArray;
        }
    }

    /**
     * Retrieves the brightness array representing the sub-images of the processed
     * image.
     *
     * @return The brightness array representing the sub-images.
     */
    public double[][] getSubImagesResolutions() {
        return brightnessArray;
    }

    /**
     * Retrieves the width of the image.
     *
     * @return The width of the image.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Retrieves the height of the image.
     *
     * @return The height of the image.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Retrieves the resolution of the image.
     *
     * @return The resolution of the image.
     */
    public int getResolution() {
        return imageResolution;
    }

    private int getClosestPowerOfTwo(int num) {
        double logNum = Math.log(num) / Math.log(2);
        return (int) (Math.pow(2, Math.ceil(logNum)));
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

    private void updateBrightnessArray() {
        int heightRes = height / subImageSize;
        for (int row = 0; row < heightRes; row++) {
            for (int col = 0; col < imageResolution; col++) {
                brightnessArray[row][col] = calculateSubImageBrightness(row, col);
            }
        }
    }

    /**
     * A memento class used to store the brightness array state.
     */
    public static class BrightnessMemento {
        private final double[][] brightnessArray;

        /**
         * Constructs a new BrightnessMemento with the specified brightness array.
         *
         * @param brightnessArray
         *            The brightness array to be stored in the memento.
         */
        public BrightnessMemento(double[][] brightnessArray) {
            this.brightnessArray = brightnessArray;
        }
    }
}
