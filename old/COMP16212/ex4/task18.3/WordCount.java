import java.io.IOException;

public class WordCount
{
  public static void main(String[] args)
  {
    try
    {
      int curentByte,wordCount=0;
      // read byte from file and check for last byte
      while((curentByte=System.in.read())!=-1)
      {
        // count word at next whitespace char
        char curentChar = (char) curentByte; 
        if(Character.isWhitespace(curentChar))
        {
          wordCount++;
          // skip to next non ws char return if found one
          if(!skipToNextChar())
            // no char found so break loop EOF
            break;
        }
      }
      // print result
      System.out.println(wordCount);
    }
    catch(IOException exc)
    {
      System.err.println(exc);
    }
    finally
    { 
      try { System.in.close(); }
      catch(IOException exc) { System.out.println("Exception:" + exc); }
    }
  }

  /**
   * skips whitespace characters on stdin till next non-whitespace characheter
   * @return if a whitespace char was found or false if not (EOF)
   */
  public static boolean skipToNextChar() throws IOException
  {
    int nextByte;
    while((nextByte=System.in.read())!=-1)
    {
      if(!Character.isWhitespace((char) nextByte))
        return true;
    }
    // no non ws char found return false
    return false;
  }
}
