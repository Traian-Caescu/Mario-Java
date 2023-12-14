package menu.elements.difficulty;

/**
 * The difficulty level of the game
 */
public enum DifficultyLevel
{
    /**
     * Easiest difficulty
     * 3 lives
     */
    EASY(3),

    /**
     * Medium difficulty
     * 2 lives
     */
    MEDIUM(2),

    /**
     * Hardest difficulty
     * 1 life only
     */
    HARD(1);

    private int lives;  // The amount of lives a difficulty level provides

    /**
     * Creates a difficulty level with the
     * set number of lives
     * @param lives the number of lives the player should have
     */
    DifficultyLevel(int lives)
    {
        this.lives = lives;
    }

    /**
     * Gets the amount of lives for the difficulty level
     * @return the lives of the player
     */
    public int getLives()
    {
        return lives;
    }
}
