package uielements.textoverlay;

/**
 * Represents an item to be displayed in a MessageBoard
 */
public class MessageItem
{
    private String title;   // The title of the messageBoard
    private String body;    // The text body of the message

    /**
     * Creates a MessageBoard item
     * @param title the title text of the board
     * @param body the body text of the board
     */
    public MessageItem(String title, String body)
    {
        this.title = title;
        this.body = body;
    }

    /**
     * Gets the title of the MessageBoard
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the body text of the MessageBoard
     * @return the body text
     */
    public String getBody()
    {
        return body;
    }
}
