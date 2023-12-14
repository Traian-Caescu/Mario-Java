package gameobjects.platforms;

/**
 * Like a platform, but a different texture
 */
public class Floor extends Platform
{
    /**
     * Creates a floor
     *
     * @param x x position of floor
     * @param y y position of floor
     */
    public Floor(float x, float y)
    {
        super(x, y);
        setTexture("/floor.png");
        getSprite().scale(0.2f, 0.2f);
        setPosition(x, y);
    }
}
