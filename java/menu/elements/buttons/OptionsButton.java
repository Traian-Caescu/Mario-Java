package menu.elements.buttons;

import game.GameSettings;
import gameobjects.GameObject;
import menu.menus.Banner;
import menu.menus.ButtonMenu;
import menu.menus.MenuLayout;
import menu.elements.difficulty.DifficultyDown;
import menu.elements.difficulty.DifficultySelector;
import menu.elements.difficulty.DifficultyUp;
import menu.menus.PagedMenu;
import menu.elements.volume.DecreaseVolumeButton;
import menu.elements.volume.IncreaseVolumeButton;
import menu.elements.volume.VolumeHandler;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;

/**
 * Button that opens up the options menu
 */
public class OptionsButton extends Button
{
    private GameSettings settings;
    /**
     * Creates a button that has a location on screen
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     * @param imagePath the image of the button
     */
    public OptionsButton(float x, float y, String imagePath, GameSettings settings)
    {
        super(x, y, imagePath);
        this.settings = settings;
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        MenuLayout newLayout = new MenuLayout("Options","/ui/blurBackground.png");
        VolumeHandler handler = new VolumeHandler(550, 235, Color.BLACK, settings);
        IncreaseVolumeButton upVolume = new IncreaseVolumeButton(600, 200, handler);
        DecreaseVolumeButton downVolume = new DecreaseVolumeButton(685, 200, handler);
        DifficultySelector difficultySelector = new DifficultySelector(470, 420, Color.BLACK, settings);
        DifficultyUp difficultyUp = new DifficultyUp(600, 400, difficultySelector);
        DifficultyDown difficultyDown = new DifficultyDown(685, 400, difficultySelector);
        Text title = GameObject.createText("Options", Color.BLACK);
        title.setPosition(330, 30);
        title.setCharacterSize(52);
        Text volumeDesc = GameObject.createText("Volume:", Color.BLACK);
        volumeDesc.setOrigin(0, 0);
        volumeDesc.setPosition(200, 220);
        volumeDesc.setCharacterSize(40);
        Text difficultyDesc = GameObject.createText("Difficulty:", Color.BLACK);
        difficultyDesc.setOrigin(0, 0);
        difficultyDesc.setPosition(200, 410);
        difficultyDesc.setCharacterSize(40);
        newLayout.addDrawable(upVolume);
        newLayout.addDrawable(downVolume);
        newLayout.addDrawable(handler);
        newLayout.addDrawable(title);
        newLayout.addDrawable(difficultySelector);
        newLayout.addDrawable(difficultyUp);
        newLayout.addDrawable(difficultyDown);
        newLayout.addDrawable(volumeDesc);
        newLayout.addDrawable(difficultyDesc);
        PagedMenu pagedMenu = new PagedMenu();
        pagedMenu.addPage(newLayout);
        menu.setMenu(pagedMenu);
    }
}
