package gameobjects.powerups;

import game.Game;
import gameobjects.player.Character;

public class JumpBoost extends PowerUp {

    private static int boostsActive = 0;
    /**
     * Creates a new JumpBoost power-up at position x,y
     *
     * @param x             the x position
     * @param y             the y position
     * @param duration      length of how long the jump boost will be active for
     * @param character instance of the character
     */
    public JumpBoost(float x, float y, int duration, Character character)
    {
        super(x, y, duration, character);
        setTexture("/powerups/jumpboost.png");
        getSprite().setScale(0.07f, 0.07f);

    }

    @Override
    protected void powerUpOn(Character character, Game game) {
        boostsActive++;
        character.getJumpHandler().setMaxJumpGravity(40);
    }

    @Override
    protected void powerUpOff(Character character, Game game) {
        boostsActive--;
        if(boostsActive == 0)
        {
            character.getJumpHandler().setMaxJumpGravity(20);
        }
    }

    @Override
    public JumpBoost duplicate()
    {
        return new JumpBoost(getX(), getY(), getDuration(), getCharacter());
    }
}
