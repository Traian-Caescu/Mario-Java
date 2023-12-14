package gameobjects.player;

import game.Game;
import gameobjects.traps.movingtraps.Animator;
import timer.TimerListener;

/**
 * A class that handles the player jumping/falling
 */
public class JumpHandler implements TimerListener, Animator
{
    private float jumpGravity;              // The gravity of jumping
    private float fallGravity;              // The gravity of falling
    private float jumpTime;                 // The current time into the jump
    private float fallTime;                 // The current time into the fall
    private boolean forceFall;              // Override the time aspect and force a fall
    private final float START_TIME = 1.5f;  // The time before the jump should go down
    private float maxJumpGravity;           // The maximum jump multiplier
    private float yJump;                    // The height of the previous jump

    /**
     * Creates the jump handler
     */
    public JumpHandler()
    {
        maxJumpGravity = 15f;
        jumpGravity = maxJumpGravity;
        fallGravity = 4f;
        forceFall = false;
        jumpTime = START_TIME;
        yJump = 0;
    }

    /**
     * Sets the jump height, 20 is standard
     * @param gravity the gravity multiplier
     */
    public void setMaxJumpGravity(float gravity)
    {
        maxJumpGravity = gravity;
    }

    /**
     * Resets all the variables to default values (where the player isn't
     * falling or jumping)
     */
    public void reset()
    {
        jumpTime = START_TIME;
        fallTime = START_TIME;
        jumpGravity = maxJumpGravity;
        fallGravity = 4f;
        forceFall = false;
    }

    /**
     * Forces the player to fall
     */
    public void hitHead()
    {
        forceFall = true;
    }

    /**
     * Gets the height of the previous jump
     * @return previous jump height
     */
    public float getLastYJump()
    {
        return yJump;
    }

    @Override
    public void onTick(Game game)
    {
        Character player = game.getCurrentLevel().getCharacter();
        if(!player.isSafe() && !player.canJump() && !forceFall)
        {
            yJump = jumpTime * jumpGravity;
            player.setY(player.getY() - yJump);
            jumpTime -= 0.08f;
        }
        else if(!player.isSafe())
        {
            yJump = fallTime * fallGravity;
            player.setY(player.getY() + yJump);
            fallTime += 0.1f;
        }
        else
        {
            reset();
        }
    }

    @Override
    public void start()
    {
        Game.getTimer().schedule(this, 5f, true, true);
    }
}
