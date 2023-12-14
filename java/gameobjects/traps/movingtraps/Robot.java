package gameobjects.traps.movingtraps;

import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import gameobjects.platforms.Platform;
import gameobjects.traps.StaticTrap;
import timer.TimerListener;

/**
 * A robot that moves side to side and hurts the player if touched
 */
public class Robot extends StaticTrap implements TimerListener, Animator
{
    private float minX;       // Minimum X value the robot can walk
    private float maxX;       // Maximum X value the robot can walk
    private float velocity;   // The speed that the robot moves

    /**
     * Creates a robot that moves left to right
     *
     * @param x the x position
     * @param y the y position
     * @param min the minimum X value this robot can walk
     * @param max the maximum X value this robot can walk
     * @param speed the speed the robot moves
     */
    public Robot(float x, float y, float min, float max, float speed)
    {
        super(x, y, "/traps/robot/robot.png", 1);
        getSprite().setScale(-1.5f, 1.5f);
        getSprite().setOrigin(getSprite().getLocalBounds().width/2, getSprite().getOrigin().y);
        getSprite().setPosition(x, y);
        minX = min;
        maxX = max;
        velocity = speed;
    }

    @Override
    public void start()
    {
        Game.getTimer().schedule(this, 6000f, false, true);
    }

    @Override
    public void onTick(Game game)
    {
        if (!safeToMove(game) || !(getX() + velocity <= maxX) || !(getX() + velocity >= minX))
        {
            velocity *= -1;
            getSprite().setScale(getSprite().getScale().x * -1, getSprite().getScale().y);
        }
        setPosition(getX() + velocity, getY());
    }

    /**
     * Gets if it is safe to continue moving in the current direction
     * @param game the game to check in
     * @return true if it is safe, false if the robot wouldn't be on a platform anymore
     */
    private boolean safeToMove(Game game)
    {
        Collider newArea = new Collider(getX() + velocity, getY(), getWidth()/2, getHeight());
        for (GameObject object : game.getCurrentLevel().getAllObjects())
        {
            if(object instanceof Platform)
            {
                if(object.isColliding(newArea))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
