package gameobjects.winrequirements;

import game.Game;

public interface TaskRequirement
{
    /**
     * Gets if the player has completed the task
     * @param game the game with the task
     * @return true if the task is complete, false otherwise
     */
    boolean isComplete(Game game);

    /**
     * Gets the human-readable description of this task
     * @param game the game to get the description for
     * @return the description of the task
     */
    String getDescription(Game game);
}
