package audio;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class to make playing sounds easier
 */
public class SoundPlayer
{
    private Sound sound;        // The sound that should be played
    private SoundBuffer buffer; // The buffer that the sound is read into

    /**
     * Creates a new sound player with volume of 20
     */
    public SoundPlayer()
    {
        sound = new Sound();
        buffer = new SoundBuffer();
    }

    /**
     * Plays the audio linked to the enum passed
     * @param type the enum of the audio file you want to play
     * @return true if it was able to play, false if the file wasn't found
     */
    public boolean play(SoundType type)
    {
        InputStream source = SoundPlayer.class.getResourceAsStream(type.getAudioPath());
        try
        {
            buffer.loadFromStream(source);
            sound.setBuffer(buffer);
            sound.play();
            source.close();
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    /**
     * Sets the volume of the player
     * @param volume the volume (0 <= x <= 100)
     */
    public void setVolume(int volume)
    {
        sound.setVolume(volume);
    }

    /**
     * Sets if the audio should loop forever
     * @param loop true if it should repeat, false otherwise
     */
    public void loop(boolean loop)
    {
        sound.setLoop(loop);
    }

    /**
     * Stops the audio playing
     */
    public void stop()
    {
        sound.stop();
    }
}
