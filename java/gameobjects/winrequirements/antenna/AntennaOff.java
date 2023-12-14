package gameobjects.winrequirements.antenna;

import game.Game;
import gameobjects.GameObject;

/**
 * A game object that disables an antenna when collided with
 */
public class AntennaOff extends AntennaButton
{
    /**
     * Creates a new button at coords (X,Y) on screen
     *
     * @param x       the x position
     * @param y       the y position
     * @param antenna the antenna to disable
     */
    public AntennaOff(float x, float y, Antenna antenna)
    {
        super(x, y, antenna);
        setTexture("/antenna/buttonOff.png");
        getSprite().setScale(0.2f, 0.2f);
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        getAntenna().deactivate(game);
    }
}
