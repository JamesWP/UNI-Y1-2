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
 * "delivery order" by using a compairing method in the DeliveryHouseDetails
 * class
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
      List<DeliveryHouseDetails> addresses = new ArrayList<DeliveryHouseDetails>();
      String line;
      while ((line = input.readLine()) != null)
      {
        addresses.add(new DeliveryHouseDetails(line));
      }

      Collections.sort(addresses);

      for (DeliveryHouseDetails address : addresses)
        output.println(address);
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
