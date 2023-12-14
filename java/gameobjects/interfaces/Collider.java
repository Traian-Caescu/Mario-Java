package gameobjects.interfaces;

import org.jsfml.graphics.FloatRect;

/**
 * Represents a box in which a collision can happen with another box
 */
public class Collider
{
    private final FloatRect collisionBox;   // The rectangle that the collision box is

    /**
     * Creates a new collider
     * @param x the left-most x position
     * @param y the top y position
     * @param width the width of the box
     * @param height the height of the box
     */
    public Collider(float x, float y, float width, float height)
    {
        collisionBox = new FloatRect(x, y, width, height);
    }

    /**
     * Creates a new collider
     * @param box the rectangle that this collider is
     */
    public Collider(FloatRect box)
    {
        collisionBox = box;
    }

    /**
     * Gets if one box is colliding with another
     * @param collider the box to test for
     * @return true if there is a collision, false otherwise
     */
    public boolean isColliding(Collider collider)
    {
        if(collider != null)
        {
            return collisionBox.intersection(collider.getCollisionBox()) != null;
        }
        return false;
    }

    /**
     * Gets the rectangle that represents this collision box
     * @return the box that is the collision area
     */
    public FloatRect getCollisionBox()
    {
        return collisionBox;
    }

    @Override
    public String toString()
    {
        String description = "X: " + collisionBox.left + " Y: " + collisionBox.top;
        description += "\nW: " + collisionBox.width + " H: " + collisionBox.height;
        return description;
    }
}
