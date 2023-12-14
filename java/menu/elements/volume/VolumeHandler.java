package menu.elements.volume;

import game.GameSettings;
import gameobjects.GameObject;
import menu.UIElement;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2i;

/**
 * A class that handles the changing of volume and displaying that information
 * Only provides visual change, does not change actual volume
 */
public class VolumeHandler extends UIElement
{
    private int currentVolume;          // The current volume
    private final int MAX_VOLUME = 100; // Max volume allowed
    private Text volumeDisplay;         // The text that displays the volume
    private GameSettings settings;      // The settings to change

    /**
     * Creates a textual display of the current volume
     *
     * @param x the x co-ord of the text
     * @param y the y co-ord of the text
     * @param colour the colour of the text of the volume
     * @param settings the game settings that should be edited
     */
    public VolumeHandler(float x, float y, Color colour, GameSettings settings)
    {
        super(x, y);
        currentVolume = settings.getVolume();
        volumeDisplay = GameObject.createText(currentVolume + "", colour);
        volumeDisplay.setPosition(x, y);
        this.settings = settings;
    }

    /**
     * Increment the volume by 10
     * @return the new volume or 100 if the max was hit
     */
    public int incrementVolume()
    {
        if(currentVolume < MAX_VOLUME)
        {
            currentVolume += 10;
        }
        volumeDisplay.setString(currentVolume + "");
        settings.setVolume(currentVolume);
        return currentVolume;
    }

    /**
     * Decrement the volume by 10
     * @return the new volume or 0 if minimum was hit
     */
    public int decrementVolume()
    {
        if(currentVolume > 0)
        {
            currentVolume -= 10;
        }
        volumeDisplay.setString(currentVolume + "");
        settings.setVolume(currentVolume);
        return currentVolume;
    }

    /**
     * Sets the volume
     * @param volume the current volume
     */
    public void setVolume(int volume)
    {
        this.currentVolume = volume;
        volumeDisplay.setString(currentVolume + "");
        settings.setVolume(currentVolume);
    }

    /**
     * Gets the current volume
     * @return the current volume
     */
    public int getVolume()
    {
        return currentVolume;
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        volumeDisplay.setPosition(x, y);
    }

    @Override
    public boolean isInside(Vector2i mousePosition)
    {
        return false;
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        // Do nothing
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        // Do nothing
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        // Do nothing
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        volumeDisplay.draw(renderTarget, renderStates);
    }
}
