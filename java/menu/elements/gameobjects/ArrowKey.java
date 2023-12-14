package menu.elements.gameobjects;

import menu.UIElement;
import menu.menus.ButtonMenu;

/**
 * Draws an up arrow on screen
 */
public class ArrowKey extends UIElement
{
    /**
     * Creates an up arrow
     *
     * @param x the x co-ord
     * @param y the y co-ord
     */
    public ArrowKey(float x, float y)
    {
        super(x, y);
        setTexture("/ui/movement/up.png");
        getSprite().setOrigin(getSprite().getLocalBounds().width/2, getSprite().getLocalBounds().height/2);
    }

    /**
     * Sets the rotation of the UI element
     * @param rotation the rotation, in degrees
     */
    public void setRotation(float rotation)
    {
        getSprite().setRotation(rotation);
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
