package gameobjects;

public abstract class Positional
{
    private float xPos;                 // The X position on screen
    private float yPos;                 // The Y position on screen
    private float prevXPos;             // The previous X value
    private float prevYPos;             // The previous Y value

    /**
     * Creates an object that has a location on screen
     * @param x the x co-ord
     * @param y the y co-ord
     */
    public Positional(float x, float y)
    {
        xPos = x;
        prevXPos = x;
        yPos = y;
        prevYPos = y;
    }

    /**
     * Sets the x position
     * @param x the new x position
     */
    public void setX(float x)
    {
        setPosition(x, yPos);
    }

    /**
     * Sets the Y position
     * @param y the new y position
     */
    public void setY(float y)
    {
        setPosition(xPos, y);
    }

    /**
     * Gets the X position
     * @return the x position
     */
    public float getX()
    {
        return xPos;
    }

    /**
     * Gets the Y position
     * @return the y position
     */
    public float getY()
    {
        return yPos;
    }

    /**
     * Gets the previous X value of the object
     * @return the previous X value
     */
    public float getPrevXPos()
    {
        return prevXPos;
    }

    /**
     * Gets the previous Y value of the object
     * @return the previous Y value
     */
    public float getPrevYPos()
    {
        return prevYPos;
    }

    public void setPreviousPosition(float x, float y)
    {
        prevXPos = x;
        prevYPos = y;
    }

    /**
     * Sets both the x and y position
     * This is called in setX and setY as the text must also be moved which is done in here
     * @param x the new x position
     * @param y the new y position
     */
    public void setPosition(float x, float y)
    {
        prevXPos = xPos;
        prevYPos = yPos;
        xPos = x;
        yPos = y;
        setPosOnScreen(x, y);
    }

    /**
     * Gets the angle in degrees from this object and another X,Y position
     * @param x the x co-ord of the other object
     * @param y the y co-ord of the other object
     * @return the angle in degrees
     */
    public int getAngleToPos(float x, float y)
    {
        float diffX = x - getX();
        float diffY = y - getY();
        double angle = Math.toDegrees(Math.atan(diffY/diffX));
        angle = Math.abs(angle);
        if(y > getY())
        {
            angle += 90;
        }
        if(x < getX())
        {
            angle = 360 - angle;
        }
        return (int)Math.round(angle);
    }

    /**
     * Gets the angle from this positional to another positional
     * @param object the positional to get the angle to
     * @return the angle to the positional in degrees
     */
    public int getAngleToPos(Positional object)
    {
        return getAngleToPos(object.getX(), object.getY());
    }

    /**
     * Sets the position on screen of the object
     * @param x the x position
     * @param y the y position
     */
    protected abstract void setPosOnScreen(float x, float y);
}
