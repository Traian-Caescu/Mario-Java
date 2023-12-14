package eventhandling;

import org.jsfml.window.Keyboard;

import java.util.ArrayList;

/**
 * A class that makes using keyboard keys easier
 */
public class KeyCollection
{
    private ArrayList<KeyState> keys;           // The list of keys and if they are pressed or not
    private static KeyCollection collection;    // Ensures only one instance of this class can exist

    /**
     * Gets the instance of this class or creates a new one if there isn't an instance
     * @return the instance of this class
     */
    public static KeyCollection getInstance()
    {
        if(collection == null)
        {
            collection = new KeyCollection();
        }
        return collection;
    }

    /**
     * Initialises the list
     */
    private KeyCollection()
    {
        keys = new ArrayList<>();
    }

    /**
     * Adds a keyboard key to the list
     * @param key the key (assumes not pressed)
     */
    public void addKey(Keyboard.Key key)
    {
        if(!keyExists(key))
        {
            keys.add(new KeyState(key));
        }
    }

    /**
     * Gets if a key is already in the list
     * @param key the key to test for
     * @return true if it is in the list, false otherwise
     */
    public boolean keyExists(Keyboard.Key key)
    {
        return (getKeyInfo(key) != null);
    }

    /**
     * Gets the KeyState for a key (the key and if it is pressed)
     * @param key the keyboard key to find
     * @return the state of this key, null if it isn't in the list
     */
    public KeyState getKeyInfo(Keyboard.Key key)
    {
        for (KeyState keyInfo : keys)
        {
            if(keyInfo.getKey().equals(key))
            {
                return keyInfo;
            }
        }
        return null;
    }

    /**
     * Gets if a key has been pressed
     * @param key the key to test for
     * @return true if it is pressed, false otherwise
     */
    public boolean keyPressed(Keyboard.Key key)
    {
        KeyState keyInfo = getKeyInfo(key);
        if(keyInfo != null)
        {
            return keyInfo.isDown();
        }
        else
        {
            return false;
        }
    }

    /**
     * Sets the key to the given pressed value
     * Will add key to list if it is not found
     * @param key the key that has/hasn't been pressed
     * @param isPressed the state the key is in (pressed = true, unpressed = false)
     */
    public void setPressed(Keyboard.Key key, boolean isPressed)
    {
        addKey(key);
        KeyState keyInfo = getKeyInfo(key);
        keyInfo.setState(isPressed);
    }

    /**
     * Gets if any key is currently pressed
     * @return true if at least one key is pressed, false otherwise
     */
    public boolean keyDown()
    {
        for (KeyState keyInfo : keys)
        {
            if(keyInfo.isDown())
            {
                return true;
            }
        }
        return false;
    }
}
