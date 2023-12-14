package uielements.leaderboard;

import java.util.Comparator;

/**
 * Compares two entries in a leaderboard and sorts them by shortest time to longest time
 */
public class LeaderboardEntryComparator implements Comparator<LeaderboardEntry>
{
    @Override
    public int compare(LeaderboardEntry o1, LeaderboardEntry o2)
    {
        String[] timeSplit1 = o1.getTime().split(" : ");
        String[] timeSplit2 = o2.getTime().split(" : ");
        int[] time1 = new int[2];
        time1[0] = Integer.parseInt(timeSplit1[0]);
        time1[1] = Integer.parseInt(timeSplit1[1]);
        int[] time2 = new int[2];
        time2[0] = Integer.parseInt(timeSplit2[0]);
        time2[1] = Integer.parseInt(timeSplit2[1]);
        if(time1[0] < time2[0])
        {
            return -1;
        }
        else if(time1[0] > time2[0])
        {
            return 1;
        }
        else
        {
            if(time1[1] < time2[1])
            {
                return -1;
            }
            else if(time1[1] > time2[1])
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
}
