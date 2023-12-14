package menu.elements.buttons;

import gameobjects.GameObject;
import menu.UIElement;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.*;

/**
 * Represents a clickable button that has text inside it
 */
public abstract class Button extends UIElement
{
    private Text displayText;   // The text to display
    /**
     * Creates a button that has a location on screen
     *
     * @param x the x co-ord
     * @param y the y co-ord
     */
    public Button(float x, float y, String imagePath)
    {
        super(x, y);
        setTexture(imagePath);
        getSprite().setOrigin(getSprite().getLocalBounds().width/2, getSprite().getLocalBounds().height/2);
        displayText = GameObject.createText("", Color.BLACK);
        setPosition(x, y);
    }

    /**
     * Sets the text of the button
     * @param text what the button should say
     * @param colour the colour of the text
     */
    public void setText(String text, Color colour)
    {
        displayText = GameObject.createText(text, colour);
        displayText.setCharacterSize(40);
        displayText.setPosition(getX() - 19, getY());
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        getSprite().setPosition(x, y);
        displayText.setPosition(x, y);
    }

    @Override
    protected void onEnter(ButtonMenu menu)
    {
        setTexture("/ui/selectedButton.png");
        getSprite().setOrigin(getSprite().getLocalBounds().width/2, getSprite().getLocalBounds().height/2);
        setPosition(getX() - 19, getY());
    }

    @Override
    protected void onLeave(ButtonMenu menu)
    {
        setTexture("/ui/button.png");
        getSprite().setOrigin(getSprite().getLocalBounds().width / 2, getSprite().getLocalBounds().height / 2);
        setPosition(getX() + 19, getY());
        displayText.setPosition(getX() - 19, getY());
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        getSprite().draw(renderTarget, renderStates);
        displayText.draw(renderTarget, renderStates);
    }
}
