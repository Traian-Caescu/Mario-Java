package menu.elements.buttons;

import menu.UIElement;
import menu.menus.ButtonMenu;

/**
 * A button that goes to the previous PagedMenu in a ButtonMenu
 */
public class BackButton extends UIElement
{
    /**
     * Creates a button that it used to go to the previous PagedMenu
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     */
    public BackButton(float x, float y)
    {
        super(x, y);
        setTexture("/ui/back.png");
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        setTexture("/ui/backHover.png");
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        setTexture("/ui/back.png");
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        menu.back();
    }
}
