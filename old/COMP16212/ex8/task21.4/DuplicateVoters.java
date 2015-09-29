import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * this program parses an input file and checks for duplicates using a set to
 * find any voters that already exist in the file
 */
public class DuplicateVoters
{
  public static void main(String[] args)
  {
    BufferedReader input = null;
    PrintWriter output = null;
    try
    {
      /*
      parse arguments and open files
       */
      if (args.length != 2)
        throw new IllegalArgumentException("you must supply exactly two " +
                                           "arguments");
      input = new BufferedReader(new FileReader(args[0]));
      output = new PrintWriter(new File(args[1]));

      Set<String> voters = new HashSet<String>();
      String voterName, voterVote;
      int totalDuplicates = 0;
      /*
      read the input two lines at a time...
      count all duplicate entries using a set
       */
      while ((voterName = input.readLine()) != null
             && (voterVote = input.readLine()) != null)
      {
        // if this evaluates true then the voter is a duplicate
        if (!voters.add(voterName))
        {
          output.println(voterName);
          totalDuplicates++;
        }
      }
      output.println("There were " + totalDuplicates + " duplicates in" +
                            " " +
                         "the file.");
      System.out.println("File successfully read " +totalDuplicates +" " +
                         "duplicates");
    } catch (IOException fileError)
    {
      System.out.println("There was an error reading / writing files:" +
                         fileError.getMessage());
    } catch (IllegalArgumentException argError)
    {
      System.out.println("Argument error:" + argError.getMessage());
    } finally
    {
      // close any open handles to files
      if (input != null) try { input.close(); } catch (IOException err)
      {System.out.println("Error closing input file");}
      if (output != null) output.close();
    }
  }
}