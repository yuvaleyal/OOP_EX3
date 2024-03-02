package ascii_art;

import image.Image;
import image.ImageSetter;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm {
    private ImageSetter image;
    private SubImgCharMatcher charSet;

    public AsciiArtAlgorithm(Image img, int res, SubImgCharMatcher chars) {
        image = new ImageSetter(img);
        image.updateResulotion(res);
        charSet = chars;
    }

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
