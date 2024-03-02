package image;

import java.util.TreeMap;
import image.ImageSetter.BrightnessMemento;

public class SubImagesHistory {
    private TreeMap<Integer, BrightnessMemento> history;

    /**
     * Constructs a new SubImagesHistory instance to store the history of brightness
     * mementos.
     */
    public SubImagesHistory() {
        history = new TreeMap<Integer, BrightnessMemento>();
    }

    /**
     * Saves a brightness memento for a specific resolution in the history.
     *
     * @param resolution
     *            The resolution of the sub-image.
     * @param memento
     *            The brightness memento to be saved.
     */
    public void saveSubImage(int resolution, BrightnessMemento memento) {
        if (history.containsKey(resolution)) {
            return;
        }
        history.put(resolution, memento);
    }

    /**
     * Retrieves the brightness memento for a specific resolution from the history.
     *
     * @param resolution
     *            The resolution of the sub-image.
     * @return The brightness memento corresponding to the specified resolution, or
     *         null if not found.
     */
    public BrightnessMemento getSubImageByResolution(int resolution) {
        if (history.containsKey(resolution)) {
            return history.get(resolution);
        }
        return null;
    }
}
