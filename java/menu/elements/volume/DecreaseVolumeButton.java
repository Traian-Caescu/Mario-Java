package menu.elements.volume;

import menu.menus.ButtonMenu;

/**
 * Decreases the background volume
 */
public class DecreaseVolumeButton extends VolumeButton
{
    /**
     * Creates a button that has a location on screen
     * Decreases the volume when pressed
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     */
    public DecreaseVolumeButton(float x, float y, VolumeHandler handler)
    {
        super(x, y, "/ui/decrement.png", handler);
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        setTexture("/ui/decrementHover.png");
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        setTexture("/ui/decrement.png");
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        getVolumeHandler().decrementVolume();
    }
}
