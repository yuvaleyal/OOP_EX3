package ascii_art;

import image.Image;
import image.ImageSetter;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm {
    private ImageSetter image;
    private SubImgCharMatcher charSet;

    /**
     * Constructs a new AsciiArtAlgorithm instance with the specified parameters.
     *
     * @param img
     *            The image to be processed.
     * @param res
     *            The resolution of the output ASCII art.
     * @param chars
     *            The character set used for mapping pixel intensity to characters.
     */
    public AsciiArtAlgorithm(Image img, int res, SubImgCharMatcher chars) {
        image = new ImageSetter(img);
        image.updateResulotion(res);
        charSet = chars;
    }

    /**
     * Runs the ASCII art algorithm to generate the ASCII representation of the
     * input image.
     *
     * @return A two-dimensional array representing the ASCII art.
     */
    public char[][] run() {
        double[][] dividedImg = image.getSubImagesResolutions();
        int height = dividedImg[0].length;
        int width = dividedImg.length;
        char[][] result = new char[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = charSet.getCharByImageBrightness(dividedImg[row][col]);
            }
        }
        return result;
    }
}
