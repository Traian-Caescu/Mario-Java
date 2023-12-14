package menu.elements.buttons;

import menu.UIElement;
import menu.menus.ButtonMenu;
import menu.menus.PagedMenu;

/**
 * A button that goes to the next page in a PagedMenu
 * (If one exists)
 */
public class NextPageButton extends UIElement
{
    /**
     * Creates a button that goes to the next page in a PagedMenu
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     */
    public NextPageButton(float x, float y)
    {
        super(x, y);
        setTexture("/ui/next.png");
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        setTexture("/ui/nextHover.png");
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        setTexture("/ui/next.png");
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        PagedMenu pagedMenu = menu.getCurrentMenu();
        pagedMenu.nextPage();
        menu.redraw();
    }
}
