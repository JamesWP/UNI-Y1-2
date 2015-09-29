import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
      read the input file line by line into the address treeset
      this implicityly sorts them into order
       */
      Set<DeliveryHouseDetails> addresses
          = new TreeSet<DeliveryHouseDetails>();
      String line;
      while ((line = input.readLine()) != null)
      {
        addresses.add(new DeliveryHouseDetails(line));
      }
      /*
      iterate through the items by using the iterator this orders them in the
       order they are n the set which is there natural ordering
      */
      Iterator<DeliveryHouseDetails> iterator = addresses.iterator();
      while (iterator.hasNext())
        output.println(iterator.next());
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
