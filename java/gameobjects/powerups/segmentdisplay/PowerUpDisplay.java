package gameobjects.powerups.segmentdisplay;

import gameobjects.powerups.PowerUp;
import menu.UIElement;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import java.util.ArrayList;

/**
 * Shows powerups on screen so you know what is active
 */
public class PowerUpDisplay extends UIElement
{
    private ArrayList<PowerUpSegment> segments; // THe powerups
    /**
     * Creates a Powerup displayer
     *
     * @param x the x position
     * @param y the y position
     */
    public PowerUpDisplay(float x, float y)
    {
        super(x, y);
        segments = new ArrayList<>();
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

    /**
     * Adds a powerup to the screen or increases the duration of an existing one
     * @param powerup the powerup to add
     */
    public void addPowerup(PowerUp powerup)
    {
        boolean found = false;
        for (PowerUpSegment segment : segments)
        {
            if(segment.getPowerup().getClass().equals(powerup.getClass()))
            {
                found = true;
                if(powerup.getDuration() > segment.getRemainingDuration())
                {
                    segment.setDuration(powerup.getDuration());
                }
            }
        }
        if(!found)
        {
            segments.add(new PowerUpSegment(getX(), getY(), powerup, this));
        }
        setPosOnScreen(getX(), getY());
    }

    /**
     * Remove the powerup from view
     * @param powerup the powerup to remove
     */
    public void removePowerup(PowerUp powerup)
    {
        PowerUpSegment segmentFound = null;
        for (PowerUpSegment segment : segments)
        {
            if(segment.getPowerup().equals(powerup))
            {
                segmentFound = segment;
            }
        }
        if(segmentFound != null)
        {
            segments.remove(segmentFound);
            setPosOnScreen(getX(), getY());
        }
    }

    /**
     * Removes all powerups from display
     */
    public void clear()
    {
        segments.clear();
    }


    @Override
    public void setPosOnScreen(float x, float y)
    {
        for (PowerUpSegment segment : segments)
        {
            segment.setPosOnScreen(x, y);
            y += 60;
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states)
    {
        for (PowerUpSegment segment : segments)
        {
            segment.draw(target, states);
        }
    }
}
