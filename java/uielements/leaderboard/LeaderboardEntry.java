package uielements.leaderboard;

/**
 * An object that represents a time in a leaderboard
 */
public class LeaderboardEntry
{
    private String date;    // The date string of when the entry was made
    private String time;    // The time it took

    /**
     * Creates a leaderboard entry
     * @param date the date it was made
     * @param time the time taken
     */
    public LeaderboardEntry(String date, String time)
    {
        this.date = date;
        this.time = time;
    }

    /**
     * Gets the date the entry was made
     * @return the date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Gets the time taken on the level
     * @return the time taken
     */
    public String getTime()
    {
        return time;
    }
}
