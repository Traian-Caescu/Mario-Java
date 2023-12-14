package gameobjects.winrequirements;

import game.Game;
import gameobjects.player.Character;
import gameobjects.GameObject;
import gameobjects.interfaces.Collider;
import levels.level.LevelReader;
import levels.story.Story;
import levels.story.StoryReader;
import levels.story.StoryTime;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import rendering.GameWindow;
import timer.TimerListener;
import uielements.leaderboard.Leaderboard;
import uielements.leaderboard.LeaderboardEntry;
import uielements.leaderboard.LeaderboardReader;
import uielements.textoverlay.MessageBoard;
import uielements.textoverlay.MessageItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Helicopter extends GameObject implements TimerListener
{
    private boolean sentOverlay;
    /**
     * Creates a new GameObject at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public Helicopter(float x, float y)
    {
        super(x, y);
        setTexture("/helicopter.png");
        getSprite().setScale(0.1f, 0.1f);
        sentOverlay = false;
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {
        if(interactor instanceof Character)
        {
            Character player = game.getCurrentLevel().getCharacter();
            MessageBoard board = new MessageBoard(player.getX() -220, player.getY() + player.getHeight() + 50);
            if(game.tasksComplete())
            {

                game.getLeaderboardTimer().pauseTimer();
                long minutesTaken = Math.round((game.getLeaderboardTimer().getTotalMilliseconds()/(double)1000)/60);
                long seconds = Math.round((game.getLeaderboardTimer().getTotalMilliseconds()/(double)1000) % 60);
                String timeTaken = minutesTaken + " : " + seconds;
                LocalDateTime now = LocalDateTime.now();
                String dateAchieved = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LeaderboardEntry entry = new LeaderboardEntry(dateAchieved, timeTaken);
                LeaderboardReader.getInstance().addEntry(LevelReader.getInstance().getCurrentIndex(), entry);
                ArrayList<Story> stories = StoryReader.getInstance().getActiveStory();
                for (Story levelStory : stories)
                {
                    if(levelStory.getStoryTime().equals(StoryTime.END))
                    {
                        for (MessageItem item : levelStory.getDialogue())
                        {
                            board.addMessage(item);
                        }
                        break;
                    }
                }
                if(board.getMessages().size() > 0)
                {
                    game.openOverlay(board);
                    sentOverlay = true;
                    Game.getTimer().schedule(this, 5000f, false, false);
                }
                Leaderboard foundLeaderboard = LeaderboardReader.getInstance().getLeaderboardFromIndex(LevelReader.getInstance().getCurrentIndex());
                GameWindow window = game.getWindow();
                Vector2f location = window.getFrame().mapPixelToCoords(new Vector2i(200, 100), window.getFrame().getView());
                foundLeaderboard.setPosition(location.x, location.y);
                game.openOverlay(foundLeaderboard);
            }
            else
            {
                if(!sentOverlay)
                {
                    MessageItem message = new MessageItem("Pilot", "We haven't completed all the tasks! We can't leave now!");
                    board.addMessage(message);
                    sentOverlay = true;
                    Game.getTimer().schedule(this, 5000f, false, false);
                    game.openOverlay(board);
                }
            }
        }
    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }

    @Override
    public void onTick(Game game)
    {
        sentOverlay = false;
        Game.getTimer().remove(this);
    }
}
