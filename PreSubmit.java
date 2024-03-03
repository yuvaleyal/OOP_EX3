//import ascii_art.PresubmitAlgorithms;
import ascii_art.PresumbitShell;
import ascii_art.Testing;
import ascii_art.img_to_char.PresubmitBrightnessImgCharMatcher;
import ascii_art.img_to_char.PresubmitCharRenderer;
import image.PresubmitImage;

public class PreSubmit {

    public static void main(String[] args){
        Testing[] toTest = {new PresubmitCharRenderer(), new PresubmitBrightnessImgCharMatcher(),
                new PresumbitShell(), new PresubmitImage()};
        for (Testing c: toTest) {
            c.test();
        }
    }
}

