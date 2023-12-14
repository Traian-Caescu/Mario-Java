package gameobjects.winrequirements;

import gameobjects.interfaces.Collider;

/**
 * Represents an object used to repair a Generator
 */
public class Cog extends Collectible
{
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public Cog(float x, float y)
    {
        super(x, y);
        setTexture("/generators/cog.png");
        getSprite().setScale(0.5f, 0.5f);
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }
}
