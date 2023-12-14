package gameobjects.traps;

import audio.SoundPlayer;
import audio.SoundType;
import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;

/**
 * Represents a class that is stationary
 */
public class StaticTrap extends Enemy
{
    private SoundPlayer hurtSound;
    /**
     * Creates a static trap
     * @param x the x position
     * @param y the y position
     * @param image the image path of the trap
     * @param damage the damage it gives
     */
    public StaticTrap(float x, float y, String image,  int damage)
    {
        super(x, y, image, damage);
        setPosition(x, y);
        hurtSound = new SoundPlayer();
        hurtSound.setVolume(5);
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if(interactor instanceof Character)
        {
            Character player = (Character) interactor;
            if(!player.isDown())
            {
                if(!player.isInvincible())
                {
                    if(player.getX() + player.getWidth()/2 - 10 >= getX() && player.getX() <= getX() + getWidth())
                    {
                        hurtSound.play(SoundType.HURTFROMTRAP);
                        player.addLives(-getDamage(), game);
                        game.pauseOnDeath(null);
                    }
                }
            }
        }
    }
}
