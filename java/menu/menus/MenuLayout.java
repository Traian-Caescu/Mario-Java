package menu.menus;

import org.jsfml.graphics.Drawable;
import java.util.ArrayList;

/**
 * A layout that can be loaded (like a level) to a ButtonMenu
 */
public class MenuLayout
{
    private String windowTitle;             // The title of the window
    private String backgroundPath;          // The path to the background image
    private ArrayList<Drawable> drawables;  // The elements to be drawn on the menu

    /**
     * Creates a menu layout
     * @param title the title of the window when loaded
     */
    public MenuLayout(String title, String backgroundPath)
    {
        windowTitle = title;
        drawables = new ArrayList<>();
        this.backgroundPath = backgroundPath;
    }

    /**
     * Gets the window title
     * @return the window title
     */
    public String getWindowTitle()
    {
        return windowTitle;
    }

    public String getBackgroundImage()
    {
        return backgroundPath;
    }

    /**
     * Adds a drawable to the collection
     * The order is the order in which they will be rendered
     * @param drawable the drawable to add
     */
    public void addDrawable(Drawable drawable)
    {
        drawables.add(drawable);
    }

    /**
     * Removes a drawable from the collection
     * WARNING: Only use this when it is not currently active
     * or you'll get concurrent modification issues
     * @param drawable the drawable to remove
     */
    public void removeDrawable(Drawable drawable)
    {
        drawables.remove(drawable);
    }

    /**
     * Gets the list of items to draw
     * @return the drawables to draw
     */
    public ArrayList<Drawable> getDrawables()
    {
        return drawables;
    }
}
