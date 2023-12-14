package gameobjects.interfaces;

import game.Game;
import gameobjects.GameObject;

/**
 * A class which represents something that can take damage
 */
public abstract class KillableEntity extends GameObject
{
    private int lives;      // The current lives it has
    private int baseLives;  // The amount of total lives this entity can have
    private boolean isDown;

    /**
     * Creates a new KillableEntity with a give X,Y position and set of lives
     * @param x the current X position
     * @param y the current Y position
     * @param lives the total lives it can have
     */
    public KillableEntity(float x, float y, int lives)
    {
        super(x, y);
        baseLives = lives;
        this.lives = lives;
        isDown = false;
    }

    /**
     * Increases the current lives
     * @param lives the lives to add
     * @param game the game this affects
     */
    public void addLives(int lives, Game game)
    {
        this.lives += lives;
        if(game != null)
        {
            onLivesChanged(game);
        }
    }

    /**
     * Sets the total and current lives count
     * @param lives the lives the entity should have
     * @param game the game this affects
     */
    public void setLives(int lives, Game game)
    {
        this.lives = lives;
        baseLives = lives;
        if(game != null)
        {
            onLivesChanged(game);
        }
    }

    /**
     * Gets the current lives of the entity
     * @return the current lives
     */
    public int getLives()
    {
        return lives;
    }

    /**
     * Gets the total lives possible
     * @return the total lives
     */
    public int getBaseLives()
    {
        return baseLives;
    }

    /**
     * Gets if the entity has died (lives is 0 or less)
     * @return true if dead, false otherwise
     */
    public boolean isDead()
    {
        return this.lives <= 0;
    }

    /**
     * Called when the lives of the entity change
     * @param game the game this affects
     */
    protected abstract void onLivesChanged(Game game);

    public boolean isDown()
    {
        return isDown;
    }

    public void setDown(boolean down)
    {
        isDown = down;
    }

}
