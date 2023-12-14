package levels;

import gameobjects.Checkpoint;
import java.util.ArrayList;

/**
 * A class to wrap all the checkpoints in the game to make it easier to work with
 */
public class CheckpointCollection
{
    private ArrayList<Checkpoint> checkpoints;  // The list of checkpoints in the level
    private Checkpoint mostRecentCheckpoint;    // The most recently activated checkpoint

    /**
     * Creates a checkpoint collection with the set of passed checkpoints
     * @param points the checkpoints to add
     */
    public CheckpointCollection(ArrayList<Checkpoint> points)
    {
        this.checkpoints = points;
        mostRecentCheckpoint = null;
    }

    /**
     * Creates an empty list of checkpoints
     */
    public CheckpointCollection()
    {
        checkpoints = new ArrayList<>();
        mostRecentCheckpoint = null;
    }

    /**
     * Adds a checkpoint to the list
     * @param newPoint the new checkpoint
     */
    public void addCheckpoint(Checkpoint newPoint)
    {
        checkpoints.add(newPoint);
    }

    /**
     * Adds multiple checkpoints to the list
     * @param points the list of checkpoints
     */
    public void addCheckpoints(ArrayList<Checkpoint> points)
    {
        checkpoints.addAll(points);
    }

    /**
     * Gets the most recent checkpoint activated
     * @return the checkpoint most recently activated or null if one hasn't been
     */
    public Checkpoint getMostRecentCheckpoint()
    {
        return mostRecentCheckpoint;
    }

    /**
     * Gets the list of checkpoints in this collection
     * @return the list of checkpoints
     */
    public ArrayList<Checkpoint> getCheckpoints()
    {
        return checkpoints;
    }

    /**
     * Activates one checkpoint and disables all the others
     * @param point the checkpoint to activate
     */
    public void activateCheckpoint(Checkpoint point)
    {
        if(checkpoints.contains(point))
        {
            for (Checkpoint checkpoint : checkpoints)
            {
                if(checkpoint.equals(point))
                {
                    mostRecentCheckpoint = checkpoint;
                }
                else
                {
                    checkpoint.silentSetActive(false);
                }
            }
        }
    }
}
