package menu.elements.buttons;

import game.Game;
import game.GameSettings;
import levels.level.LevelReader;
import menu.menus.ButtonMenu;

/**
 * Button that launches the first level of the game when pressed
 */
public class StartButton extends Button
{
    private GameSettings settings;
    /**
     * Creates a button that has a location on screen
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     * @param imagePath the button image
     * @param settings the settings to launch the game with
     */
    public StartButton(float x, float y, String imagePath, GameSettings settings)
    {
        super(x, y, imagePath);
        this.settings = settings;
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        Game invasionGame = new Game(settings);
        menu.close();
        invasionGame.setLevel(LevelReader.getInstance().getCurrentLevel());
        invasionGame.run();
    }
}
