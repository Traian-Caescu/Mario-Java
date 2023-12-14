package gameobjects.powerups;

import game.Game;
import gameobjects.player.Character;

public class Invincibility extends PowerUp {

    private static int boostsActive = 0;
    /**
     * Creates new invincibility power-up at position x,y
     *
     * @param x     the x position
     * @param y     the y position
     * @param duration How long the invicibiltiy with be active for
     * @param character instace of the character
     */
    public Invincibility(float x, float y, int duration, Character character) {
        super(x, y, duration, character);
        setTexture("/powerups/invincibility.png");
        getSprite().setScale(0.12f, 0.12f);

    }

    @Override
    protected void powerUpOn(Character character, Game game) {
        boostsActive++;
        character.setInvincible(true);

    }

    @Override
    protected void powerUpOff(Character character, Game game) {
        boostsActive--;
        if(boostsActive == 0)
        {
            character.setInvincible(false);
        }
    }

    @Override
    public Invincibility duplicate()
    {
        return new Invincibility(getX(), getY(), getDuration(), getCharacter());
    }
}
