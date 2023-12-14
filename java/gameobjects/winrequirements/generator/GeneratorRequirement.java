package gameobjects.winrequirements.generator;

import game.Game;
import gameobjects.GameObject;
import gameobjects.winrequirements.TaskRequirement;
import levels.level.Level;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a task where the player has to disable generators
 */
public class GeneratorRequirement implements TaskRequirement
{
    @Override
    public boolean isComplete(Game game)
    {
        return (getTotalCogsRequired(game.getCurrentLevel()) == 0);
    }

    /**
     * Gets a list of generators in a level
     * @param level the level to get generators from
     * @return the list of generators
     */
    public List<Generator> getGenerators(Level level)
    {
        List<Generator> generators = new ArrayList<>();
        for (GameObject object : level.getMiscObjects())
        {
            if(object instanceof Generator)
            {
                generators.add((Generator) object);
            }
        }
        return generators;
    }

    /**
     * Gets the total cogs required in a level
     * @param level the level to get the cogs for
     * @return the amount of cogs required in the level
     */
    public int getTotalCogsRequired(Level level)
    {
        List<Generator> generators = getGenerators(level);
        int cogsRequired = 0;
        for (Generator generator : generators)
        {
            cogsRequired += generator.getCogsRequired();
        }
        return cogsRequired;
    }

    @Override
    public String getDescription(Game game)
    {
        int cogsRequired = getTotalCogsRequired(game.getCurrentLevel());
        return "Cogs for repairs: " + cogsRequired;
    }
}
