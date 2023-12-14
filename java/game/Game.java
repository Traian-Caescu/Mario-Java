package game;

import audio.SoundPlayer;
import audio.SoundType;
import eventhandling.KeyCollection;
import gameobjects.*;
import gameobjects.player.Character;
import gameobjects.player.hearts.HeartsManager;
import gameobjects.powerups.PowerUp;
import gameobjects.powerups.segmentdisplay.PowerUpDisplay;
import gameobjects.traps.Enemy;
import gameobjects.traps.movingtraps.Animator;
import gameobjects.traps.shootingtraps.CannonBullet;
import gameobjects.player.DeathDelayer;
import gameobjects.winrequirements.TaskRequirement;
import levels.story.Story;
import levels.story.StoryReader;
import levels.story.StoryTime;
import menu.menus.MainMenu;
import rendering.Renderer;
import timer.GameTimer;
import levels.level.Level;
import levels.level.LevelReader;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import timer.LeaderboardTimer;
import timer.TimedItem;
import timer.TimerListener;
import uielements.Overlay;
import uielements.PauseScreen;
import uielements.textoverlay.MessageBoard;
import uielements.textoverlay.MessageItem;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the game that does the loading of levels
 * and the displaying of game objects
 */
public class Game extends Renderer
{
    private ArrayList<Overlay> activeOverlays;  // The list of overlays currently being displayed
    private LevelReader levelReader;            // A file reader for level files
    private StoryReader storyReader;            // A file reader for story files
    private Level currentLevel;                 // The current level in the game
    private KeyCollection Movementkeys;         // The collection of keys that say if a key has been pressed or not
    private boolean moveScreen = false;         // True if the player has moved, false otherwise
    private boolean isPaused = false;           // True if the game is paused, false otherwise
    private boolean loadNextLevel = false;      // True if the next level should be loaded, false otherwise
    private int scrollSpeed = 6;                // The speed of player movement
    private float lowestY;                      // The lowest Y value in the level
    private SoundPlayer backgroundPlayer;       // The sound player for background music
    private SoundPlayer playerSound;            // The sound player for player noises
    private static GameTimer timer;             // The timer that underpins all the animations
    HeartsManager heartsManager;                // Manages player's visual lives
    private GameSettings settings;              // The game settings for the game
    private PowerUpDisplay powerDisplay;        // Shows active powerups
    private LeaderboardTimer leaderboardTimer;  // Records times for the leaderboard

    /**
     * Creates a game
     */
    public Game(GameSettings settings)
    {
        super("Invasion", 1280, 720);
        this.settings = settings;
        backgroundPlayer = new SoundPlayer();
        playerSound = new SoundPlayer();
        activeOverlays = new ArrayList<>();
        buildKeys();
        timer = GameTimer.getInstance(this);
        levelReader = LevelReader.getInstance();
        storyReader = StoryReader.getInstance();
        leaderboardTimer = new LeaderboardTimer();
    }

    /**
     * Creates the list of keys the game detects
     */
    private void buildKeys()
    {
        Movementkeys = KeyCollection.getInstance();
        Movementkeys.addKey(Keyboard.Key.LEFT);
        Movementkeys.addKey(Keyboard.Key.RIGHT);
        Movementkeys.addKey(Keyboard.Key.UP);
        Movementkeys.addKey(Keyboard.Key.DOWN);
        Movementkeys.addKey(Keyboard.Key.SPACE);
    }

    /**
     * Unsets all keys
     */
    public void clearKeys()
    {
        Movementkeys.setPressed(Keyboard.Key.LEFT, false);
        Movementkeys.setPressed(Keyboard.Key.UP, false);
        Movementkeys.setPressed(Keyboard.Key.SPACE, false);
        Movementkeys.setPressed(Keyboard.Key.DOWN, false);
        Movementkeys.setPressed(Keyboard.Key.RIGHT, false);
    }

    /**
     * Gets the class which reads levels from files
     * @return the level reader
     */
    public LevelReader getLevels()
    {
        return levelReader;
    }

    /**
     * Sets the scroll speed of game as player moves
     * @param scrollSpeed Scroll speed, default = 6
     */
    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public int getScrollSpeed()
    {
        return scrollSpeed;
    }

    public Level getCurrentLevel()
    {
        return currentLevel;
    }

    /**
     * Moves the player by a given amount
     * @param dx the amount to move the player in the X direction
     * @param dy the amount to move the player in the Y direction
     */
    public void scrollObjects(int dx, int dy)
    {
        Character player = currentLevel.getCharacter();
        player.setPosition(player.getX() + dx, player.getY() + dy);
    }

    /**
     * Pauses the game
     * @param killer the object to remove, can be null
     */
    public void pauseOnDeath(Enemy killer)
    {
        new DeathDelayer(this, killer);
    }

    /**
     * Gets if the game is paused
     * @return true if the game is paused, false otherwise
     */
    public boolean isPaused()
    {
        return isPaused;
    }

    /**
     * Gets the object that records leaderboard times
     * @return the leaderboard timer
     */
    public LeaderboardTimer getLeaderboardTimer()
    {
        return leaderboardTimer;
    }

    /**
     * Sets the current level of the game
     * @param level the level to load
     */
    public void setLevel(Level level)
    {
        Game.getTimer().clear();
        activeOverlays.clear();
        isPaused = false;
        for (Drawable drawable : getDrawables())
        {
            removeDrawable(drawable);
        }
        currentLevel = level;
        if(currentLevel != null)
        {
            currentLevel.setLocked(false);
            loadLevel(currentLevel);
            backgroundPlayer.setVolume(settings.getVolume());
            backgroundPlayer.loop(true);
            backgroundPlayer.play(SoundType.DEFAULT_BACKGROUND);
            leaderboardTimer.reset();
            leaderboardTimer.startTimer();
        }
        else
        {
            backgroundPlayer.stop();
            playerSound.stop();
            settings.setShowThanks(true);
            close();
            new MainMenu(settings);
        }
    }

    /**
     * Displays the passed level
     * @param level the level to draw on screen
     */
    private void loadLevel(Level level)
    {
        loadNextLevel = false;
        clearKeys();
        setScrollSpeed(6);
        refreshScreen();
        getWindow().setBackground(level.getBackgroundImage());
        for (GameObject object : level.getAllObjects())
        {
            addDrawable(object);
        }
        lowestY = 0;
        for (Drawable object : level.getAllObjects())
        {
            if(object instanceof GameObject)
            {
                GameObject gameObj = (GameObject) object;
                if(gameObj.getY() > lowestY)
                {
                    lowestY = gameObj.getY();
                }
                if(object instanceof Animator)
                {

                    Animator animator = (Animator)object;
                    animator.start();
                }
            }
        }
        heartsManager = new HeartsManager(settings.getDifficulty().getLives());
        powerDisplay = new PowerUpDisplay(100, 10);
        addDrawable(powerDisplay);
        currentLevel.getCharacter().setLives(settings.getDifficulty().getLives(), null);
        heartsManager.updateLives(this);
        currentLevel.getCharacter().getJumpHandler().start();
        loadStory();
    }

    /**
     * Loads the opening story into a message board and displays it
     */
    private void loadStory()
    {
        int fileIndex = levelReader.getCurrentIndex();
        ArrayList<Story> stories = storyReader.setActiveStory(fileIndex);
        for (Story levelStory : stories)
        {
            if(levelStory.getStoryTime().equals(StoryTime.START))
            {
                Character player = currentLevel.getCharacter();
                MessageBoard message = new MessageBoard(player.getX() -220, player.getY() + player.getHeight() + 50);
                for (MessageItem item : levelStory.getDialogue())
                {
                    message.addMessage(item);
                }
                openOverlay(message);
                break;
            }
        }
    }

    /**
     * Run the collisions functions if the player has collided with something
     */
    public void doCollisions()
    {
        Character player = currentLevel.getCharacter();
        player.setSafe(false);
        for (Drawable collider : getDrawables())
        {
            if(!isPaused)
            {
                if(collider instanceof GameObject)
                {
                    GameObject object = (GameObject) collider;
                    if(object.isColliding(player))
                    {
                        object.onInteraction(this, player);
                    }
                }
            }
        }
    }

    /**
     * Add an overlay to the list of active ones
     * @param newOverlay the new overlay
     */
    public void openOverlay(Overlay newOverlay)
    {
        activeOverlays.add(newOverlay);
    }

    /**
     * Closes the most recent overlay
     */
    public void closeOverlay()
    {
        if(activeOverlays.size() > 0)
        {
            activeOverlays.remove(0);
        }
    }

    /**
     * Gets if the player has fallen below a certain point
     * @return true if the player is off the map, false otherwise
     */
    public boolean playerFallen()
    {
        Character player = currentLevel.getCharacter();
        return (player.getY() > lowestY + 100);
    }

    /**
     * Teleports the player to the most recent checkpoint or spawn if there is no recent checkpoint
     */
    public void teleportToCheckpoint()
    {
        Character player = currentLevel.getCharacter();
        Checkpoint mostRecentCheckpoint = currentLevel.getCheckpointCollection().getMostRecentCheckpoint();
        if(mostRecentCheckpoint == null)
        {
            float[] spawn = currentLevel.getSpawn();
            player.setPosition(spawn[0], spawn[1]);
        }
        else
        {
            player.setPosition(mostRecentCheckpoint.getX() + 10, mostRecentCheckpoint.getY() - 10);
        }
    }

    /**
     * Removes displayed powerups and their effects
     */
    private void clearPowerups()
    {
        ArrayList<TimerListener> toRemove = new ArrayList<>();
        for (TimedItem item : timer.getItems())
        {
            if(item.getListener() instanceof PowerUp)
            {
                toRemove.add(item.getListener());
            }
        }
        for (TimerListener listener : toRemove)
        {
            timer.remove(listener);
            listener.onTick(this);
        }
        powerDisplay.clear();
    }

    /**
     * Respawns the player at a checkpoint
     */
    public void respawn()
    {
        Character player = currentLevel.getCharacter();
        clearPowerups();
        if(player.isDead())
        {
            teleportToCheckpoint();
            player.setLives(player.getBaseLives(), null);
        }
        else
        {
            player.setPosition(player.getLastSafePos()[0], player.getLastSafePos()[1]);
        }
        for (Drawable drawable : getDrawables())
        {
            if(drawable instanceof CannonBullet)
            {
                CannonBullet bullet = (CannonBullet) drawable;
                bullet.stop();
                removeDrawable(bullet);
            }
        }
        heartsManager.updateLives(this);
        player.setWalking(false);
        player.setJump(true);
    }

    @Override
    public void run()
    {
        Character player;
        PauseScreen pause = new PauseScreen(0,0);
        heartsManager.updateLives(this);
        while (isOpen())
        {
            player = currentLevel.getCharacter();
            Vector2f vec = currentLevel.getCharacter().getSprite().getPosition();
            View mainView = (View) getWindow().getFrame().getView();
            mainView.setCenter(vec);
            heartsManager.setPosition(player.getX() - getWindow().getWidth()/2, player.getY() - getWindow().getHeight()/2);
            powerDisplay.setPosition(player.getX() + getWindow().getWidth()/2 - 300, player.getY() - getWindow().getHeight()/2);
            getWindow().clear();
            getWindow().getFrame().setView(mainView);
            timer.elapseTime();
            for(Event event : getWindow().getFrame().pollEvents())
            {
                if (event.type == Event.Type.CLOSED)
                {
                    close();
                }
                if(event.type == Event.Type.KEY_RELEASED)
                {
                    if(!isPaused || event.asKeyEvent().key.equals(Keyboard.Key.ESCAPE))
                    {
                        KeyEvent keyEvent = event.asKeyEvent();
                        Movementkeys.setPressed(keyEvent.key, false);
                        if(!Movementkeys.keyDown())
                        {
                            moveScreen = false;
                        }
                    }
                }
                if(event.type == Event.Type.KEY_PRESSED)
                {
                    if(!isPaused || event.asKeyEvent().key.equals(Keyboard.Key.ESCAPE))
                    {
                        moveScreen = true;
                        KeyEvent keyEvent = event.asKeyEvent();
                        Movementkeys.setPressed(keyEvent.key, true);
                        if(keyEvent.key.equals(Keyboard.Key.ESCAPE))
                        {
                            if(isPaused)
                            {
                                pause.hide(this);
                                Movementkeys.setPressed(keyEvent.key, false);
                            }
                            else
                            {
                                pause.setPosition(player.getX(), player.getY());
                                pause.show(this);
                            }
                        }
                    }
                }
                else if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
                {
                    Movementkeys.setPressed(Keyboard.Key.RETURN, true);
                }
                else if(event.type == Event.Type.MOUSE_BUTTON_RELEASED)
                {
                    Movementkeys.setPressed(Keyboard.Key.RETURN, false);
                }
            }
            if (!isPaused)
            {
                if(playerFallen())
                {
                    if(!player.isInvincible())
                    {
                        player.addLives(-1, this);
                    }
                    new DeathDelayer(this, null);
                }
                boolean walking = false;
                if(moveScreen)
                {
                    if(Movementkeys.keyPressed(Keyboard.Key.RIGHT))
                    {
                        scrollObjects(+scrollSpeed, 0);
                        walking = true;
                        player.turnRight();
                    }
                    if(Movementkeys.keyPressed(Keyboard.Key.LEFT))
                    {
                        scrollObjects(-scrollSpeed, 0);
                        walking = true;
                        player.turnLeft();
                    }
                    if(Movementkeys.keyPressed(Keyboard.Key.UP) || Movementkeys.keyPressed(Keyboard.Key.SPACE))
                    {
                        if(player.canJump())
                        {
                            playerSound.setVolume(50);
                            playerSound.play(SoundType.JUMP);
                            player.setJump(false);
                            player.setY(player.getY()-25);
                            player.setPreviousPosition(player.getX(), player.getY());
                            player.setSafe(false);
                        }
                    }
                }
                player.setWalking(walking);
                doCollisions();
            }
            showOverlays();
            refreshScreen();
            if(loadNextLevel)
            {
                setLevel(levelReader.getNextLevel());
            }
        }
        backgroundPlayer.stop();
        playerSound.stop();
        if(currentLevel != null)
        {
            new MainMenu(settings);
        }
    }

    /**
     * Adds a powerup to the powerup displayer
     * @param powerup the powerup to add
     */
    public void addPowerupToDisplay(PowerUp powerup)
    {
        powerDisplay.addPowerup(powerup.duplicate());
    }

    /**
     * Gets the class that manages the visuals of the player's lives
     * @return the hearts manager
     */
    public HeartsManager getHeartsManager()
    {
        return heartsManager;
    }

    /**
     * Goes through the list of active overlays one at a time
     */
    private void showOverlays()
    {
        if(activeOverlays.size() > 0)
        {
            if(activeOverlays.get(0).isDisplaying())
            {
                if(Movementkeys.keyPressed(Keyboard.Key.RETURN))
                {
                    activeOverlays.get(0).onClick(this);
                    Movementkeys.setPressed(Keyboard.Key.RETURN, false);
                }
            }
            else
            {
                activeOverlays.get(0).show(this);
            }
        }
    }

    /**
     * Gets if player is colliding with enemy
     * @return true if player is colliding with enemy, false otherwise
     */
    public boolean playerInDanger()
    {
        for (GameObject object : currentLevel.getAllObjects())
        {
            if(object instanceof Enemy)
            {
                if(object.isColliding(currentLevel.getCharacter()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets if an overlay is open
     * @return true if at least one overlay is active
     */
    public boolean overlayOpen()
    {
        for (Overlay overlay : activeOverlays)
        {
            if(overlay.isDisplaying())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * A flag to tell the game to load the next level when it next can
     */
    public void loadNextLevel()
    {
        loadNextLevel = true;
    }

    /**
     * Pauses/unpauses the game
     * @param paused true if the game should be paused, false otherwise
     */
    public void setPaused(boolean paused)
    {
        isPaused = paused;
        if(paused)
        {
            leaderboardTimer.pauseTimer();
        }
        else
        {
            leaderboardTimer.startTimer();
        }
        clearKeys();
    }

    /**
     * Gets if all the tasks for the current level have been completed
     * @return true if the tasks are complete, false otherwise
     */
    public boolean tasksComplete()
    {
        for (TaskRequirement requirement : currentLevel.getRequirements())
        {
            if(!requirement.isComplete(this))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the timer the game uses
     * @return the timer
     */
    public static GameTimer getTimer()
    {
        return timer;
    }
}
