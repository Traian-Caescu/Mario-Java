package gameobjects.player.hearts;

import game.Game;

import java.util.ArrayList;

/**
 * Manages the creation and removal of hearts when the player takes damage
 */
public class HeartsManager
{
    private ArrayList<Heart> activeHearts;              // Hearts that are currently in view
    private float width = new Heart(-1, -1).getWidth(); // Used to ensure hearts are separated correctly
    private int prevLives;

    /**
     * Creates a heart manager
     */
    public HeartsManager(int maxLives)
    {
        activeHearts = new ArrayList<>();
        prevLives = 0;
        createLives(maxLives);
    }

    private void createLives(int maxLives)
    {
        for (int i = 0; i < maxLives; i++)
        {
            Heart newHeart;
            if(activeHearts.size() == 0)
            {
                newHeart = new Heart(10, 10);
            }
            else
            {
                float prevXPos = activeHearts.get(activeHearts.size() - 1).getX();
                newHeart = new Heart(prevXPos + width + 10, 10);
            }
            activeHearts.add(newHeart);
        }
    }

    /**
     * Sets the amount of hearts based on player's hearts
     */
    public void updateLives(Game game)
    {
        int currentLives = game.getCurrentLevel().getCharacter().getLives();
        if(currentLives != prevLives)
        {
            if(currentLives < prevLives)
            {
                int lastIndex = prevLives - 1;
                for (int i = 0; i < prevLives - currentLives; i++)
                {
                    game.removeDrawable(activeHearts.get(lastIndex - i));
                }
            }
            else
            {
                for (int i = 0; i < currentLives - prevLives; i++)
                {
                    game.addDrawable(activeHearts.get(prevLives + i));
                }
            }
            prevLives = currentLives;
        }
    }

    /**
     * Sets the top left position of the first heart (must be updated regularly
     * to keep hearts in the correct position as the player moves)
     * @param x the left position
     * @param y the top position
     */
    public void setPosition(float x, float y)
    {
        for (Heart heart : activeHearts)
        {
            heart.setPosition(x, y);
            x += width + 10;
        }
    }
}
