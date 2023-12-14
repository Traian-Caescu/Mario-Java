package gameobjects.winrequirements.coin;

import gameobjects.interfaces.Collider;
import gameobjects.winrequirements.Collectible;

/**
 * Represents a coin that the player can pick up to win the game
 */
public class Coin extends Collectible
{
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public Coin(float x, float y)
    {
        super(x, y);
        setTexture("/coin.png");
        getSprite().scale(0.5f, 0.5f);
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }
}
