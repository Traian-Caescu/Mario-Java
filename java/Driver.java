import game.GameSettings;
import menu.elements.difficulty.DifficultyLevel;
import menu.menus.MainMenu;

public class Driver
{
    public static void main(String[] args){
        MainMenu invasionMenu = new MainMenu(new GameSettings(DifficultyLevel.MEDIUM, 10));
    }
}
