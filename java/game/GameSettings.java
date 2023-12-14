package game;

import menu.elements.difficulty.DifficultyLevel;

/**
 * Settings that change how the game is ran
 */
public class GameSettings
{
    private DifficultyLevel difficulty; // Difficulty of the game
    private int volume;                 // Volume of the background music
    private boolean showThanks;         // True if the "Thank you" screen should be show

    /**
     * Creates the game settings
     * @param level the difficulty level of the game
     * @param volume the volume of the background music
     */
    public GameSettings(DifficultyLevel level, int volume)
    {
        difficulty = level;
        this.volume = volume;
        showThanks = false;
    }

    /**
     * Set if the thank you screen should be shown on launch
     * @param thanks true if it should, false otherwise
     */
    public void setShowThanks(boolean thanks)
    {
        showThanks = thanks;
    }

    /**
     * Gets if the thank you screen should be shown
     * @return true if it should, false otherwise
     */
    public boolean showThanks()
    {
        return showThanks;
    }

    /**
     * Get the difficulty of the game
     * @return the difficulty
     */
    public DifficultyLevel getDifficulty()
    {
        return difficulty;
    }

    /**
     * Set the difficulty of the game
     * @param difficulty the difficulty
     */
    public void setDifficulty(DifficultyLevel difficulty)
    {
        this.difficulty = difficulty;
    }

    /**
     * Get the volume of the background music
     * @return the volume
     */
    public int getVolume()
    {
        return volume;
    }

    /**
     * Set the volume of the background music
     * @param volume the volume
     */
    public void setVolume(int volume)
    {
        this.volume = volume;
    }
}
