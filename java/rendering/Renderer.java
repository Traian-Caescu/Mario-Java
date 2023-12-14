package rendering;

import org.jsfml.graphics.Drawable;
import java.util.ArrayList;

/**
 * A class to wrap a few helpful frame-based activities
 */
public abstract class Renderer
{
    private GameWindow window;                      // The window that everything is displayed on
    private ArrayList<Drawable> drawables;          // Objects currently drawn
    private ArrayList<Drawable> objectsToRemove;    // Objects that should be removed from view

    /**
     * Creates a new window
     * @param title the title of the window
     * @param width the width in pixels
     * @param height the height in pixels
     */
    public Renderer(String title, int width, int height)
    {
        drawables = new ArrayList<>();
        objectsToRemove = new ArrayList<>();
        window = new GameWindow(title, width, height);
    }

    /**
     * Redraws all objects on screen (should be called many times)
     */
    public void refreshScreen()
    {
        drawables.removeAll(objectsToRemove);
        objectsToRemove.clear();
        for (Drawable object : drawables)
        {
            window.getFrame().draw(object);
        }
        window.getFrame().display();
    }

    /**
     * Closes the window
     */
    public void close()
    {
        window.getFrame().close();
    }

    /**
     * Adds a drawable onto the window and draws it
     * @param drawable the item to draw
     */
    public void addDrawable(Drawable drawable)
    {
        drawables.add(drawable);
    }

    /**
     * Sets an item to be removed on next refresh
     * @param drawable the item to remove
     */
    public void removeDrawable(Drawable drawable)
    {
        objectsToRemove.add(drawable);
    }

    /**
     * Gets the list of items being drawn on screen
     * @return the items on screen
     */
    public ArrayList<Drawable> getDrawables()
    {
        return drawables;
    }

    /**
     * The main body of the screen where all events are polled
     */
    public abstract void run();

    /**
     * Gets if the window is open
     * @return true if it is open, false otherwise
     */
    public boolean isOpen()
    {
        return getWindow().getFrame().isOpen();
    }

    /**
     * Gets the window that things are drawn on to
     * @return the window
     */
    public GameWindow getWindow()
    {
        return window;
    }
}
