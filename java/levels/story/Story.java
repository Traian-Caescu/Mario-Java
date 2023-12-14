package levels.story;

import uielements.textoverlay.MessageItem;

import java.util.ArrayList;

public class Story
{
    private ArrayList<MessageItem> dialogue;
    private StoryTime time;
    private int levelIndex;

    public Story(StoryTime timeOfStory, int level)
    {
        dialogue = new ArrayList<>();
        time = timeOfStory;
        levelIndex = level;
    }

    public void addDialogue(String speaker, String text)
    {
        dialogue.add(new MessageItem(speaker, text));
    }

    public void addDialogue(MessageItem newDialogue)
    {
        dialogue.add(newDialogue);
    }

    public StoryTime getStoryTime()
    {
        return time;
    }

    public int getLevelIndex()
    {
        return levelIndex;
    }

    public ArrayList<MessageItem> getDialogue()
    {
        return dialogue;
    }
}
