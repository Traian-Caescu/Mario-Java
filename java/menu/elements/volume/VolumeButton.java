package menu.elements.volume;

import menu.UIElement;

/**
 * Represents a button that changes the volume of music
 */
public abstract class VolumeButton extends UIElement
{
    private VolumeHandler volumeHandler;    // The volume changer

    /**
     * Creates an object that has a location on screen
     *
     * @param x the x co-ord
     * @param y the y co-ord
     */
    public VolumeButton(float x, float y, String filePath, VolumeHandler handler)
    {
        super(x, y);
        volumeHandler = handler;
        setTexture(filePath);
    }

    /**
     * Gets the volume handler associated with the button
     * @return the volume handler
     */
    public VolumeHandler getVolumeHandler()
    {
        return volumeHandler;
    }
}
