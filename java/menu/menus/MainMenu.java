package menu.menus;

import game.GameSettings;
import gameobjects.GameObject;
import menu.elements.buttons.*;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;

/**
 * A class to give the user options before starting a game
 */
public class MainMenu extends ButtonMenu
{
    /**
     * Creates a new main menu (800 x 600)
     */
    public MainMenu(GameSettings settings)
    {
        super("Main Screen", 800, 600);
        Button startButton = new StartButton(384, 157, "/ui/button.png", settings);
        startButton.setText("Start", Color.BLACK);
        Button levelSelection = new LevelSelectionButton(startButton.getX(), startButton.getY() + 86, "/ui/button.png", settings);
        levelSelection.setText("Select Level", Color.BLACK);
        Button tutorial = new TutorialButton(levelSelection.getX(), levelSelection.getY() + 86, "/ui/button.png");
        tutorial.setText("Tutorial", Color.BLACK);
        Button options = new OptionsButton(tutorial.getX(), tutorial.getY() + 86, "/ui/button.png", settings);
        options.setText("Options", Color.BLACK);
        Button quit = new QuitButton(options.getX(), options.getY() + 86, "/ui/button.png");
        quit.setText("Quit", Color.BLACK);
        MenuLayout newLayout = new MenuLayout("Main Menu", "/ui/startMenuBackground.png");
        newLayout.addDrawable(startButton);
        newLayout.addDrawable(levelSelection);
        newLayout.addDrawable(tutorial);
        newLayout.addDrawable(options);
        newLayout.addDrawable(quit);
        PagedMenu pagedMenu = new PagedMenu();
        pagedMenu.addPage(newLayout);
        setMenu(pagedMenu);
        if(settings.showThanks())
        {
            settings.setShowThanks(false);
            MenuLayout thanksLayout = new MenuLayout("Thank you", "/ui/blurbackground.png");
            Text thanksText = GameObject.createText("Thank you for playing!", Color.BLACK);
            thanksText.setCharacterSize(48);
            thanksText.setPosition(300,200);
            thanksText.setCharacterSize(48);
            Text desc = GameObject.createText("We hoped you had fun!", Color.BLACK);
            desc.setCharacterSize(30);
            desc.setPosition(300,300);
            thanksLayout.addDrawable(thanksText);
            thanksLayout.addDrawable(desc);
            PagedMenu thanksMenu = new PagedMenu();
            thanksMenu.addPage(thanksLayout);
            setMenu(thanksMenu);
        }
        run();
    }
}
