package menu;

import gameobjects.GameObject;
import gameobjects.Positional;
import menu.menus.ButtonMenu;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;

/**
 * Represents an element to be displayed on the main menu
 */
public abstract class UIElement extends Positional implements Drawable
{
    private boolean isHovering; // True if the mouse is hovering over the element, false otherwise
    private Sprite elementImage;
    /**
     * Creates a UI Element that has a location on screen
     *
     * @param x the x co-ord
     * @param y the y co-ord
     */
    public UIElement(float x, float y)
    {
        super(x, y);
        isHovering = false;
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        if(elementImage != null)
        {
            elementImage.setPosition(x, y);
        }
    }

    public void setTexture(String path)
    {
        elementImage = GameObject.getSpriteFromImage(path);
        elementImage.setPosition(getX(), getY());
    }

    public Sprite getSprite()
    {
        return elementImage;
    }

    /**
     * Gets if the mouse is hovering over this objcect
     * @param mousePosition the mouse position
     * @return true if hovering, false otherwise
     */
    public boolean isInside(Vector2i mousePosition)
    {
        if(elementImage != null)
        {
            FloatRect mouseArea = new FloatRect(mousePosition.x, mousePosition.y, 1, 1);
            return elementImage.getGlobalBounds().intersection(mouseArea) != null;
        }
        return false;
    }

    /**
     * Called when mouse enters the object's area
     * @param menu the menu the object is on
     */
    public void onHoverEnter(ButtonMenu menu)
    {
        if(!isHovering)
        {
            isHovering = true;
            onEnter(menu);
        }
    }

    /**
     * Called when the mouse leaves the object
     * @param menu the menu this object is on
     */
    public void onHoverLeave(ButtonMenu menu)
    {
        if(isHovering)
        {
            isHovering = false;
            onLeave(menu);
        }
    }

    /**
     * Gets if the mouse is hovering over the object
     * @return true if hovering, false otherwise
     */
    public boolean isHovering()
    {
        return isHovering;
    }

    /**
     * Function called when mouse enters object's area
     * @param menu the menu the object is on
     */
    protected abstract void onEnter(ButtonMenu menu);

    /**
     * Function called when mouse leaves object's area
     * @param menu the menu the object is on
     */
    protected abstract void onLeave(ButtonMenu menu);

    /**
     * Function called when mouse clicks inside object's area
     * @param menu the menu the object is on
     */
    public abstract void onClick(ButtonMenu menu);

    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        if(elementImage != null)
        {
            elementImage.draw(renderTarget, renderStates);
        }
    }
}
