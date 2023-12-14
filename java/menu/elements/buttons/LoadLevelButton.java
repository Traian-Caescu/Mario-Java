package menu.elements.buttons;

import game.Game;
import game.GameSettings;
import gameobjects.GameObject;
import levels.level.Level;
import levels.level.LevelReader;
import menu.UIElement;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;

/**
 * Represents a button that will start the game with the current
 * level selected
 */
public class LoadLevelButton extends UIElement
{
    private int index;  // The index of the level to run
    private GameSettings settings;
    private Text displayText;   // Shows the level number
    private boolean unlocked;   // True if it is unlocked, false otherwise
    /**
     * Creates a button that has a location on screen, when clicked it
     * loads the selected level into the game
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     * @param imagePath the image of the button
     * @param levelIndex the index of the level to load from the level reader
     * @param settings the settings to launch the game with
     */
    public LoadLevelButton(float x, float y, String imagePath, int levelIndex, GameSettings settings)
    {
        super(x, y);
        setTexture(imagePath);
        index = levelIndex;
        this.settings = settings;
        displayText = GameObject.createText(((levelIndex % 3) + 1) + "", Color.WHITE);
        unlocked = false;
        setPosOnScreen(getX(), getY());
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {

    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {

    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        getSprite().setPosition(x, y);
        if(displayText != null)
        {
            displayText.setPosition(getX() + getSprite().getGlobalBounds().width/2, getY() + getSprite().getGlobalBounds().height/2);
        }
        if(index <= LevelReader.getInstance().getCurrentIndex() && !unlocked)
        {
            unlocked = true;
            setTexture("/ui/world" + (index/3 + 1) + "unlocked.png");
        }
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        Level foundLevel = LevelReader.getInstance().getLevelFromIndex(index);
        if(!foundLevel.isLocked() || unlocked)
        {
            Game invasionGame = new Game(settings);
            invasionGame.setLevel(foundLevel);
            menu.close();
            invasionGame.run();
        }
    }

    public void draw(RenderTarget target, RenderStates states)
    {
        getSprite().draw(target, states);
        displayText.draw(target, states);
    }
}
