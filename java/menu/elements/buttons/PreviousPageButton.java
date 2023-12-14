package menu.elements.buttons;

import menu.UIElement;
import menu.menus.ButtonMenu;
import menu.menus.PagedMenu;

/**
 * A button that goes to the previous page in a PagedMenu
 * (If one exists)
 */
public class PreviousPageButton extends UIElement
{
    /**
     * Creates a button that opens the previous page in a PagedMenu
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     */
    public PreviousPageButton(float x, float y)
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
        PagedMenu pagedMenu = menu.getCurrentMenu();
        pagedMenu.previousPage();
        menu.redraw();
    }
}
