package uielements;

import game.Game;
import gameobjects.GameObject;
import gameobjects.player.Character;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;

/**
 * A class that pauses the game and shows a screen when it does
 */
public class PauseScreen extends Overlay
{
    private Sprite pauseImage;  // The image to display when paused

    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public PauseScreen(float x, float y)
    {
        super(x, y);
        pauseImage = GameObject.getSpriteFromImage("/pause.png");
        pauseImage.scale(0.5f, 0.5f);
        FloatRect area = pauseImage.getLocalBounds();
        pauseImage.setOrigin(area.left + area.width/2, area.height + area.height/2);
        setPosition(x, y);
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        pauseImage.setPosition(x, y);
    }

    @Override
    protected void showOverlay(Game game)
    {
        Character player = game.getCurrentLevel().getCharacter();
        setPosition(player.getX(), player.getY());
        game.addDrawable(this);
    }

    @Override
    protected void hideOverlay(Game game)
    {
        game.removeDrawable(this);
    }

    @Override
    public void draw(RenderTarget target, RenderStates states)
    {
        pauseImage.draw(target, states);
    }

    @Override
    public void onClick(Game game)
    {

    }
}
