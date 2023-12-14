package timer;

/**
 * Represents an item in a timer schedule
 */
public class TimedItem
{
    private TimerListener listener; // The item that is scheduled
    private float currentDelay;     // The current time before execution
    private float delay;            // The total time before execution
    private boolean repeat;         // True if this should be repeated, false otherwise
    private TimerPriority priority; // True if the task should be allowed while the game is paused

    /**
     * Creates a timed item
     * @param listener the object that will be executed
     * @param delay the delay in milliseconds before it is executed
     * @param repeat true if this task should repeat, false otherwise
     */
    public TimedItem(TimerListener listener, float delay, boolean repeat)
    {
        this.listener = listener;
        this.delay = delay;
        currentDelay = delay;
        this.repeat = repeat;
        priority = TimerPriority.NORMAL;
    }

    /**
     * Sets the priority of this task
     * NORMAL - It will pause while the game is paused
     * HIGH - It will continue while the game is paused
     * @param priority the priority level
     */
    public void setPriority(TimerPriority priority)
    {
        this.priority = priority;
    }

    /**
     * Gets the priority of this task
     * NORMAL - It will pause while the game is paused
     * HIGH - It will continue while the game is paused
     * @return the priority level
     */
    public TimerPriority getPriority()
    {
        return priority;
    }

    /**
     * Sets if the task should be repeated
     * @param repeat true if it should repeat, false otherwise
     */
    public void setRepeat(boolean repeat)
    {
        this.repeat = repeat;
    }

    /**
     * Gets if the task is set to repeat
     * @return true if it will repeat, false otherwise
     */
    public boolean isRepeating()
    {
        return repeat;
    }

    /**
     * Gets the task that will be executed
     * @return the listener task
     */
    public TimerListener getListener()
    {
        return listener;
    }

    /**
     * Gets the delay, in seconds, that must pass before this is executed
     * @return the delay in seconds
     */
    public float getDelay()
    {
        return delay;
    }

    /**
     * Gets the remaining time, in seconds, that must be waited until this is executed
     * @return the remaining delay
     */
    public float getCurrentDelay()
    {
        return currentDelay;
    }

    /**
     * Reduces the time to wait by the given seconds
     * @param milliseconds the amount of time that has passed (in milliseconds)
     * @return true if it should now execute, false otherwise
     */
    public boolean elapseTime(float milliseconds)
    {
        currentDelay -= milliseconds;
        if(currentDelay <= 0)
        {
            currentDelay = delay;
            return true;
        }
        return false;
    }
}
