package image;

import java.awt.Color;

public class ImageSetter {
    private Image image;

    public ImageSetter(Image img) {
        image = padImage(img);
    }

    private int getClosestPowerOfTwo(int num) {
        double logNum = Math.log(num) / Math.log(2);
        return (int) Math.ceil(Math.pow(2, logNum));
    }

    private Image padImage(Image image) {
        int ogWidth = image.getWidth();
        int ogHeight = image.getHeight();
        int width = getClosestPowerOfTwo(ogWidth);
        int height = getClosestPowerOfTwo(ogHeight);
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

    // res is a power of 2
    private void createSubImages(int res) {

    }
}
