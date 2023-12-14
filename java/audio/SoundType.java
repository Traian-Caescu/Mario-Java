package audio;

/**
 * An enum for making passing audio files easier
 */
public enum SoundType
{
    JUMP("/soundeffects/jump.wav"),
    WALK("/soundeffects/HeavyBONK.wav"),
    HURTFROMTRAP("/soundeffects/triggeredTrap.wav"),
    HURTFROMBULLET("/soundeffects/triggeredProjectile.wav"),
    DEFAULT_BACKGROUND("/soundeffects/02 Crazy Dave (Intro Theme).wav"),
    GENERATOR_DEACTIVATED("/soundeffects/generator.wav"),
    POWERUP_ACTIVATED("/soundeffects/powerUp.wav");

    private String audioPath;   // The path to the audio file the enum represents

    /**
     * Creates the enum with the given path
     * @param soundPath the audio's file path
     */
    private SoundType(String soundPath)
    {
        audioPath = soundPath;
    }

    /**
     * Gets the audio file's path
     * @return the path to the audio file
     */
    public String getAudioPath()
    {
        return audioPath;
    }
}
