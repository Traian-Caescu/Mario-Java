package gameobjects.winrequirements.antenna;

import gameobjects.GameObject;
import gameobjects.interfaces.Collider;

public abstract class AntennaButton extends GameObject
{
    private Antenna antenna;

    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public AntennaButton(float x, float y, Antenna antenna)
    {
        super(x, y);
        this.antenna = antenna;
    }

    public Antenna getAntenna()
    {
        return antenna;
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }
}
