package levels;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class for reading text files
 */
public class GameFileReader
{
    private ArrayList<File> files;  // Files found in directory
    private String directory;       // The directory path

    public GameFileReader(String directory)
    {
        this.directory = directory;
        files = new ArrayList<>();
        getFileData();
    }

    /**
     * Refreshes the list of files in the directory
     */
    public void refreshFileList()
    {
        files.clear();
        getFileData();
    }

    /**
     * Finds all files in the directory
     */
    private void getFileData()
    {
        File levelDirectory = new File(directory);
        for (File levelFile : levelDirectory.listFiles())
        {
            if(!levelFile.isDirectory())
            {
                files.add(levelFile);
            }
            files.sort(new Comparator<File>()
            {
                @Override
                public int compare(File o1, File o2)
                {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    /**
     * Gets a list of all files in the directory
     * @return the list of files in a directory
     */
    public ArrayList<File> getFiles()
    {
        return files;
    }

    /**
     * Gets a file given the index
     * @param index the index of the file
     * @return the file, or null if not found
     */
    public File getFileFromIndex(int index)
    {
        if(index >= 0 && index < files.size())
        {
            return files.get(index);
        }
        return null;
    }

    /**
     * Gets a list of text lines in a file
     * @param file the file to get the text from
     * @return a list of lines in the file
     */
    public List<String> getFileLines(File file)
    {
        try
        {
            Path pathToFile = Paths.get(file.getPath());
            return Files.readAllLines(pathToFile);
        }
        catch (IOException ex)
        {
            return new ArrayList<>();
        }
    }

    /**
     * Converts a CSV line into a list of strings
     * @param csvLine the CSV line to parse
     * @return a list of CSV attributes
     */
    public List<String> parseLine(String csvLine)
    {
        String[] attributes = csvLine.split(",");
        List<String> formattedAttributes = new ArrayList<>();
        String currentAttribute = "";
        boolean insideString = false;
        for (String attribute : attributes)
        {
            if(insideString)
            {
                currentAttribute += "," + attribute;
                if(attribute.endsWith("\""))
                {
                    insideString = false;
                    formattedAttributes.add(currentAttribute.replace("\"", ""));
                    currentAttribute = "";
                }
            }
            else if(attribute.startsWith("\""))
            {
                if(attribute.endsWith("\""))
                {
                    formattedAttributes.add(attribute.replace("\"", ""));
                }
                else
                {
                    currentAttribute += attribute;
                    insideString = true;
                }
            }
            else
            {
                formattedAttributes.add(attribute);
            }
        }
        return formattedAttributes;
    }
}
