package timer;

import game.Game;
import org.jsfml.system.Clock;
import java.util.ArrayList;

/**
 * A timer for events that need to happen repeatedly or after a time
 */
public class GameTimer
{
    private static Clock timer;                     // The Clock this timer works from
    private Game game;                              // The game in which this timer operates under
    private ArrayList<TimedItem> items;             // Items that will be called repeatedly
    private ArrayList<TimedItem> completedItems;    // Items to be removed from the timer
    private static GameTimer gameTimer;             // A single instance of this class to be used by all classes (no repeated instantiation)

    /**
     * Returns the timer currently active or creates a new one if it doesn't exist yet
     * @param game the game the timer passes to functions
     * @return the GameTimer that is currently active
     */
    public static GameTimer getInstance(Game game)
    {
        if(gameTimer != null)
        {
            gameTimer.setGame(game);
            return gameTimer;
        }
        new GameTimer(game);
        return gameTimer;
    }

    /**
     * Instnatiates the static timer to be used by all classes
     * @param game the game that will be passed to functions
     */
    private GameTimer(Game game)
    {
        this.game = game;
        timer = new Clock();
        items = new ArrayList<>();
        completedItems = new ArrayList<>();
        gameTimer = this;
    }

    /**
     * Schedules a timed event
     * @param listener the listener that has the event to be fired
     * @param delay the delay, in milliseconds, between calls
     * @param instantStart true if it should fire immediately, false if the delay should happen first
     * @param repeat true if this should repeat, false otherwise
     * @return the item in the schedule
     */
    public TimedItem schedule(TimerListener listener, float delay, boolean instantStart, boolean repeat)
    {
        TimedItem item = new TimedItem(listener, delay, repeat);
        items.add(item);
        if(instantStart)
        {
            listener.onTick(game);
        }
        return item;
    }

    /**
     * Set the listener's event to repeat/not repeat
     * @param listener the event to change
     * @param repeat true if it should repeat, false otherwise
     */
    public void setRepeat(TimerListener listener, boolean repeat)
    {
        for(TimedItem item : items)
        {
            if(item.getListener().equals(listener))
            {
                item.setRepeat(repeat);
            }
        }
    }

    /**
     * Sets the game this timer is active on
     * @param newGame the game
     */
    public void setGame(Game newGame)
    {
        this.game = newGame;
    }

    /**
     * Should be called frequently
     * Calculates how long each event has had and whether it should fire or not
     */
    public void elapseTime()
    {
        float elapsedTime = timer.restart().asMilliseconds();
        for (int i = 0; i < items.size(); i++)
        {
            if(!game.isPaused() || items.get(i).getPriority() == TimerPriority.HIGH)
            {
                if(items.get(i).elapseTime(elapsedTime))
                {
                    items.get(i).getListener().onTick(game);
                    if(!items.get(i).isRepeating())
                    {
                        completedItems.add(items.get(i));
                    }
                }
            }
        }
        items.removeAll(completedItems);
    }

    /**
     * Sets the event to be removed during the next check
     * @param listener the event to remove
     */
    public void remove(TimerListener listener)
    {
        TimedItem foundItem = null;
       for (TimedItem item : items)
       {
           if(item.getListener().equals(listener))
           {
                foundItem = item;
           }
       }
       completedItems.add(foundItem);
    }

    /**
     * Gets the items currently in the timer queue
     * @return the active timer items
     */
    public ArrayList<TimedItem>  getItems()
    {
        return items;
    }

    /**
     * Removes all items from timer queue
     */
    public void clear()
    {
        completedItems.addAll(items);
    }
}
