package gameobjects.powerups.segmentdisplay;

import game.Game;
import gameobjects.GameObject;
import gameobjects.powerups.PowerUp;
import menu.UIElement;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import timer.TimerListener;

/**
 * Represents a powerup and a duration bar of how long is left
 * for it to deactivate
 */
public class PowerUpSegment extends UIElement implements TimerListener
{
    private PowerUp powerup;            // The powerup that is being timed
    private Sprite powerupBar;          // The progress bar
    private float decrement;            // The value to decrement the progress bar by each second
    private float rollingScale;         // The current scale of the progress bar (to show time left)
    private PowerUpDisplay displayer;   // The class that is displaying this segment
    /**
     * Creates a segment which shows duration of a powerup
     *
     * @param x the x co-ord
     * @param y the y co-ord
     * @param powerup the powerup to display
     * @param displayer the object which displays this segment
     */
    public PowerUpSegment(float x, float y, PowerUp powerup, PowerUpDisplay displayer)
    {
        super(x, y);
        this.powerup = powerup;
        FloatRect bounds = powerup.getSprite().getLocalBounds();
        powerup.getSprite().setOrigin(bounds.width/2, bounds.height/2);
        setTexture("/powerups/powerupSegment.png");
        getSprite().setScale(0.5f, 0.5f);
        powerup.getSprite().setScale(0.04f, 0.04f);
        powerupBar = GameObject.getSpriteFromImage("/powerups/powerupBar.png");
        powerupBar.setScale(0.5f, 0.5f);
        setPosition(x, y);
        decrement = 0.5f/(powerup.getDuration() / 1000f);
        rollingScale = 0.5f;
        this.displayer = displayer;
        Game.getTimer().schedule(this, 1000f, false, true);

    }

    /**
     * Sets the duration of the progress bar
     * Done automatically when first made
     * @param duration the duration in milliseconds
     */
    public void setDuration(int duration)
    {
        decrement = 0.5f/(duration / 1000f);
        rollingScale = 0.5f;
        powerupBar.setScale(rollingScale, 0.5f);
    }

    /**
     * Gets the remaining time left
     * @return the remaining time in seconds
     */
    public int getRemainingDuration()
    {
        float diff = (0.5f - rollingScale)/decrement;
        return Math.round(diff);
    }

    @Override
    public void setPosOnScreen(float x, float y)
    {
        getSprite().setPosition(x, y);
        powerup.setPosition(x + 30, y + 25);
        powerupBar.setPosition(x + 63, y  + 4);
    }

    @Override
    public void draw(RenderTarget target, RenderStates states)
    {
        getSprite().draw(target, states);
        powerup.draw(target, states);
        powerupBar.draw(target, states);
    }

    @Override
    public void onTick(Game game)
    {
        rollingScale -= decrement;
        powerupBar.setScale(rollingScale, 0.5f);
        if(rollingScale <= 0.0001f) // Floating point issue
        {
            Game.getTimer().remove(this);
            displayer.removePowerup(powerup);

        }
    }

    /**
     * Gets the powerup
     * @return the powerup
     */
    public PowerUp getPowerup()
    {
        return powerup;
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {

    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {

    }

    @Override
    public void onClick(ButtonMenu menu)
    {

    }

}
