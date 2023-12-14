package uielements.leaderboard;

import levels.GameFileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for reading leaderboard information into a leaderboard class
 */
public class LeaderboardReader extends GameFileReader
{
    private static LeaderboardReader reader;    // The singular instance of this class

    /**
     * Creates the reader and points it to the /leaderboard/data/ folder
     */
    private LeaderboardReader()
    {
        super("leaderboard/data/");
    }

    /**
     * Gets the singular instance of this reader
     * @return the instance of this class
     */
    public static LeaderboardReader getInstance()
    {
        if(reader != null)
        {
            return reader;
        }
        reader = new LeaderboardReader();
        return reader;
    }

    /**
     * Gets a leaderboard given the index of a level
     * @param index the level index
     * @return the leaderboard
     */
    public Leaderboard getLeaderboardFromIndex(int index)
    {
        File leaderboardFile = getFileFromIndex(index);
        List<String> lines = getFileLines(leaderboardFile);
        Leaderboard leaderboard = new Leaderboard(0,0);
        ArrayList<LeaderboardEntry> entries = new ArrayList<>();
        for (String line : lines)
        {
            if(!line.equals(""))
            {
                List<String> entry = parseLine(line);
                entries.add(new LeaderboardEntry(entry.get(0), entry.get(1)));
            }
        }
        entries.sort(new LeaderboardEntryComparator());
        for (LeaderboardEntry entry : entries)
        {
            leaderboard.addEntry(entry);
        }
        return leaderboard;
    }

    /**
     * Inserts a leaderboard entry into the leaderboard file
     * @param index the index of the level this entry is about
     * @param entry the entry to append
     */
    public void addEntry(int index, LeaderboardEntry entry)
    {
        File leaderboardFile = getFileFromIndex(index);
        try
        {
            FileWriter writer = new FileWriter(leaderboardFile, true);
            writer.append("\n");
            writer.append(entry.getDate());
            writer.append(",");
            String time = entry.getTime();
            if(time.split(" : ")[0].length() == 1)
            {
                time = "0" + time;
            }
            if(time.split(" : ")[1].length() == 1)
            {
                time = time.split(" : ")[0] + " : 0" + time.split(" : ")[1];
            }
            writer.append(time);
            writer.close();
        }
        catch (IOException ex)
        {

        }
    }
}
