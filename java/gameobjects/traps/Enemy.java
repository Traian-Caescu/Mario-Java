package gameobjects.traps;

import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import gameobjects.interfaces.Interactable;

/**
 * Represents something that can damage a player
 */
public abstract class Enemy extends GameObject implements Interactable
{
    private int damage; // The damage given to something that touches this
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     * @param image the file path to the image file for this enemy
     * @param damage the damage this enemy gives
     */
    public Enemy(float x, float y, String image, int damage)
    {
        super(x, y);
        this.damage = damage;
        setTexture(image);
    }

    /**
     * Gets the damage this does to something
     * @return the damage given
     */
    public int getDamage()
    {
        return damage;
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }
}
