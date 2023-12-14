package gameobjects.powerups;

import game.Game;
import gameobjects.player.Character;

public class SpeedBoost extends PowerUp {
    /**
     * Set the speed that the power-up, default speed without boost is 6
     */
    private int speed;
    private static int boostsActive = 0;

    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x     the x position
     * @param y     the y position
     */
    public SpeedBoost(float x, float y, int duration, Character character, int speed) {
        super(x, y, duration, character);
        setTexture("/powerups/speedboost.png");
        this.speed = speed;
        getSprite().setScale(0.1f, 0.1f);
    }

    @Override
    protected void powerUpOn(Character character, Game game) {
        boostsActive++;
        game.setScrollSpeed(speed);

    }

    @Override
    protected void powerUpOff(Character character, Game game) {
        boostsActive--;
        if(boostsActive == 0)
        {
            game.setScrollSpeed(6); //Sets to default value
        }
    }

    @Override
    public SpeedBoost duplicate()
    {
        return new SpeedBoost(getX(), getY(), getDuration(), getCharacter(), speed);
    }
}
