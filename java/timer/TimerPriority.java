package timer;

/**
 * An enum to set the priority of a timer item
 */
public enum TimerPriority
{
    /**
     * The timed item will not pause while the game is paused
     */
    HIGH,

    /**
     * The timed item will pause while the game is paused
     */
    NORMAL
}
