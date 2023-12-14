package uielements.textoverlay;

import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Clickable;
import org.jsfml.graphics.*;
import timer.TimedItem;
import timer.TimerListener;
import timer.TimerPriority;
import uielements.Overlay;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * An object to be drawn on screen which shows a message
 */
public class MessageBoard extends Overlay implements Clickable, TimerListener
{
    private ArrayList<MessageItem> messages;                    // Messages to be displayed
    private String text = "";                                   // Current body text to display
    private String currentTextDisplayed = "";                   // Currently displayed text (for animating main body text appearing)
    private Text titleText;                                     // The title of the message
    private Text mainBodyText;                                  // The text in the main section of the message
    private Sprite background;                                  // The background sprite used to draw the message board
    private static final int[] TITLE_OFFSET = new int[]{7, 2};  // The offset of the title text from the position of the background sprite
    private static final int[] BODY_OFFSET = new int[]{15, 49}; // The offset of the body text from the position of the background sprite
    private Font textFont;                                      // The font of the text
    private boolean changeMessage = false;                      // True if next message should be loaded

    /**
     * Creates a new Message at coords (X,Y) on screen
     *
     * @param x the x position
     * @param y the y position
     */
    public MessageBoard(float x, float y)
    {
        super(x, y);
        InputStream fontStream = MessageBoard.class.getClassLoader().getResourceAsStream("fonts/Arimo.ttf");
        try {
            textFont = new Font();
            textFont.loadFromStream(fontStream);
            fontStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        background = new Sprite();
        setBackground("/textOverlay.png");
        titleText = new Text();
        titleText.setFont(textFont);
        titleText.setColor(Color.BLACK);
        titleText.setCharacterSize(32);
        titleText.setOrigin(0, 0);
        mainBodyText = new Text();
        mainBodyText.setFont(textFont);
        mainBodyText.setCharacterSize(28);
        mainBodyText.setOrigin(0, 0);
        mainBodyText.setColor(Color.BLACK);
        messages = new ArrayList<>();
        setPosition(x, y);
    }

    /**
     * Add message item to board
     * @param item the item to add
     */
    public void addMessage(MessageItem item)
    {
        messages.add(item);
    }

    /**
     * Load the next message into the board if there is one
     */
    private void loadNextMessage()
    {
        if(messages.size() > 0)
        {
            currentTextDisplayed = "";
            MessageItem item = messages.get(0);
            messages.remove(0);
            titleText.setString(item.getTitle());
            text = formatText(item.getBody());
            startTimer();
        }
        changeMessage = false;
    }

    /**
     * Sets the background image for the message box
     * @param filePath the file path of the image
     * @return true if it was loaded successfully, false otherwise
     */
    public boolean setBackground(String filePath)
    {
        Texture newTexture = new Texture();
        try
        {
            InputStream imageFile = GameObject.class.getResourceAsStream(filePath);
            newTexture.loadFromStream(imageFile);
            background = new Sprite(newTexture);
            imageFile.close();
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    /**
     * Gets if the main body text has finished animating
     * @return true if it has, false if it is still going
     */
    public boolean finishedDisplaying()
    {
        return (isDisplaying() && messages.size() == 0 && text.equals(""));
    }

    @Override
    public void onClick(Game game)
    {
        if(finishedDisplaying())
        {
            hide(game);
        }
        else if(changeMessage)
        {
            loadNextMessage();
        }
        else
        {
            Game.getTimer().remove(this);
            currentTextDisplayed += text;
            text = "";
            mainBodyText.setString(currentTextDisplayed);
            game.refreshScreen();
            changeMessage = true;
        }
    }

    /**
     * Gets messages remaining in the message board
     * @return the list of items left to show
     */
    public ArrayList<MessageItem> getMessages()
    {
        return messages;
    }

    /**
     * Starts the timer/drawing of animating characters onto the board
     */
    private void startTimer()
    {
        TimedItem textShower = Game.getTimer().schedule(this, 50f, true, true);
        textShower.setPriority(TimerPriority.HIGH);
    }

    @Override
    protected void showOverlay(Game game)
    {
        game.addDrawable(this);
        loadNextMessage();
    }

    @Override
    protected void hideOverlay(Game game)
    {
        Game.getTimer().remove(this);
        game.removeDrawable(this);
        game.closeOverlay();
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        background.draw(renderTarget, renderStates);
        mainBodyText.draw(renderTarget, renderStates);
        titleText.draw(renderTarget, renderStates);
    }

    @Override
    protected void setPosOnScreen(float x, float y)
    {
        background.setPosition(x, y);
        titleText.setPosition(x + TITLE_OFFSET[0], y + TITLE_OFFSET[1]);
        mainBodyText.setPosition(x + BODY_OFFSET[0], y + BODY_OFFSET[1]);
    }

    @Override
    public void onTick(Game game)
    {
        int length = 1;
        if(text.length() < length)
        {
            length = text.length();
        }
        if(length > 0)
        {
            currentTextDisplayed += text.substring(0, length);
            text = text.substring(length);
            mainBodyText.setString(currentTextDisplayed);
        }
        else
        {
            currentTextDisplayed += text;
            text = "";
            mainBodyText.setString(currentTextDisplayed);
            Game.getTimer().remove(this);
            changeMessage = true;
        }
    }

    /**
     * Formats a string to fit inside the message board
     * @param unformattedText the unformatted string
     * @return a string that can be displayed inside the MessageBoard box
     */
    private String formatText(String unformattedText)
    {
        StringBuilder builder = new StringBuilder();
        int previousIndex = 0;
        boolean finished = false;
        int increment = 40;
        if(majorityUpper(unformattedText))
        {
            increment = 25;
        }
        for (int i = 0; !finished; i += increment)
        {
            if(previousIndex + increment < unformattedText.length())
            {
                int index = unformattedText.substring(previousIndex, previousIndex + increment).lastIndexOf(" ") + previousIndex;
                builder.append(unformattedText, previousIndex, index);
                builder.append("\n");
                previousIndex = index + 1;
            }
            else
            {
                builder.append(unformattedText, previousIndex, unformattedText.length());
                finished = true;
            }
        }
        return builder.toString();
    }

    private boolean majorityUpper(String text)
    {
        int length = text.length();
        int uppercaseChars = 0;
        for (char letter : text.toCharArray())
        {
            if(Character.isUpperCase(letter))
            {
                uppercaseChars++;
            }
        }
        return (double)uppercaseChars / (double)length >= 0.5;
    }
}
