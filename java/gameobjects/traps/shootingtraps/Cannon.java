package gameobjects.traps.shootingtraps;

import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;
import gameobjects.traps.StaticTrap;
import gameobjects.traps.movingtraps.Animator;
import timer.TimerListener;

/**
 * Represents something which shoots bullets at players at a consistent rate
 */
public class Cannon extends StaticTrap implements TimerListener, Animator
{
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x      the x position
     * @param y      the y position
     * @param damage the damage the cannon gives
     */
    public Cannon(float x, float y, int damage)
    {
        super(x, y, "/traps/cannon/cannon.png", damage);
        getSprite().setScale(0.2f, 0.2f);
        setPosition(x, y);
    }

    /**
     * Gets the cannon to start shooting the player
     */
    public void start()
    {
        Game.getTimer().schedule(this, 3000, true, true);
    }

    @Override
    public void onTick(Game game)
    {
        fireStraight(game);
    }

    private void fireAngled(Game game)
    {
        int angleToPlayer = getAngleToPos(game.getCurrentLevel().getCharacter());
        float xScale = getSprite().getScale().x;
        if((xScale > 0 && angleToPlayer >= 45 && angleToPlayer <= 135) || (xScale < 0 && angleToPlayer <= 315 && angleToPlayer >= 225))
        {
            CannonBullet bullet = new CannonBullet(getX() + getWidth()/2, getY() + getHeight()/2, 1, this);
            Character character = game.getCurrentLevel().getCharacter();
            bullet.setRotation(bullet.getAngleToPos(character));
            float yDiff = character.getY() - getY();
            float xDiff = character.getX() - getX();
            bullet.setDx(xDiff/12);
            bullet.setDy(yDiff/12);
            game.addDrawable(bullet);
        }
    }

    private void fireStraight(Game game)
    {
        CannonBullet bullet = new CannonBullet(getX() + getWidth()/2, getY() + getHeight()/2, 1, this);
        if(getSprite().getRotation() == 0)
        {
            bullet.setRotation(90);
            bullet.setDx(8);
        }
        else if(getSprite().getRotation() <= 90)
        {
            bullet.setRotation(180);
            bullet.setDy(8);
        }
        else if(getSprite().getRotation() <= 180)
        {
            bullet.setRotation(270);
            bullet.setDx(-8);
        }
        else if(getSprite().getRotation() <= 270)
        {
            bullet.setRotation(0);
            bullet.setDy(-8);
        }
        if(getSprite().getRotation() == 0)
        {
            bullet.setPosition(getX() + getWidth()/2, getY() + getHeight()/2);
        }
        else if(getSprite().getRotation() == 180)
        {
            bullet.setPosition(getX() + getWidth()/2, getY() + getHeight());
        }
        if(bullet.isVisible(game))
        {
            game.addDrawable(bullet);
            bullet.start();
        }
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        // Do nothing
    }
}
