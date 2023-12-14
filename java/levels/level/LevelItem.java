package levels.level;

import java.util.List;

/**
 * A class that wraps the data received from a line in the CSV level file
 */
public class LevelItem
{
    private String name;        // The name of the item
    private float xPos;         // The X position
    private float yPos;         // The Y position
    private int rotation;       // The rotation of the object (in degrees)
    private double scale;       // The scale of the object
    private List<String> extraInfo; // Any other information the CSV file might contain

    /**
     * Creates a level item
     * @param xPos the x position
     * @param yPos the y position
     * @param name the name of the item
     * @param scale the scale of it
     * @param rotation the rotation angle (in degrees)
     */
    public LevelItem(float xPos, float yPos, String name, double scale, int rotation, List<String> extra)
    {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
        this.scale = scale;
        extraInfo = extra;
    }

    /**
     * Gets the name of the item
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the X position
     * @return the x position
     */
    public float getXPos()
    {
        return xPos;
    }

    /**
     * Gets the Y position
     * @return the y position
     */
    public float getYPos()
    {
        return yPos;
    }

    /**
     * Gets the rotation of the object (in degrees)
     * @return the angle (0 <= X <= 360)
     */
    public int getRotation()
    {
        return rotation;
    }

    /**
     * Gets the scale of the object
     * @return the scale
     */
    public double getScale()
    {
        return scale;
    }

    public List<String> getExtraInfo()
    {
        return extraInfo;
    }
}
