package menu.elements.volume;

import menu.menus.ButtonMenu;

/**
 * Button that increases the background music volume when pressed
 */
public class IncreaseVolumeButton extends VolumeButton
{
    /**
     * Creates an object that has a location on screen, increases
     * background volume when pressed
     *
     * @param x        the x co-ord
     * @param y        the y co-ord
     * @param handler the button image
     */
    public IncreaseVolumeButton(float x, float y, VolumeHandler handler)
    {
        super(x, y, "/ui/increment.png", handler);
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        setTexture("/ui/incrementHover.png");
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        setTexture("/ui/increment.png");
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        getVolumeHandler().incrementVolume();
    }
}
