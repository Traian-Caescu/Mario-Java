package menu.menus;

import menu.UIElement;
import menu.elements.buttons.BackButton;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseEvent;
import rendering.Renderer;
import java.util.Stack;

/**
 * Represents a menu that is navigated via buttons and
 * other UI elements
 */
public class ButtonMenu extends Renderer
{
    private PagedMenu currentMenu;      // The current menu
    private Stack<PagedMenu> menuStack; // History of menus
    /**
     * Creates a new window
     *
     * @param title  the title of the window
     * @param width  the width in pixels
     * @param height the height in pixels
     */
    public ButtonMenu(String title, int width, int height)
    {
        super(title, width, height);
        menuStack = new Stack<>();
    }

    /**
     * Sets the current displayed layout to the past one
     * @param newMenu the new menu
     */
    public void setMenu(PagedMenu newMenu)
    {
        if(currentMenu == null)
        {
            currentMenu = newMenu;
        }
        else
        {
            menuStack.push(currentMenu);
            for (MenuLayout layout : newMenu.getPages())
            {
                layout.addDrawable(new BackButton(10, 10));
            }
        }
        loadLayout(newMenu);
    }

    /**
     * Redraws all objects in the current page
     * Called when a new PagedMenu is loaded
     */
    public void redraw()
    {
        for (Drawable drawable : getDrawables())
        {
            removeDrawable(drawable);
        }
        for (Drawable drawable : currentMenu.getCurrentPage().getDrawables())
        {
            addDrawable(drawable);
        }
        getWindow().getFrame().setTitle(currentMenu.getCurrentPage().getWindowTitle());
        getWindow().setBackground(currentMenu.getCurrentPage().getBackgroundImage());
        showHovers(Mouse.getPosition(getWindow().getFrame()));
    }

    /**
     * Loads the menu into the window
     * @param pagedMenu the menu to load
     */
    private void loadLayout(PagedMenu pagedMenu)
    {
        pagedMenu.resetPage();
        currentMenu = pagedMenu;
        redraw();
    }

    /**
     * Goes back to the previous menu if one exists
     */
    public void back()
    {
        if(menuStack.size() > 0)
        {
            loadLayout(menuStack.pop());
        }
    }

    @Override
    public void run()
    {
        getWindow().getFrame().display();
        while (isOpen())
        {
            getWindow().clear();
            for(Event event : getWindow().getFrame().pollEvents())
            {
                if (event.type == Event.Type.CLOSED)
                {
                    getWindow().getFrame().close();
                }
                if(event.type == Event.Type.MOUSE_MOVED)
                {
                    MouseEvent mouseEvent = event.asMouseEvent();
                    showHovers(mouseEvent.position);
                }
                if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
                {
                    MouseEvent mouseEvent = event.asMouseEvent();
                    UIElement elementPressed = getElementPressed(mouseEvent.position);
                    if(elementPressed != null)
                    {
                        elementPressed.onClick(this);
                    }
                }
            }
            refreshScreen();
        }
    }

    /**
     * Gets the UI element that the mouse is over
     * @param mousePosition the position of the mouse
     * @return the UI element the mouse is in or null
     */
    public UIElement getElementPressed(Vector2i mousePosition)
    {
        for (Drawable drawable : getDrawables())
        {
            if(drawable instanceof UIElement)
            {
                UIElement element = (UIElement) drawable;
                if(element.isInside(mousePosition))
                {
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * Shows the hover image for a UI Element the mouse is inside
     * @param mousePosition the position of the mouse
     */
    public void showHovers(Vector2i mousePosition)
    {
        for (Drawable drawable : getDrawables())
        {
            if(drawable instanceof UIElement)
            {
                UIElement element = (UIElement) drawable;
                element.onHoverLeave(this);
                if(element.isInside(mousePosition))
                {
                    element.onHoverEnter(this);
                }
            }
        }
    }

    /**
     * Gets the current menu that is active
     * @return the current menu
     */
    public PagedMenu getCurrentMenu()
    {
        return currentMenu;
    }
}
