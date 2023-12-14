package gameobjects.powerups;
import audio.SoundPlayer;
import audio.SoundType;
import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import gameobjects.player.Character;
import timer.TimerListener;

public abstract class PowerUp extends GameObject implements TimerListener
{
    private int duration;
    private Character character;
    private static SoundPlayer activationNoise;
    /**
     * Abstract class for different power-up types
     *
     * @param x the x position
     * @param y the y position
     * @param duration duration of time power up will work for
     * @param character instance of character
     */
    public PowerUp(float x, float y, int duration, Character character)
    {
        super(x, y);
        setPosition(x, y);
        this.duration = duration;
        this.character = character;
        activationNoise = new SoundPlayer();
        activationNoise.setVolume(50);
    }
    /**
     * @return Duration of power up
     */
    public int getDuration() {
        return duration;
    }

    public void startPowerUp(Character character, Game game)
    {
        game.removeDrawable(this);
        character.addPowerup(this);
        game.addPowerupToDisplay(this);
        powerUpOn(character, game);
    }

    public void disablePowerUp(Character character, Game game)
    {
        game.addDrawable(this);
        character.removePowerup(this);
        powerUpOff(character, game);
    }

    public Character getCharacter()
    {
        return character;
    }

    protected abstract void powerUpOn(Character character, Game game);

    protected abstract void powerUpOff(Character character, Game game);

    /**
     * Creates a copy of this powerup
     * @return the new powerup
     */
    public abstract PowerUp duplicate();

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if (interactor instanceof Character)
        {
            activationNoise.play(SoundType.POWERUP_ACTIVATED);
            Game.getTimer().schedule(this, duration, false, false);
            startPowerUp(character, game);
        }
    }

    @Override
    public Collider getCollider()
    {
        return null;
    }

    @Override
    public void onTick(Game game)
    {
        disablePowerUp(character, game);
        Game.getTimer().remove(this);
    }
}
