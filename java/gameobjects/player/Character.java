package gameobjects.player;

import audio.SoundPlayer;
import audio.SoundType;
import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import gameobjects.interfaces.KillableEntity;
import gameobjects.powerups.PowerUp;
import timer.TimerListener;
import uielements.inventory.Inventory;
import java.util.ArrayList;

/**
 * Represents a player in the game
 */
public class Character extends KillableEntity implements TimerListener
{
    private boolean canJump;        // True if the player is able to jump, false otherwise
    private boolean playerSafe;     // True if the player is standing on something, false otherwise
    private boolean isWalking;      // True if the player is currently walking, false otherwise
    private int walkTexture;        // Represents the current walk texture (for walking animation)
    private boolean facingRight;    // True if they are facing right (->) false otherwise
    private Inventory inventory;    // The player's inventory
    private float[] lastSafePos;    // The last X,Y Position of the player that was safe
    private boolean isInvincible;   // True if player is invincible (Due to power-up), false otherwise
    private JumpHandler jumpHandler;// The system for handling the player's movement when jumping and falling
    private SoundPlayer walkingSound;
    private ArrayList<PowerUp> activePowerups;

    /**
     * Creates the player
     * @param x the x position
     * @param y the y position
     * @param image the image path of the player
     */
    public Character(float x, float y,String image) {
        super(x, y, 3);
        setTexture(image);
        walkTexture = 1;
        walkingSound = new SoundPlayer();
        walkingSound.setVolume(10);
        getSprite().scale(1f, 1f);
        getSprite().setOrigin(getSprite().getTexture().getSize().x/2, getSprite().getOrigin().y);
        setPosition(x,y);
        playerSafe = true;
        lastSafePos = new float[2];
        setSafePos(x, y);
        facingRight = true;
        isWalking = false;
        inventory = new Inventory();
        isInvincible = false;
        canJump = true;
        jumpHandler = new JumpHandler();
        activePowerups = new ArrayList<>();
    }

    /**
     * Turns the player to the right
     */
    public void turnRight(){
        getSprite().setScale(1f, 1f);
        facingRight = true;
    }

    /**
     * Turns the player to the left
     */
    public void turnLeft(){
        getSprite().setScale(-1f, 1f);
        facingRight = false;
    }

    /**
     * Sets the player to do the walking animation if true
     * @param walking true if walking, false if the player is standing still
     */
    public void setWalking(boolean walking)
    {
        if (isWalking != walking)
        {
            isWalking = walking;
            if (isWalking)
            {
                Game.getTimer().schedule(this, 150f, true, true);
            }
            else
            {
                Game.getTimer().remove(this);
                Game.getTimer().schedule(listener -> setDirectionTexture("/character/walk1.png"), 10f, false, false);
            }
        }
    }

    /**
     * Gets if player is invincible
     * @return True if invincible, false otherwise
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Sets if player is invincible
     * @param invincible True for invincible, false otherwise
     */
    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    /**
     * Gets if the player is standing on something
     * @return true if the player is safe, false otherwise
     */
    public boolean isSafe()
    {
        return playerSafe;
    }

    /**
     * Gets if the player is able to jump
     * @return true if the player can jump, false otherwise
     */
    public boolean canJump()
    {
        return canJump;
    }

    /**
     * Sets if the player can jump
     * @param jump true if they can jump, false otherwise
     */
    public void setJump(boolean jump)
    {
        canJump = jump;
        if(!canJump)
        {
            playerSafe = false;
        }
    }

    /**
     * Gets the player's inventory
     * @return the player's inventory
     */
    public Inventory getInventory()
    {
        return inventory;
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        // Does nothing as everything else interacts with the player
    }

    @Override
    public Collider getCollider()
    {
        float topX = getX();
        float topY = getY() + getHeight();
        float width = getX() - getPrevXPos() + getWidth()/2;
        float height = getPrevYPos() - getY();

        if(getPrevXPos() < topX)
        {
            topX = getPrevXPos();
            width = topX - getX() + getWidth()/2;
        }
        if(getPrevYPos() < topY)
        {
            topY = getPrevYPos() + getHeight();
            height = getY() - topY;
        }
        return new Collider(topX, topY, width, height/5);
    }

    public JumpHandler getJumpHandler()
    {
        return jumpHandler;
    }

    /**
     * Sets the texture for the player and ensures it is facing the correct direction
     * @param texture the image path to set the player to
     */
    private void setDirectionTexture(String texture)
    {
        setTexture(texture);
        getSprite().setOrigin(getSprite().getTexture().getSize().x/2, getSprite().getOrigin().y);
        setPosition(getX(), getY());
        if(!facingRight)
        {
            turnLeft();
        }
    }

    /**
     * Sets the last safe position of the player, used when respawning
     * Sets the player to be safe
     * @param x the x position of the safe place
     * @param y the y position of the safe place
     */
    public void setSafePos(float x, float y)
    {
        lastSafePos[0] = x;
        lastSafePos[1] = y;
        playerSafe = true;
    }

    /**
     * Gets the last X,Y co-ords that were safe
     * @return the safe X,Y position
     */
    public float[] getLastSafePos()
    {
        return lastSafePos;
    }

    /**
     * Sets the player as being safe, done automatically when calling setSafePos()
     * @param safe true if the player is safe, false otherwise
     */
    public void setSafe(boolean safe)
    {
        playerSafe = safe;
    }

    @Override
    public void onTick(Game game)
    {
        setDirectionTexture("/character/walk" + walkTexture + ".png");
        if(walkTexture < 3)
        {
            walkTexture++;
        }
        else
        {
            walkTexture = 0;
        }
        if(walkTexture % 2 == 0)
        {
            if(playerSafe)
            {
                walkingSound.play(SoundType.WALK);
            }
        }
    }

    public boolean facingRight()
    {
        return facingRight;
    }

    public void addPowerup(PowerUp powerup)
    {
        activePowerups.add(powerup);
    }

    public void removePowerup(PowerUp powerup)
    {
        activePowerups.remove(powerup);
    }

    public ArrayList<PowerUp> getActivePowerups()
    {
        return activePowerups;
    }

    @Override
    protected void onLivesChanged(Game game)
    {
        if(game != null)
        {
            game.getHeartsManager().updateLives(game);
            setDown(true);
        }
    }
}
