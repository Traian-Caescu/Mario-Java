package gameobjects.winrequirements.coin;

import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;
import gameobjects.winrequirements.TaskRequirement;
import levels.level.Level;

/**
 * Represents a task where the player has to collect the coins on the map
 */
public class CoinRequirement implements TaskRequirement
{
    @Override
    public boolean isComplete(Game game)
    {
        Level currentLevel = game.getCurrentLevel();
        return (getPlayerCoins(currentLevel.getCharacter()) == getCoinsInLevel(currentLevel));
    }

    /**
     * Gets the total coins in a level
     * @param level the level to get the coins from
     * @return the number of coins in the level
     */
    public int getCoinsInLevel(Level level)
    {
        int coinsRequired = 0;
        for (GameObject object : level.getMiscObjects())
        {
            if(object instanceof Coin)
            {
                coinsRequired++;
            }
        }
        return coinsRequired;
    }

    /**
     * Gets the amount of coins in the player's inventory
     * @param player the player to get the coins for
     * @return the amount of coins the player has
     */
    public int getPlayerCoins(Character player)
    {
        return player.getInventory().getItemCount(new Coin(-1, -1));
    }

    @Override
    public String getDescription(Game game)
    {
        Level currentLevel = game.getCurrentLevel();
        int coinsRemaining = getCoinsInLevel(currentLevel) - getPlayerCoins(currentLevel.getCharacter());
        return "Coins remaining: " + coinsRemaining;
    }
}
