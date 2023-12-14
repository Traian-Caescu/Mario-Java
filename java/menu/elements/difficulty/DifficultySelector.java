package menu.elements.difficulty;

import game.GameSettings;
import gameobjects.GameObject;
import menu.UIElement;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;

public class DifficultySelector extends UIElement
{
    private DifficultyLevel difficulty;
    private Text difficultyText;
    private GameSettings settings;

    /**
     * Creates a UI Element that has a location on screen
     *
     * @param x the x co-ord
     * @param y the y co-ord
     * @param colour the colour of the text
     * @param settings the settings to change
     */
    public DifficultySelector(float x, float y, Color colour, GameSettings settings)
    {
        super(x, y);
        difficulty = settings.getDifficulty();
        difficultyText = GameObject.createText(difficulty.toString(), colour);
        difficultyText.setOrigin(0,0);
        difficultyText.setPosition(x, y);
        this.settings = settings;
    }

    /**
     * Increases the difficulty or rotates it back
     * round to the start if the max is hit
     * @return the new difficulty
     */
    public DifficultyLevel increaseDifficulty()
    {
        switch (difficulty)
        {
            case EASY:
                difficulty = DifficultyLevel.MEDIUM;
                break;
            case MEDIUM:
                difficulty = DifficultyLevel.HARD;
                break;
            case HARD:
                difficulty = DifficultyLevel.EASY;
        }
        difficultyText.setString(difficulty.toString());
        settings.setDifficulty(difficulty);
        return difficulty;
    }

    /**
     * Decreases the difficulty or rotates it back
     * round to the end if it beginning was hit
     * @return the new difficulty
     */
    public DifficultyLevel decreaseDifficulty()
    {
        switch (difficulty)
        {
            case EASY:
                difficulty = DifficultyLevel.HARD;
                break;
            case MEDIUM:
                difficulty = DifficultyLevel.EASY;
                break;
            case HARD:
                difficulty = DifficultyLevel.MEDIUM;
        }
        difficultyText.setString(difficulty.toString());
        settings.setDifficulty(difficulty);
        return difficulty;
    }

    /**
     * Sets the difficulty
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(DifficultyLevel difficulty)
    {
        difficultyText.setString(difficulty.toString());
        this.difficulty = difficulty;
        settings.setDifficulty(difficulty);
    }

    /**
     * Gets the current difficulty
     * @return the current difficulty
     */
    public DifficultyLevel getDifficulty()
    {
        return difficulty;
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        difficultyText.setPosition(x, y);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        difficultyText.draw(renderTarget, renderStates);
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        // DOES NOTHING
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        // DOES NOTHING
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        // DOES NOTHING
    }
}
