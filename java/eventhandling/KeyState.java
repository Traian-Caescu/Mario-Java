package eventhandling;

import org.jsfml.window.Keyboard;

/**
 * A class that represents a key and whether it is pressed or not
 */
public class KeyState
{
    private Keyboard.Key keyPressed;    // The keyboard key
    private boolean isDown;             // True if pressed, false otherwise

    /**
     * Creates a KeyState with the assumption the key is not pressed
     * @param key the keyboard key
     */
    public KeyState(Keyboard.Key key)
    {
        keyPressed = key;
        isDown = false;
    }

    /**
     * Sets whether the key is pressed or not
     * @param isPressed true if the key is pressed, false otherwise
     */
    public void setState(boolean isPressed)
    {
        isDown = isPressed;
    }

    /**
     * Returns true if the key is pressed
     * @return true if the key is pressed, false otherwise
     */
    public boolean isDown()
    {
        return isDown;
    }

    /**
     * Gets the key related to the KeyState
     * @return the keyboard key this state relates to
     */
    public Keyboard.Key getKey()
    {
        return keyPressed;
    }
}
