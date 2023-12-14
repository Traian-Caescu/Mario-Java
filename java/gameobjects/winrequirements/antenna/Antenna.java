package gameobjects.winrequirements.antenna;

import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import gameobjects.player.Character;
import levels.level.LevelReader;
import levels.story.Story;
import levels.story.StoryReader;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import rendering.GameWindow;
import uielements.leaderboard.Leaderboard;
import uielements.leaderboard.LeaderboardEntry;
import uielements.leaderboard.LeaderboardReader;
import uielements.textoverlay.MessageBoard;
import uielements.textoverlay.MessageItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Antenna in the game that is broadcasting the robot orders
 */
public class Antenna extends GameObject
{
    private boolean optionChosen;   // True if the player has already chosen to deactivate the antenna
    /**
     * Creates a new Antenna at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public Antenna(float x, float y)
    {
        super(x, y);
        optionChosen = false;
        setTexture("/antenna/activated.png");
        getSprite().setScale(2f, 2f);
    }

    /**
     * Does the story for when the antenna is deactivated
     * @param game the game to run the story on
     */
    public void deactivate(Game game)
    {
        if(!optionChosen)
        {
            optionChosen = true;
            setTexture("/antenna/deactivated.png");
            getSprite().setScale(2f, 2f);
            game.getLeaderboardTimer().pauseTimer();
            long minutesTaken = Math.round((game.getLeaderboardTimer().getTotalMilliseconds()/(double)1000)/60);
            long seconds = Math.round((game.getLeaderboardTimer().getTotalMilliseconds()/(double)1000) % 60);
            String timeTaken = minutesTaken + " : " + seconds;
            LocalDateTime now = LocalDateTime.now();
            String dateAchieved = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LeaderboardEntry entry = new LeaderboardEntry(dateAchieved, timeTaken);
            LeaderboardReader.getInstance().addEntry(LevelReader.getInstance().getCurrentIndex(), entry);
            Story Story = StoryReader.getInstance().getStoryFromIndex(9); // 9 is the dummy level name for this ending
            Character player = game.getCurrentLevel().getCharacter();
            MessageBoard board = new MessageBoard(player.getX() -220, player.getY() + player.getHeight() + 50);
            for (MessageItem item : Story.getDialogue())
            {
                board.addMessage(item);
            }
            game.openOverlay(board);
            Leaderboard foundLeaderboard = LeaderboardReader.getInstance().getLeaderboardFromIndex(LevelReader.getInstance().getCurrentIndex());
            GameWindow window = game.getWindow();
            Vector2f location = window.getFrame().mapPixelToCoords(new Vector2i(200, 100), window.getFrame().getView());
            foundLeaderboard.setPosition(location.x, location.y);
            game.openOverlay(foundLeaderboard);
        }
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {

    }

    @Override
    public Collider getCollider()
    {
        return null;
    }
}
