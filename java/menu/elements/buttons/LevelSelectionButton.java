package menu.elements.buttons;

import game.GameSettings;
import gameobjects.GameObject;
import levels.level.LevelReader;
import menu.menus.ButtonMenu;
import menu.menus.MenuLayout;
import menu.menus.PagedMenu;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;

/**
 * A button that opens the level selection menu
 */
public class LevelSelectionButton extends Button
{
    private LevelReader reader;
    private GameSettings settings;
    /**
     * Creates a button that has a location on screen
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     * @param imagePath the image of the button
     */
    public LevelSelectionButton(float x, float y, String imagePath, GameSettings settings)
    {
        super(x, y, imagePath);
        reader = LevelReader.getInstance();
        this.settings = settings;
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        MenuLayout selectionLayout = new MenuLayout("Level Selection","/ui/blurBackground.png");
        Text title = GameObject.createText("Level Selection", Color.BLACK);
        float yPos = 50;
        float xPos = 250;
        int levelSetNumber = 0;
        for (int i = 0; i < reader.getLevelCount(); i++)
        {
            if(i % 3 == 0)
            {
                yPos += 95;
                xPos = 250 - 85;
                levelSetNumber++;
                Text worldDesc = GameObject.createText("World " + levelSetNumber, Color.BLACK);
                worldDesc.setOrigin(0,0);
                worldDesc.setPosition(xPos - 80, yPos + 15);
                selectionLayout.addDrawable(worldDesc);
            }
            xPos += 85;
            LoadLevelButton newButton;
            if(i != 0)
            {
                newButton = new LoadLevelButton(0,0, "/ui/world" + levelSetNumber + "locked.png", i, settings);
            }
            else
            {
                newButton = new LoadLevelButton(0,0, "/ui/world" + levelSetNumber + "unlocked.png", i, settings);
            }
            newButton.setPosition(xPos, yPos);
            selectionLayout.addDrawable(newButton);
        }
        title.setCharacterSize(48);
        title.setPosition(300, 40);
        selectionLayout.addDrawable(title);
        PagedMenu pagedMenu = new PagedMenu();
        pagedMenu.addPage(selectionLayout);
        menu.setMenu(pagedMenu);
    }
}
