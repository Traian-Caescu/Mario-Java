package gameobjects;

import game.Game;
import gameobjects.interfaces.Collider;
import gameobjects.interfaces.Interactable;
import org.jsfml.graphics.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class that handles all objects that must be drawn on screen
 * Most likely you will never need to instantiate this directly, just inherit it in
 * the object you want to create
 */
public abstract class GameObject extends Positional implements Drawable, Interactable
{
    private final int TEXT_OFFSET = 40; // Where the text that is displayed above the object should be placed
    private Sprite sprite;              // This is what is actually drawn on screen (JSFML required), treat it like an image
    private Text displayText;           // The text to be displayed above the image, cannot be null but can be ""
    private String imagePath;

    /**
     * Creates a new GameObject at coords (X,Y) on screen
     * @param x the x position
     * @param y the y position
     */
    public GameObject(float x, float y)
    {
        super(x, y);
        sprite = new Sprite();
        imagePath = "";
        Font textFont = new Font();
        try
        {
            InputStream fontFound = GameObject.class.getResourceAsStream("/fonts/Arimo.ttf");
            textFont.loadFromStream(fontFound);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        displayText = new Text("", textFont);
        displayText.setColor(Color.BLACK);
        FloatRect textArea = displayText.getLocalBounds();
        displayText.setOrigin(textArea.left + textArea.width/2, textArea.top + textArea.height/2); // Sets centre of movement to be the centre, not top left (makes life easier)
    }

    /**
     * Sets the text to be displayed above the object
     * @param text "" for nothing, or enter whatever text you want displayed
     * @param colour the text colour
     */
    public void setDisplayText(String text, Color colour)
    {
        Text newText = createText(text, colour);
        newText.setPosition(displayText.getPosition());
        displayText = newText;
    }

    /**
     * Creates a text object that is centre-aligned
     * @param text the text to display
     * @param colour the colour of the text
     * @return the text object created
     */
    public static Text createText(String text, Color colour)
    {
        Font textFont = new Font();
        try
        {
            InputStream fontFound = GameObject.class.getResourceAsStream("/fonts/Arimo.ttf");
            textFont.loadFromStream(fontFound);
            Text newText = new Text(text, textFont);
            newText.setColor(colour);
            FloatRect textArea = newText.getLocalBounds();
            newText.setOrigin(textArea.width/2, textArea.top + textArea.height/2);
            return newText;
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Sets what the object should look like
     * @param resourcePath the path to the file in the resources folder format: /XXXXXXXXX.png
     * @return true if the image was loaded, false if something went wrong (probably that it couldn't find the file)
     */
    public boolean setTexture(String resourcePath)
    {
        sprite = getSpriteFromImage(resourcePath);
        setPosition(getX(), getY());
        imagePath = resourcePath;
        return sprite != null;
    }

    public static Sprite getSpriteFromImage(String imagePath)
    {
        Texture newTexture = new Texture();
        Sprite newSprite;

        try
        {
            InputStream imageFile = GameObject.class.getResourceAsStream(imagePath);
            newTexture.loadFromStream(imageFile);
            imageFile.close();
            newSprite = new Sprite(newTexture);
            return newSprite;
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    /**
     * Gets the object drawn on screen
     * @return the image drawn by JSFML
     */
    public Sprite getSprite()
    {
        return sprite;
    }

    /**
     * Gets the width of the object
     * @return either the width of the object or 0 if no image is loaded
     */
    public float getWidth()
    {
        if(sprite == null)
        {
            return 0;
        }
        return Math.abs(sprite.getTexture().getSize().x * sprite.getScale().x);
    }

    /**
     * Gets the height of the object
     * @return the height of the object or 0 if no image has been loaded
     */
    public float getHeight()
    {
        if(sprite == null)
        {
            return 0;
        }
        return Math.abs(sprite.getTexture().getSize().y * sprite.getScale().y);
    }

    /**
     * Sets the rotation of the object
     * @param degrees the rotation in degrees (0 <= X <= 360)
     */
    public void setRotation(float degrees)
    {
        if(degrees >= 0 && degrees <= 360)
        {
            sprite.setRotation(degrees);
            float xDiff = getX() - sprite.getGlobalBounds().left;
            float yDiff = getY() - sprite.getGlobalBounds().top;
            sprite.setPosition(sprite.getPosition().x + xDiff, sprite.getPosition().y + yDiff);
        }
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        sprite.setPosition(x, y);
        displayText.setPosition(x + (sprite.getGlobalBounds().width/2), y - TEXT_OFFSET);
    }

    /**
     * Gets whether another GameObject is touching this
     * @param object the object to check for
     * @return true if they are touching (colliding), false otherwise
     */
    public boolean isColliding(GameObject object)
    {
        return isColliding(object.getAbsoluteCollider());
    }

    public boolean isColliding(Collider collider)
    {
        if(collider == null)
        {
            return false;
        }
        return getAbsoluteCollider().isColliding(collider);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        sprite.draw(renderTarget, renderStates);
        displayText.draw(renderTarget, renderStates);
    }

    @Override
    public abstract void onInteraction(Game game, GameObject interactor);

    @Override
    public abstract Collider getCollider();

    /**
     * Gets the collider of the sprite of this object
     * @return the collider for the entire sprite
     */
    public Collider getAbsoluteCollider()
    {
        FloatRect prevPos = new FloatRect(getPrevXPos(), getPrevYPos(), getWidth(), getHeight());
        FloatRect currentPos = new FloatRect(getX(), getY(), getWidth(), getHeight());
        if(prevPos.left == currentPos.left && prevPos.top == currentPos.top)
        {
            return new Collider(sprite.getGlobalBounds());
        }
        else
        {
            float newLeft = currentPos.left;
            float newTop = currentPos.top;
            float newWidth = (prevPos.left + prevPos.width) - newLeft;
            float newHeight = (prevPos.top + prevPos.height) - newTop;
            if(prevPos.left < currentPos.left)
            {
                newLeft = prevPos.left;
                newWidth = (currentPos.left + currentPos.width) - newLeft;
            }
            if(prevPos.top < currentPos.top)
            {
                newTop = prevPos.top;
                newHeight = (currentPos.top + currentPos.height) - newTop;
            }
            return new Collider(newLeft, newTop, newWidth, newHeight);
        }
    }

    public String getImagePath()
    {
        return imagePath;
    }
}
