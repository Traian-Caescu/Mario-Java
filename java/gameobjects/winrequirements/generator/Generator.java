package gameobjects.winrequirements.generator;

import audio.SoundPlayer;
import audio.SoundType;
import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import gameobjects.winrequirements.Cog;
import org.jsfml.graphics.Color;

/**
 * An object in the game that requires cogs to repair
 * These must be fixed before the game can end
 */
public class Generator extends GameObject
{
    private int cogsRequired;   // The number of cogs required to repair this generator
    private int maxCogs;
    private static SoundPlayer deactivatedNoise;
    private boolean completed;

    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     * @param cogs the amount of cogs required to fix this (0 to 4)
     */
    public Generator(float x, float y, int cogs)
    {
        super(x, y);
        cogsRequired = cogs;
        maxCogs = cogsRequired;
        setCogsImage();
        deactivatedNoise = new SoundPlayer();
        completed = false;
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if(interactor instanceof Character)
        {
            Character player = (Character) interactor;
            int playerCogs = player.getInventory().getItemCount(new Cog(-1, -1));
            if(playerCogs > 0 && !completed)
            {
                if(playerCogs > cogsRequired)
                {
                    playerCogs -= cogsRequired;
                    cogsRequired = 0;
                }
                else if(playerCogs < cogsRequired)
                {
                    cogsRequired -= playerCogs;
                    playerCogs = 0;
                }
                else
                {
                    playerCogs = 0;
                    cogsRequired = 0;
                }
                setCogsImage();
                player.getInventory().setAmount(new Cog(-1, -1), playerCogs);
                if(cogsRequired == 0)
                {
                    deactivatedNoise.play(SoundType.GENERATOR_DEACTIVATED);
                    completed = true;
                }
            }
        }
    }

    /**
     * Sets the appropriate texture for the object given the number of cogs required
     */
    private void setCogsImage()
    {
        if(cogsRequired >= 0 && cogsRequired <= 4)
        {
            setTexture("/generators/" + cogsRequired + "cogs.png");
            Color displayColour = Color.BLACK;
            String displayText;
            if(cogsRequired == 1)
            {
                displayText = cogsRequired + " COG REQUIRED";
            }
            else
            {
                displayText = cogsRequired + " COGS REQUIRED";
            }
            if(cogsRequired == 0)
            {
                displayColour = Color.GREEN;
                displayText = "GENERATOR DISABLED";
            }
            setDisplayText(displayText, displayColour);
        }
        else if(cogsRequired > 4)
        {
            setTexture("/generators/4cogs.png");
        }
        else
        {
            setTexture("/generators/0cogs.png");
        }
        getSprite().setScale(2f, 2f);
    }

    /**
     * Gets the amount of cogs required to disable the generator
     * @return the cogs required
     */
    public int getCogsRequired()
    {
        return cogsRequired;
    }

    /**
     * Resets the progress
     */
    public void reset()
    {
        cogsRequired = maxCogs;
        setCogsImage();
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }
}
