package levels.story;

import levels.GameFileReader;
import uielements.textoverlay.MessageItem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a story from a CSV file
 */
public class StoryReader extends GameFileReader
{
    private static StoryReader reader;      // The singular instance of this class
    private ArrayList<Story> activeStory;   // The stories for the current level

    private StoryReader()
    {
        super("gamelevels/story/");
        activeStory = new ArrayList<>();
    }

    /**
     * Gets the only instance of this reader allowed
     * @return the instance of the class
     */
    public static StoryReader getInstance()
    {
        if(reader != null)
        {
            return reader;
        }
        reader = new StoryReader();
        return reader;
    }

    /**
     * Gets a story from the index
     * @param index the index of the story
     * @return the story or null if it wasn't found
     */
    private ArrayList<Story> getStoriesFromIndex(int index)
    {
        String indexString = index + "";
        ArrayList<Story> stories = new ArrayList<>();
        for (File storyFile : getFiles())
        {
            String[] fileName = storyFile.getName().split(".csv")[0].split("-");
            if(fileName[0].equals(indexString))
            {
                stories.add(parseStory(storyFile));
            }
        }
        return stories;
    }

    /**
     * Converts a file to a usable story
     * @param storyFile the csv file with the story in it
     * @return the story object made from the file
     */
    private Story parseStory(File storyFile)
    {
        String[] fileName = storyFile.getName().split(".csv")[0].split("-");
        StoryTime time = StoryTime.valueOf(fileName[1].toUpperCase());
        int index = Integer.parseInt(fileName[0]);
        Story newStory = new Story(time, index);
        for (String row : getFileLines(storyFile))
        {
            newStory.addDialogue(parseRow(row));
        }
        return newStory;
    }

    /**
     * Gets the story from a file at the given index
     * @param index the index of the story file
     * @return the story or null if it wasn't found
     */
    public Story getStoryFromIndex(int index)
    {
        File storyFile = getFileFromIndex(index);
        if(storyFile != null)
        {
            return parseStory(storyFile);
        }
        return null;
    }

    /**
     * Converts a row of a story CSV to a single dialogue
     * @param csvRow the row in the CSV file
     * @return the MessageBoard-ready object
     */
    private MessageItem parseRow(String csvRow)
    {
        List<String> segments = parseLine(csvRow);
        MessageItem message = new MessageItem(segments.get(0), segments.get(1));
        return message;
    }

    /**
     * Sets the stories for the current level
     * @param levelIndex the index of the current level
     * @return the active story set
     */
    public ArrayList<Story> setActiveStory(int levelIndex)
    {
        activeStory = getStoriesFromIndex(levelIndex);
        return activeStory;
    }

    /**
     * Gets the currently active stories for the level
     * @return the active stories
     */
    public ArrayList<Story> getActiveStory()
    {
        return activeStory;
    }


}
