package menu.menus;

import menu.UIElement;
import org.jsfml.system.Vector2i;

/**
 * A basic coloured rectangle that fits across the screen
 */
public class Banner extends UIElement
{
    /**
     * Creates a banner that has a location on screen
     *
     * @param x the x co-ord
     * @param y the y co-ord
     */
    public Banner(float x, float y)
    {
        super(x, y);
        setTexture("/ui/banner.png");
    }

    @Override
    public boolean isInside(Vector2i mousePosition)
    {
        return false;
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {

    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {

    }

    @Override
    public void onClick(ButtonMenu menu)
    {

    }
}
