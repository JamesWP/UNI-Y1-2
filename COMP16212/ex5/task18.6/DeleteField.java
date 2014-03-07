import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

public class DeleteField
{
  public static void main(String args[])
  {
    BufferedReader input = null;
    PrintWriter output = null;
    try
    {
      // parse arguments from args 
      // param 1 is input file or "-" for stdin
      // param 2 is the output file or - for stdout
      // param 3 is the field to delete from the input 

      if (args.length!=3)
        throw new IllegalArgumentException(
                          "you must provide only three params");
      input = new BufferedReader(args[0].equals("-")
                      ?new InputStreamReader(System.in)
                      :new FileReader(new File(args[0])));
      int deleteFieldNo = Integer.parseInt(args[2]);
      if(args[1].equals("-"))
        output = new PrintWriter(System.out);
      else
        output = new PrintWriter(args[1]);

      /**
       * for each output line:
       *  for each field.
       *    (1) if field is not deleted field: print field
       *        else dont print field
       *    (2) if inbetween two fields print \t otherwise if last print \n
       */
      String curentLine = null;
      while((curentLine=input.readLine())!=null)
      {
        String[] fields = curentLine.split("\t");
        for (int fieldNo = 0;fieldNo<fields.length;fieldNo++)
        {
          if (fieldNo+1!=deleteFieldNo) // (1)
            output.print(fields[fieldNo]);
          output.print(fieldNo+1!=fields.length // (2)
              ?fieldNo==0&&deleteFieldNo==1
                ?""
                :"\t"
              :"\n");
        }
      }
    } catch (IOException e) { System.out.println("Error reading"); }
    finally
    {
      try
      { 
        if(input != null) input.close();
        if(output != null) output.close();
      }
      catch (Exception e) {}
    }
  }
}
