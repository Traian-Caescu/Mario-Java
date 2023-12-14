package menu.elements.difficulty;

import menu.menus.ButtonMenu;

/**
 * A button for reducing the difficulty
 */
public class DifficultyDown extends DifficultyChanger
{
    /**
     * Creates a button that handles reducing the difficulty
     *
     * @param x the x co-ord
     * @param y the y co-ord
     * @param selector the difficulty selector
     */
    public DifficultyDown(float x, float y, DifficultySelector selector)
    {
        super(x, y, selector);
        setTexture("/ui/decrement.png");
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        setTexture("/ui/decrementHover.png");
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        setTexture("/ui/decrement.png");
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        getSelector().decreaseDifficulty();
    }
}
