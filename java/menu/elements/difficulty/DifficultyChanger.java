package menu.elements.difficulty;

import menu.UIElement;

/**
 * A UI element that handles changing the difficulty of the game
 */
public abstract class DifficultyChanger extends UIElement
{
    private DifficultySelector selector;    // The game difficulty

    /**
     * Creates a UI Element for selecting difficulty
     *
     * @param x the x co-ord
     * @param y the y co-ord
     * @param selector the difficulty selector
     */
    public DifficultyChanger(float x, float y, DifficultySelector selector)
    {
        super(x, y);
        this.selector = selector;
    }

    /**
     * Gets the difficulty selector
     * @return the difficulty selector
     */
    public DifficultySelector getSelector()
    {
        return selector;
    }
}
