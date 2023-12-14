package gameobjects.platforms;

import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;

/**
 * Represents a game object that a player can stand on
 */
public class Platform extends GameObject
{
    private boolean isSafe;
    /**
     * Creates a platform
     *
     * @param x x position of platform
     * @param y y position of platform
     */
    public Platform(float x, float y)
    {
        super(x, y);
        setTexture("/platform.png");
        getSprite().scale(0.2f, 0.2f);
        setPosition(x, y);
        isSafe = true;
    }

    public void setSafe(boolean safe)
    {
        isSafe = safe;
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if (interactor instanceof Character)
        {
            Character player = (Character) interactor;
            if(player.getX() + player.getWidth()/2 - 5 >= getX() && player.getX() - player.getWidth()/2 <= getX() + getWidth())
            {
                if (player.getY() + player.getJumpHandler().getLastYJump() >= getY() || player.getY() >= getY())
                {
                    player.getJumpHandler().hitHead();
                    player.setY(player.getY() + player.getJumpHandler().getLastYJump());
                    player.setPreviousPosition(player.getX(), player.getY());

                }
                else if(player.getY()  + (player.getHeight() - 30) < getY() || (player.getPrevYPos() < getY() && player.getY() + player.getHeight() > getY() && !player.canJump()))
                {
                    if(isSafe && !game.playerInDanger())
                    {
                        player.setSafePos(player.getX(), getY() - player.getHeight() + 10);
                    }
                    else
                    {
                        player.setSafe(true);
                    }
                    player.setJump(true);
                    player.setY(getY() - player.getHeight() + 10);
                    player.setPreviousPosition(player.getX(), player.getY());
                }
            }
        }
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getX(), getY(), getWidth(), 2);
    }
}