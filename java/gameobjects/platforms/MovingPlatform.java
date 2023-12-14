package gameobjects.platforms;

import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import gameobjects.traps.movingtraps.Animator;
import timer.TimerListener;

/**
 * A platform that moves left to right, up and down in a given range at a given speed
 */
public class MovingPlatform extends Platform implements TimerListener, Animator
{
    private float[] xRange; // The range of X co-ords ([0] is minimum, [1] is maximum)
    private float[] yRange; // The range of Y co-ords ([0] is minimum, [1] is maximum)
    private float xSpeed;   // The speed in the x direction
    private float ySpeed;   // The speed in the y direction

    /**
     * Creates a moving platform
     * @param x the x position
     * @param y the y position
     */
    public MovingPlatform(float x, float y)
    {
        super(x, y);
        xSpeed = 0;
        ySpeed = 0;
        setSafe(false);
    }

    /**
     * Sets the minimum and maximum X values the platform can move with the given speed
     * @param xRange the range of X values [0] is minimum, [1] is maximum
     * @param xSpeed the speed that the platform moves in the x direction
     */
    public void setXRange(float[] xRange, float xSpeed)
    {
        this.xRange = xRange;
        this.xSpeed = xSpeed;
    }

    /**
     * Sets the minimum and maximum Y values the platform can move with the given speed
     * @param yRange the range of Y values [0] is minimum, [1] is maximum
     * @param ySpeed the speed that the platform moves in the y direction
     */
    public void setYRange(float[] yRange, float ySpeed)
    {
        this.yRange = yRange;
        this.ySpeed = ySpeed;
    }

    @Override
    public void onTick(Game game)
    {
        if(xSpeed != 0)
        {
            if(getX() < xRange[0] || getX() > xRange[1])
            {
                xSpeed = -xSpeed;
            }
        }
        if(ySpeed != 0)
        {
            if(getY() < yRange[0] || getY() > yRange[1])
            {
                ySpeed = -ySpeed;
            }
        }
        for (GameObject object : game.getCurrentLevel().getAllObjects())
        {
            if(object.getSprite() != null && object.getSprite().getGlobalBounds() != null)
            {
                if(new Collider(getSprite().getGlobalBounds()).isColliding(object.getCollider()) && !(object instanceof Platform))
                {
                    object.setPosition(object.getX() + xSpeed, object.getY() + ySpeed);
                }
            }
        }
        setX(getX() + xSpeed);
        setY(getY() + ySpeed);

    }

    @Override
    public void start()
    {
        double unitDist = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));
        Game.getTimer().schedule(this, (float)unitDist, true, true);
    }
}
