package menu.elements.gameobjects;

import gameobjects.GameObject;
import menu.UIElement;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 * A class that wraps a GameObject into a UIElement to
 * be used for the start menu
 */
public class UIGameObject extends UIElement
{
    private GameObject gameObject; // The GameObject to wrap
    /**
     * Creates a UI Element from a GameObject
     *
     * @param gameObject the GameObject to wrap
     */
    public UIGameObject(GameObject gameObject)
    {
        super(gameObject.getX(), gameObject.getY());
        this.gameObject = gameObject;
    }

    /**
     * Sets the scale of the sprite in the GameObject
     * @param scale the scale
     */
    public void setScale(float scale)
    {
        gameObject.getSprite().setScale(scale, scale);
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        gameObject.setPosition(x, y);
    }

    @Override
    public void draw(RenderTarget target, RenderStates states)
    {
        gameObject.draw(target, states);
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
    public void onClick(ButtonMenu menu)
    {

    }
}
