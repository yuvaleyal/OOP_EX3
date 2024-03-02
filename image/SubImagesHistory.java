package image;

import java.util.TreeMap;
import image.ImageSetter.BrightnessMemento;

public class SubImagesHistory {
    private TreeMap<Integer, BrightnessMemento> history;

    public SubImagesHistory() {
        history = new TreeMap<Integer, BrightnessMemento>();
    }

    public void saveSubImage(int resolution, BrightnessMemento memento) {
        if (history.containsKey(resolution)) {
            return;
        }
        history.put(resolution, memento);
    }

    public BrightnessMemento getSubImageByResolution(int resolution) {
        if (history.containsKey(resolution)) {
            return history.get(resolution);
        }
        return null;
    }
}
