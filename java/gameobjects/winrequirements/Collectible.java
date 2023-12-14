package gameobjects.winrequirements;

import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;

/**
 * Represents a game object that is picked up by the player
 */
public abstract class Collectible extends GameObject
{
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public Collectible(float x, float y)
    {
        super(x, y);
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if(interactor instanceof Character)
        {
            Character player = (Character) interactor;
            player.getInventory().addItem(this, 1);
            game.removeDrawable(this);
        }
    }
}
