import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;

public class Uuencode
{
  /**
   * prints the uuencode form of the file passed in on args[0].
   * prints the heading of the file
   * constructs the output one line at a time from the file
   * prints out each line and the remaining bytes at the end of the file
   * prints the file ending
   **/
  public static void main(String[] args)
  {
    printHeading(600,args[0]);
    int nextByte = 0;
    int[] line = new int[45];
    int nextIndex = 0;

    try{
      FileInputStream file = new FileInputStream(new File(args[0]));
      while((nextByte = file.read())!=-1)
      {
        line[nextIndex++] = nextByte;
        if (nextIndex==line.length){
          printLine(line);
          nextIndex = 0;
        }
      }
    } catch (IOException e)
    {
      System.out.println("IO Error occurred");
      return;
    }
    printLine(line,nextIndex-1);
    printEOF();
  }

  /**
   * prints the uuencoded version of the (whole) line passed to it. 
   * 
   */
  private static void printLine(int[] line)
  {
    printLine(line,line.length-1); 
  }

  /**
   * prints the uuencoded version of the line passed to it up to the given
   * last index padding the length up to a multiple of three first 
   */
  private static void printLine(int[] line, int lastIndex)
  {
    writeByteAsChar(lastIndex+1);
    switch((lastIndex+1)%3)
    {
      case 1:
        line[lastIndex+2] = 0;
      case 2:
        line[lastIndex+1] = 0;
    }
    for(int grpIndex = 0;grpIndex<=lastIndex;grpIndex+=3)
    {
      writeBytesAsChar(grpIndex,line);
    }
    System.out.println();
  }

  /**
   * prints the end of file 
   *
   */
  private static void printEOF()
  {
    System.out.print("`\nend\n");
  }

  /**
   * prints the heading of the output to stdout
   */
  private static void printHeading(int mode,String filename)
  {
    System.out.printf("begin %3d %s\n",mode,filename);
  }

  /**
   * Write a single result byte as a printable character.
   * Each byte is 6-bit, i.e. range 0..63.
   * Thus adding 32 makes it printable, except for 0 which would become space
   * and so we add 96 instead -- a left single quote (‘).
   */
  private static void writeByteAsChar(int thisByte)
  {
    System.out.print((char) (thisByte==0?96:thisByte+32));
  } // writeByteAsChar

  private static void writeBytesAsChar(int byteGroupIndex,int[] lineBytes)
  {
    // Calculate 4 result bytes from the 3 input bytes.
    int byte1 = lineBytes[byteGroupIndex] >> 2;
    int byte2 = (lineBytes[byteGroupIndex] & 0x3) << 4
                 | (lineBytes[byteGroupIndex + 1] >> 4);
    int byte3 = (lineBytes[byteGroupIndex + 1] & 0xf) << 2
                 | lineBytes[byteGroupIndex + 2] >> 6;
    int byte4 = lineBytes[byteGroupIndex + 2] & 0x3f;
    // Now write those result bytes.
    writeByteAsChar(byte1);
    writeByteAsChar(byte2);
    writeByteAsChar(byte3);
    writeByteAsChar(byte4);
  }
}
