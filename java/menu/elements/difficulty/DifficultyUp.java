package menu.elements.difficulty;

import menu.menus.ButtonMenu;

/**
 * Button that increases the difficulty
 */
public class DifficultyUp extends DifficultyChanger
{
    /**
     * Creates a button that increases the difficulty of the game
     *
     * @param x the x co-ord
     * @param y the y co-ord
     * @param selector the difficulty selector
     */
    public DifficultyUp(float x, float y, DifficultySelector selector)
    {
        super(x, y, selector);
        setTexture("/ui/increment.png");
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        setTexture("/ui/incrementHover.png");
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        setTexture("/ui/increment.png");
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        getSelector().increaseDifficulty();
    }
}
