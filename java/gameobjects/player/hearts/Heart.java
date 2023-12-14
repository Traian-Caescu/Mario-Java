package gameobjects.player.hearts;

import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;

/**
 * Represents a single health unit for the player
 */
public class Heart extends GameObject
{
    /**
     * Creates a new Heart
     *
     * @param x the x position
     * @param y the y position
     */
    public Heart(float x, float y)
    {
        super(x, y);
        setTexture("/heart.png");
        getSprite().setScale(0.2f, 0.2f);
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {

    }

    @Override
    public Collider getCollider()
    {
        return null;
    }
}
