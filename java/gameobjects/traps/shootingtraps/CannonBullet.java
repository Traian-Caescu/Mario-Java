package gameobjects.traps.shootingtraps;

import audio.SoundPlayer;
import audio.SoundType;
import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;
import gameobjects.traps.StaticTrap;
import gameobjects.traps.movingtraps.Animator;
import timer.TimerListener;

/**
 * A class of game object that is fired from a cannon
 */
public class CannonBullet extends StaticTrap implements TimerListener, Animator
{
    private Cannon parentShooter;   // The cannon that shot this
    private float dx;               // The x velocity
    private float dy;               // The y velocity
    private boolean hitPlayer;      // True if hit player, false otherwise
    private SoundPlayer hurtSound;  // The sound player when it hits the player

    /**
     * Creates a new bullet that is going at a certain speed
     * @param x The x position
     * @param y the y position
     * @param damage the damage given to something that hits this
     * @param shooter the cannon that shot this
     */
    public CannonBullet(float x, float y, int damage, Cannon shooter)
    {
        super(x, y, "/traps/cannon/bullet.png", damage);
        getSprite().setScale(0.3f, 0.3f);
        setPosition(x, y);
        parentShooter = shooter;
        dx = 0;
        dy = 0;
        hitPlayer = false;
        hurtSound = new SoundPlayer();
    }

    /**
     * Essentially like clone() but it's a deep copy (except cannon)
     * @return a copy of this class
     */
    public CannonBullet duplicate()
    {
        return new CannonBullet(getX(), getY(), getDamage(), parentShooter);
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if(!hitPlayer)
        {
            if(interactor instanceof Character)
            {
                Character player = (Character) interactor;
                if(getY() > player.getY() + 20)
                {
                    if(!player.isInvincible())
                    {
                        hitPlayer = true;
                        player.addLives(-1, game);
                        setPosition(getX() + dx, getY() + dy);
                        hurtSound.play(SoundType.HURTFROMBULLET);
                        game.pauseOnDeath(this);
                    }
                }
            }
        }
    }

    public void stop()
    {
        Game.getTimer().remove(this);
    }

    /**
     * Sets the X velocity
     * @param dx the x velocity
     */
    public void setDx(float dx)
    {
        this.dx = dx;
    }

    /**
     * Sets the Y velocity
     * @param dy the y velocity
     */
    public void setDy(float dy)
    {
        this.dy = dy;
    }

    @Override
    public void onTick(Game game)
    {
        setPosition(getX() + dx, getY() + dy);

        if(!isVisible(game))
        {
            game.removeDrawable(this);
            stop();
        }
    }

    public boolean isVisible(Game game)
    {
        Character player = game.getCurrentLevel().getCharacter();
        float screenHeight = game.getWindow().getFrame().getSize().y/2;
        float screenWidth = game.getWindow().getFrame().getSize().x/2;
        float playerBottomView = screenHeight + player.getY();
        float playerTopView = player.getY() - screenHeight;
        float playerRight = player.getX() + screenWidth;
        float playerLeft = player.getX() - screenWidth;
        return !(getY() > playerBottomView || getY() < playerTopView || getX() > playerRight || getX() < playerLeft);
    }

    @Override
    public boolean isColliding(GameObject object)
    {
        return getSprite().getGlobalBounds().intersection(object.getSprite().getGlobalBounds()) != null;
    }

    @Override
    public void start()
    {
        Game.getTimer().schedule(this, 10f, true, true);
    }
}
