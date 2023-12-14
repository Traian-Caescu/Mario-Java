package gameobjects.platforms;

import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;

/**
 * A wall, the player can't move passed it
 */
public class Wall extends Platform
{
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public Wall(float x, float y)
    {
        super(x, y);
        setTexture("/wall.png");
        getSprite().scale(0.5f, 0.5f);
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if(interactor instanceof Character)
        {
            Character player = (Character) interactor;
            if(player.getY() + player.getHeight() - 30 > getY() && player.getY() + 30 < getY() + getHeight())
            {
                if(player.getX() < getX())
                {
                    if(player.getX() + player.getWidth()/2 <= getX() + 10 && player.facingRight())
                    {
                        player.setX(getX() - player.getWidth()/2 - game.getScrollSpeed());
                    }
                }
                else
                {
                    player.setX(player.getPrevXPos() + game.getScrollSpeed());
                }
                player.setPreviousPosition(player.getX(), player.getY());
            }
            else
            {
                super.onInteraction(game, interactor);
            }
        }
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }
}
