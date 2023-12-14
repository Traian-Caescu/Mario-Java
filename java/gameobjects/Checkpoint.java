package gameobjects;

import game.Game;
import gameobjects.interfaces.Collider;
import gameobjects.player.Character;
import org.jsfml.system.Vector2f;
import timer.TimerListener;

/**
 * Represents a game object that the player can teleport to if they die
 */
public class Checkpoint extends GameObject implements TimerListener
{
    private boolean isActivated;    // True if the player has activated this, false otherwise
    private int stage;

    /**
     * Creates a de-activated checkpoint
     * @param x the x position
     * @param y the y position
     */
    public Checkpoint(float x, float y) {
        super(x, y);
        stage = 0;
        isActivated = false;
        setTexture("/checkpoint/noFlag.png");
        getSprite().setScale(new Vector2f(0.5f, 0.5f));
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        isActivated = true;
        if(interactor instanceof Character)
        {
            Game.getTimer().schedule(this, 3000f, true, true);
            game.getCurrentLevel().getCheckpointCollection().activateCheckpoint(this);
        }
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }

    /**
     * Sets the checkpoint without visually changing its state
     * @param active true if the checkpoint is active, false otherwise
     */
    public void silentSetActive(boolean active)
    {
        isActivated = active;
    }

    /**
     * Sets the condition of the checkpoint
     * done automatically when collided with
     * @param active true if active, false otherwise
     */
    public void setActive(boolean active)
    {
        if(active)
        {
            setTexture("/checkpoint/fullFlag.png");
            getSprite().setScale(new Vector2f(0.5f, 0.5f));
            isActivated = true;
            stage = 2;
        }
        else
        {
            setTexture("/checkpoint/noFlag.png");
            getSprite().setScale(new Vector2f(0.5f, 0.5f));
            isActivated = false;
            stage = 0;
        }
    }

    /**
     * Gets if the checkpoint has been activated
     * @return true if it is active, false otherwise
     */
    public boolean isActivated()
    {
        return isActivated;
    }

    @Override
    public void onTick(Game game)
    {
        if(stage == 0)
        {
            setTexture("/checkpoint/halfFlag.png");
            getSprite().setScale(new Vector2f(0.5f, 0.5f));
            stage++;
        }
        else if(stage == 1)
        {
            setTexture("/checkpoint/fullFlag.png");
            getSprite().setScale(new Vector2f(0.5f, 0.5f));
            stage++;
        }
        else
        {
            Game.getTimer().remove(this);
        }
    }
}
