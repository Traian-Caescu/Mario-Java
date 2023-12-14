package menu.elements.buttons;

import menu.menus.ButtonMenu;

/**
 * Button that closes the window when pressed
 */
public class QuitButton extends Button
{
    /**
     * Creates a button that has a location on screen
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     * @param imagePath the button image
     */
    public QuitButton(float x, float y, String imagePath)
    {
        super(x, y, imagePath);
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        menu.close();
    }
}
