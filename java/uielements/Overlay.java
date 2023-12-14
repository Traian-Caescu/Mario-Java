package uielements;

import game.Game;
import gameobjects.Positional;
import gameobjects.interfaces.Clickable;
import org.jsfml.graphics.*;

/**
 * Represents something that appears over the main game
 */
public abstract class Overlay extends Positional implements Clickable, Drawable
{
    private boolean isDisplayed;
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public Overlay(float x, float y)
    {
        super(x, y);
        isDisplayed = false;
    }

    /**
     * Gets if the overlay is visible
     * @return true if visible, false otherwise
     */
    public boolean isDisplaying()
    {
        return isDisplayed;
    }

    /**
     * Sets whether it the overlay is visibile or not
     * @param display true if visible, false otherwise
     */
    public void setDisplayed(boolean display)
    {
        isDisplayed = display;
    }

    /**
     * Displays the overlay on screen
     * @param game the game this is displayed over
     */
    public void show(Game game)
    {
        setDisplayed(true);
        showOverlay(game);
        game.setPaused(true);
    }

    /**
     * Hides the overlay
     * @param game the game the overlay is displayed over
     */
    public void hide(Game game)
    {
        setDisplayed(false);
        hideOverlay(game);
        game.setPaused(false);
    }

    /**
     * Called when show() is called, should be used to display the overlay
     * @param game the game to display over
     */
    protected abstract void showOverlay(Game game);

    /**
     * Called when hide() is called, should be used to hide the overlay
     * @param game the game to display over
     */
    protected abstract void hideOverlay(Game game);

    @Override
    public abstract void draw(RenderTarget target, RenderStates states);
}
