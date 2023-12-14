package uielements.leaderboard;

import game.Game;
import gameobjects.GameObject;
import org.jsfml.graphics.*;
import uielements.Overlay;
import java.util.ArrayList;

/**
 * A leaderboard which shows the top 5 best times in the level
 */
public class Leaderboard extends Overlay
{
    private ArrayList<Text> entries;    // Leaderboard entries, includes date achieved and time taken
    private Sprite leaderboardImage;    // The background image
    private float yOffset;              // The yOffset from the top left of the leaderboardImage
    private float dateXOffset;          // the xOffset from the top left of the leaderboardImage for the date achieved
    private float timeXOffset;          // the xOffset from the top left of the leaderboardImage for the time taken
    /**
     * Creates a UI Element that has a location on screen
     *
     * @param x the x co-ord
     * @param y the y co-ord
     */
    public Leaderboard(float x, float y)
    {
        super(x, y);
        entries = new ArrayList<>();
        leaderboardImage = GameObject.getSpriteFromImage("/leaderboard/leaderboard.png");
        leaderboardImage.setPosition(x, y);
        yOffset = y + 165;
        dateXOffset = 12;
        timeXOffset = 600;
    }

    /**
     * Adds an entry to the leaderboard
     * @param entry the leaderboard entry
     */
    public void addEntry(LeaderboardEntry entry)
    {
        if(entries.size() < 10) // Max 5 entries
        {
            Text newDate = GameObject.createText(entry.getDate(), Color.BLACK);
            newDate.setOrigin(0,0);
            newDate.setPosition(getX() + dateXOffset, getY() + yOffset);
            Text newTime = GameObject.createText(entry.getTime(), Color.BLACK);
            newTime.setOrigin(0,0);
            newTime.setPosition(getX() + timeXOffset, getY() + yOffset);
            entries.add(newDate);
            entries.add(newTime);
            yOffset += 100;
        }
    }

    @Override
    protected void showOverlay(Game game)
    {
        game.addDrawable(this);
    }

    @Override
    protected void hideOverlay(Game game)
    {
        game.removeDrawable(this);
        game.closeOverlay();
        game.loadNextLevel();
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        float xDiff = x - getPrevXPos();
        float yDiff = y - getPrevYPos();
        leaderboardImage.setPosition(x, y);
        for (Text text : entries)
        {
            text.setPosition(text.getPosition().x + xDiff, text.getPosition().y + yDiff);
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states)
    {
        leaderboardImage.draw(target, states);
        for (Text text : entries)
        {
            text.draw(target, states);
        }
    }

    @Override
    public void onClick(Game game)
    {
        hide(game);
    }
}
