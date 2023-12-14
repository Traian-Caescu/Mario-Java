package rendering;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import java.io.IOException;
import java.io.InputStream;

public class GameWindow
{
    private RenderWindow gameWindow;    // The JSFML window
    private Sprite background;          // The sprite used to show the background image
    private int width;                  // Width of the window
    private int height;                 // Height of the window

    /**
     * Creates a window of size 640x480 and has a framerate of 30
     * @param title what the window should display as its title
     * @param width the width of the screen
     * @param height the height of the screen
     */
    public GameWindow(String title, int width, int height)
    {
        this.width = width;
        this.height = height;
        background = new Sprite();
        gameWindow = new RenderWindow();
        gameWindow.create(new VideoMode(width, height), title);
        gameWindow.setFramerateLimit(60);
    }

    /**
     * Sets the background of the window
     * @param resourcePath the path to the background image that is inside the resources folder format: /XXXXXXXXX.png
     * @return true if it was set, false if something went wrong (most likely image not found)
     */
    public boolean setBackground(String resourcePath)
    {
        Texture backText = new Texture();
        try
        {
            InputStream imageFile = GameWindow.class.getResourceAsStream(resourcePath);
            backText.loadFromStream(imageFile);
            imageFile.close();
            background = new Sprite(backText);
            Vector2i size = backText.getSize();
            background.scale((float)width/size.x, (float)height/size.y);
            gameWindow.draw(background);
            return true;
        }
        catch (IOException ex)
        {
            gameWindow.clear(Color.BLACK);
            return false;
        }
    }

    /**
     * Removes all objects from the window
     */
    public void clear()
    {
        if(background != null)
        {
            gameWindow.clear();
            View backgroundView = new View();
            backgroundView.setSize(gameWindow.getView().getSize());
            backgroundView.setCenter(gameWindow.getView().getSize().x/2, gameWindow.getView().getSize().y/2);
            gameWindow.setView(backgroundView);
            gameWindow.draw(background);
        }
        else
        {
            gameWindow.clear(Color.BLACK);
        }
    }

    /**
     * Gets the JSFML frame that this class uses
     * @return the window used
     */
    public RenderWindow getFrame()
    {
        return gameWindow;
    }

    /**
     * Gets the width of the window
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the height of the window
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }
}
