package timer;

/**
 * Measures the time taken to complete a level
 */
public class LeaderboardTimer
{
    private long startTime;         // The time the clock started
    private long pauseTime;         // The time when the game is paused
    private long totalMilliseconds; // Total milliseconds taken on the level

    /**
     * Creates a timer
     */
    public LeaderboardTimer()
    {
        totalMilliseconds = 0;
    }

    /**
     * Starts the timer
     */
    public void startTimer()
    {
        startTime = System.currentTimeMillis();
    }

    /**
     * Pauses the timer
     */
    public void pauseTimer()
    {
        pauseTime = System.currentTimeMillis();
        totalMilliseconds += pauseTime - startTime;
    }

    /**
     * Sets the timer back to zero
     */
    public void reset()
    {
        totalMilliseconds = 0;
    }

    /**
     * Gets the time taken
     * @return the total milliseconds on a level excluding pauses
     */
    public long getTotalMilliseconds()
    {
        return totalMilliseconds;
    }
}
