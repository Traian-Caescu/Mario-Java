package gameobjects.player;

import game.Game;
import gameobjects.traps.Enemy;
import timer.TimedItem;
import timer.TimerListener;
import timer.TimerPriority;

/**
 * Pauses the game for 1 second
 */
public class DeathDelayer implements TimerListener
{
    private Enemy enemyToRemove;    // Enemy to remove after death, null if nothing should be removed

    /**
     * Creates a 1 second delay
     * @param game the game to pause
     * @param removeOnDeath the enemy to remove, null if nothing should be removed
     */
    public DeathDelayer(Game game, Enemy removeOnDeath)
    {
        game.setPaused(true);
        enemyToRemove = removeOnDeath;
        TimedItem item = Game.getTimer().schedule(this, 1000f, false, false);
        item.setPriority(TimerPriority.HIGH);
    }

    @Override
    public void onTick(Game game)
    {
        if(enemyToRemove != null)
        {
            game.addDrawable(enemyToRemove);
        }
        Character player = game.getCurrentLevel().getCharacter();
        player.setPosition(player.getLastSafePos()[0], player.getLastSafePos()[1]);   // just move the player to literally anywhere that won't have a trap there
        game.respawn();
        Game.getTimer().remove(this);
        player.setDown(false);
        game.setPaused(false);
    }
}
