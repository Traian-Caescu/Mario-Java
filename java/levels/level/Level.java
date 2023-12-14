package levels.level;

import gameobjects.*;
import gameobjects.player.Character;
import gameobjects.platforms.Platform;
import gameobjects.traps.Enemy;
import gameobjects.winrequirements.coin.CoinRequirement;
import gameobjects.winrequirements.generator.Generator;
import gameobjects.winrequirements.generator.GeneratorRequirement;
import gameobjects.winrequirements.TaskRequirement;
import levels.CheckpointCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a level (a collection of objects)
 * THIS CLASS DOES NOTHING (IT'S JUST A DATA STRUCTURE)
 */
public class Level
{
    private String levelName;                   // The level name (not the same as file name)
    private String fileName;                    // The file name of this level
    private String backgroundImage;             // The file path of the image that is the background for the level
    private Character player;                   // The player
    private List<Enemy> enemies;                // The enemies
    private CheckpointCollection checkpoints;   // The checkpoints in the level
    private List<Platform> platforms;           // The platforms of the level
    private List<GameObject> miscObjects;       // The list of other objects
    private List<TaskRequirement> requirements; // The list of tasks that must be complete before the player can progress
    private float[] spawnLocation;              // The spawn location (0,0 by default)
    private boolean isLocked;

    /**
     * Creates a level with a name and a file name
     * By default, the level is locked
     * @param name the name of the level
     * @param fileName the file name
     */
    public Level(String name, String fileName)
    {
        levelName = name;
        this.fileName = fileName;
        enemies = new ArrayList<>();
        checkpoints = new CheckpointCollection();
        platforms = new ArrayList<>();
        miscObjects = new ArrayList<>();
        requirements = new ArrayList<>();
        requirements.add(new CoinRequirement());
        requirements.add(new GeneratorRequirement());
        spawnLocation = new float[]{0 ,0};
        isLocked = true;
    }

    public boolean isLocked()
    {
        return isLocked;
    }

    public void setLocked(boolean locked)
    {
        isLocked = locked;
    }

    /**
     * Sets the background image of the level
     * @param resLocation the file path of the background image
     */
    public void setBackgroundImage(String resLocation)
    {
        backgroundImage = resLocation;
    }

    /**
     * Gets the file path of the background image
     * @return the background image's file path
     */
    public String getBackgroundImage()
    {
        return backgroundImage;
    }

    /**
     * Sets the character for the class
     * @param newCharacter the character
     */
    public void setCharacter(Character newCharacter)
    {
        player = newCharacter;
    }

    /**
     * Sets the spawn point for the level
     * @param x the x position
     * @param y the y position
     */
    public void setSpawn(float x, float y)
    {
        spawnLocation[0] = x;
        spawnLocation[1] = y;
    }

    /**
     * Gets the spawn location of the level
     * @return the spawn co-ordinates [X,Y]
     */
    public float[] getSpawn()
    {
        return spawnLocation;
    }

    /**
     * Add an enemy to the level
     * @param newEnemy the new enemy
     */
    public void addEnemy(Enemy newEnemy)
    {
        enemies.add(newEnemy);
    }

    /**
     * Adds a checkpoint to the level
     * @param newCheckpoint the new checkpoint
     */
    public void addCheckpoint(Checkpoint newCheckpoint)
    {
        checkpoints.addCheckpoint(newCheckpoint);
    }

    /**
     * Adds a new platform to the level
     * @param newPlatform the new platform
     */
    public void addPlatform(Platform newPlatform)
    {
        platforms.add(newPlatform);
    }

    /**
     * Adds a game object to the level
     * @param object the game object
     */
    public void addMisc(GameObject object)
    {
        miscObjects.add(object);
    }

    /**
     * Gets all objects in this level as one massive list
     * @return the list of all objects in the level
     */
    public ArrayList<GameObject> getAllObjects()
    {
        ArrayList<GameObject> objects = new ArrayList<>();
        objects.addAll(checkpoints.getCheckpoints());
        objects.addAll(platforms);
        objects.addAll(enemies);
        objects.addAll(miscObjects);
        objects.add(player);
        return objects;
    }

    /**
     * Gets the level name
     * @return the level name
     */
    public String getLevelName()
    {
        return levelName;
    }

    /**
     * Gets the character
     * @return the character class
     */
    public Character getCharacter()
    {
        return player;
    }

    /**
     * Gets the traps and enemies of the level
     * @return the enemies of the level
     */
    public List<Enemy> getEnemies()
    {
        return enemies;
    }

    /**
     * Gets the collection of checkpoints in this level
     * @return the checkpoint collection
     */
    public CheckpointCollection getCheckpointCollection()
    {
        return checkpoints;
    }

    /**
     * Gets all platforms in the level
     * @return the list of platforms
     */
    public List<Platform> getPlatforms()
    {
        return platforms;
    }

    /**
     * Gets the list of other objects in the level
     * @return the list of other objects
     */
    public List<GameObject> getMiscObjects()
    {
        return miscObjects;
    }

    /**
     * Gets the requirements for the level
     * @return the level's requirements
     */
    public List<TaskRequirement> getRequirements()
    {
        return requirements;
    }

    /**
     * Gets the file name of the level
     * @return the file name of the level
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * Return the level to its original values
     */
    public void reset()
    {
        player.setPosition(getSpawn()[0], getSpawn()[1]);
        player.getInventory().clear();
        player.setSafePos(player.getX(), player.getY());
        player.setLives(3, null);
        for (Checkpoint checkpoint : checkpoints.getCheckpoints())
        {
            checkpoint.setActive(false);
        }
        for (GameObject object : getMiscObjects())
        {
            if(object instanceof Generator)
            {
                Generator generator = (Generator) object;
                generator.reset();
            }
        }
    }
}
