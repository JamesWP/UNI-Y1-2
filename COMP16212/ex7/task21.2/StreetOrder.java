import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by james on 21/03/2014.
 * <p/>
 * StreetOrder takes a file of ordered house names and prints them in
 * "delivery order" by first  printing the even numbers from the input then
 * reversing the input and printing the odd house numbers
 * <p/>
 * usage java StreetOrder <input file name > <output file name>
 */
public class StreetOrder
{
  public static void main(String[] args)
  {
    PrintWriter output = null;
    BufferedReader input = null;
    try
    {
      if (args.length != 2)
        throw new IllegalArgumentException("you must provide two filenames " +
                                                   "<input> <output>");
      output = new PrintWriter(args[1]);
      input = new BufferedReader(new FileReader(args[0]));

      /*
      read the input file line by line into the unsortedAddress array
       */
      List<String> unsortedAddresses = new ArrayList<String>();
      String line;
      while ((line = input.readLine()) != null)
      {
        unsortedAddresses.add(line);
      }

      /*
      print the even house numbers as they appear in the input file
      then reverse the array then print the odd house numbers
       */
      for (String address : unsortedAddresses)
      {
        String[] parts = address.split(" ");
        int houseNumber = Integer.parseInt(parts[0]);
        if (houseNumber % 2 == 1) output.println(address);
      }
      Collections.reverse(unsortedAddresses);
      for (String address : unsortedAddresses)
      {
        String[] parts = address.split(" ");
        int houseNumber = Integer.parseInt(parts[0]);
        String name = parts[0];
        if (houseNumber % 2 == 0) output.println(address);
      }
    } catch (IOException error)
    {
      System.err.println("Error reading or writing files. please provide a " +
                                 "readable file in args[0] and a writable " +
                                 "file in args[1]");
      System.err.println(error.getMessage());
    } catch (IllegalArgumentException error)
    {
      System.err.println(error.getMessage());
    } finally
    {
      if (output != null) output.close();
      if (input != null) try {input.close(); } catch (IOException iox) {}
    }
  }
}
