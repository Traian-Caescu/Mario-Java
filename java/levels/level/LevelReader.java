package levels.level;

import gameobjects.player.Character;
import gameobjects.Checkpoint;
import gameobjects.platforms.Floor;
import gameobjects.platforms.MovingPlatform;
import gameobjects.platforms.Platform;
import gameobjects.platforms.Wall;
import gameobjects.powerups.Invincibility;
import gameobjects.powerups.JumpBoost;
import gameobjects.powerups.SpeedBoost;
import gameobjects.traps.StaticTrap;
import gameobjects.traps.movingtraps.Robot;
import gameobjects.traps.shootingtraps.Cannon;
import gameobjects.winrequirements.Cog;
import gameobjects.winrequirements.antenna.Antenna;
import gameobjects.winrequirements.antenna.AntennaOff;
import gameobjects.winrequirements.coin.Coin;
import gameobjects.winrequirements.generator.Generator;
import gameobjects.winrequirements.Helicopter;
import levels.GameFileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is used to read CSV files in the /gamelevels/ directory
 * and convert them into Levels ready to be loaded into the game
 */
public class LevelReader extends GameFileReader
{
    private int currentIndex;       // The current index of the level chosen
    Level[] levels;                 // Levels that have been requested so far
    private static LevelReader reader;

    /**
     * Creates a new level reader and finds all the levels
     */
    private LevelReader()
    {
        super("gamelevels/");
        currentIndex = 0;
        levels = new Level[getFiles().size()];
    }

    public static LevelReader getInstance()
    {
        if(reader == null)
        {
            reader = new LevelReader();
        }
        return reader;
    }

    /**
     * Gets the level from the index in the levels array and loads it into a level
     * @param index the index of the level wanted
     * @return the level that has been found or null if it wasn't found
     */
    public Level getLevelFromIndex(int index)
    {
        if(index < levels.length && index >= 0)
        {
            currentIndex = index;
            Level levelFound;
            if(levels[index] == null)
            {
                Level parsedLevel =  csvToLevel(getFileFromIndex(index));
                levels[index] = parsedLevel;
                levelFound = parsedLevel;
            }
            else
            {
                levelFound = levels[index];
            }
            levelFound.reset();
            if(index == 0)
            {
                levelFound.setLocked(false);
            }
            return levelFound;
        }
        return null;
    }

    /**
     * Gets the next level or null if there are no more
     * @return the next level or null
     */
    public Level getNextLevel()
    {
        if(currentIndex + 1 < levels.length)
        {
            currentIndex++;
            return getLevelFromIndex(currentIndex);
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets the current level
     * @return the current level
     */
    public Level getCurrentLevel()
    {
        return getLevelFromIndex(currentIndex);
    }

    /**
     * Converts a CSV File in a filesystem to a level
     * @param levelFile the File that the level is in
     * @return the Level parsed
     */
    private Level csvToLevel(File levelFile)
    {
        List<String> fileContents = getFileLines(levelFile);
        String metadataLine = fileContents.get(0);
        List<String> metadata = parseLine(metadataLine);
        Level foundLevel = new Level(metadata.get(0), levelFile.getName());
        foundLevel.setBackgroundImage(metadata.get(1));
        foundLevel.setSpawn(Integer.parseInt(metadata.get(2)), Integer.parseInt(metadata.get(3)));
        Character newCharacter = new Character(foundLevel.getSpawn()[0], foundLevel.getSpawn()[1], "/character/walk1.png");
        foundLevel.setCharacter(newCharacter);
        fileContents.remove(0);
        Antenna antenna = null;
        for (String item : fileContents)
        {
            LevelItem newItem = parseArray(item);
            if(newItem.getName().equals("Platform"))
            {
                Platform newPlatform = new Platform(newItem.getXPos(), newItem.getYPos());
                newPlatform.setRotation(newItem.getRotation());
                foundLevel.addPlatform(newPlatform);
            }
            else if(newItem.getName().equals("Floor"))
            {
                Floor newFloor = new Floor(newItem.getXPos(), newItem.getYPos());
                newFloor.setRotation(newItem.getRotation());
                foundLevel.addPlatform(newFloor);
            }
            else if(newItem.getName().equals("Wall"))
            {
                Wall newWall = new Wall(newItem.getXPos(), newItem.getYPos());
                newWall.setRotation(newItem.getRotation());
                foundLevel.addPlatform(newWall);
            }
            else if(newItem.getName().contains("spikes"))
            {
                StaticTrap spikes = null;
                if(newItem.getName().equals("1spikes"))
                {
                    spikes = new StaticTrap(newItem.getXPos(), newItem.getYPos(), "/traps/spikes/1spikes.png", 1);
                    spikes.getSprite().setScale(0.5f, 0.5f);
                }
                else if(newItem.getName().equals("3spikes"))
                {
                    spikes = new StaticTrap(newItem.getXPos(), newItem.getYPos(), "/traps/spikes/3spikes.png", 1);
                }
                if(spikes != null)
                {
                    spikes.setRotation(newItem.getRotation());
                    foundLevel.addEnemy(spikes);
                }
            }
            else if(newItem.getName().equals("Cog"))
            {
                Cog newCog = new Cog(newItem.getXPos(), newItem.getYPos());
                newCog.setRotation(newItem.getRotation());
                foundLevel.addMisc(newCog);
            }
            else if(newItem.getName().equals("Coin"))
            {
                Coin coin = new Coin(newItem.getXPos(), newItem.getYPos());
                coin.setRotation(newItem.getRotation());
                foundLevel.addMisc(coin);
            }
            else if(newItem.getName().equals("Cannon"))
            {
                Cannon newCannon = new Cannon(newItem.getXPos(), newItem.getYPos(), 1);
                newCannon.setRotation(newItem.getRotation());
                foundLevel.addEnemy(newCannon);
            }
            else if(newItem.getName().equals("Generator"))
            {
                int cogsRequired = Integer.parseInt(newItem.getExtraInfo().get(0));
                Generator generator = new Generator(newItem.getXPos(), newItem.getYPos(), cogsRequired);
                generator.setRotation(newItem.getRotation());
                foundLevel.addMisc(generator);
            }
            else if(newItem.getName().equals("Checkpoint"))
            {
                Checkpoint newPoint = new Checkpoint(newItem.getXPos(), newItem.getYPos());
                newPoint.setRotation(newItem.getRotation());
                foundLevel.addCheckpoint(newPoint);
            }
            else if(newItem.getName().equals("JumpBoost"))
            {
                JumpBoost jumpBoost = new JumpBoost(newItem.getXPos(), newItem.getYPos(), 10000, newCharacter);
                jumpBoost.setRotation(newItem.getRotation());
                foundLevel.addMisc(jumpBoost);
            }
            else if(newItem.getName().equals("Invincibility"))
            {
                Invincibility invincibility = new Invincibility(newItem.getXPos(), newItem.getYPos(), 10000, newCharacter);
                invincibility.setRotation(newItem.getRotation());
                foundLevel.addMisc(invincibility);
            }
            else if(newItem.getName().equals("SpeedBoost"))
            {
                SpeedBoost speedBoost = new SpeedBoost(newItem.getXPos(), newItem.getYPos(), 5000, newCharacter, 12);
                speedBoost.setRotation(newItem.getRotation());
                foundLevel.addMisc(speedBoost);
            }
            else if(newItem.getName().equals("Helicopter"))
            {
                Helicopter newHeli = new Helicopter(newItem.getXPos(), newItem.getYPos());
                newHeli.setRotation(newItem.getRotation());
                foundLevel.addMisc(newHeli);
            }
            else if(newItem.getName().equals("MovingPlatform"))
            {
                if(newItem.getExtraInfo().size() > 0)
                {
                    MovingPlatform movingPlatform = new MovingPlatform(newItem.getXPos(), newItem.getYPos());
                    float minX = Float.parseFloat(newItem.getExtraInfo().get(0));
                    float maxX = Float.parseFloat(newItem.getExtraInfo().get(1));
                    float xSpeed = Float.parseFloat(newItem.getExtraInfo().get(2));
                    float minY = Float.parseFloat(newItem.getExtraInfo().get(3));
                    float maxY = Float.parseFloat(newItem.getExtraInfo().get(4));
                    float ySpeed = Float.parseFloat(newItem.getExtraInfo().get(5));
                    movingPlatform.setXRange(new float[]{minX, maxX}, xSpeed);
                    movingPlatform.setYRange(new float[]{minY, maxY}, ySpeed);
                    movingPlatform.setRotation(newItem.getRotation());
                    foundLevel.addPlatform(movingPlatform);
                }
            }
            else if(newItem.getName().equals("Robot"))
            {
                float minX = Float.parseFloat(newItem.getExtraInfo().get(0));
                float maxX = Float.parseFloat(newItem.getExtraInfo().get(1));
                float xSpeed = Float.parseFloat(newItem.getExtraInfo().get(2));
                Robot robot = new Robot(newItem.getXPos(), newItem.getYPos(), minX, maxX, xSpeed);
                robot.setRotation(newItem.getRotation());
                foundLevel.addEnemy(robot);
            }
            else if(newItem.getName().equals("ButtonOff"))
            {
                if(antenna != null)
                {
                    AntennaOff buttonOff = new AntennaOff(newItem.getXPos(), newItem.getYPos(), antenna);
                    buttonOff.setRotation(newItem.getRotation());
                    foundLevel.addMisc(buttonOff);
                }

            }
            else if(newItem.getName().equals("Antenna"))
            {
                antenna = new Antenna(newItem.getXPos(), newItem.getYPos());
                antenna.setRotation(newItem.getRotation());
                foundLevel.addMisc(antenna);
            }
        }
        return foundLevel;
    }

    /**
     * Converts a CSV line into a level
     * @param levelString the level's CSV string
     * @return the level parsed
     */
    private LevelItem parseArray(String levelString)
    {
        List<String> formattedAttributes = parseLine(levelString);
        List<String> extraInfo;
        if(formattedAttributes.size() > 4)
        {
            extraInfo = formattedAttributes.subList(5, formattedAttributes.size());
        }
        else
        {
            extraInfo = new ArrayList<>();
        }
        float xPos = Float.parseFloat(formattedAttributes.get(0));
        float yPos = Float.parseFloat(formattedAttributes.get(1));
        String objectName = formattedAttributes.get(2);
        double scale = Double.parseDouble(formattedAttributes.get(3));
        int rotation = Integer.parseInt(formattedAttributes.get(4));
        return new LevelItem(xPos, yPos, objectName, scale, rotation, extraInfo);
    }

    /**
     * Gets how many levels there are
     * @return the total amount of levels
     */
    public int getLevelCount()
    {
        return levels.length;
    }

    public int getCurrentIndex()
    {
        return currentIndex;
    }
}
