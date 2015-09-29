import java.io.IOException;
public class CheckSum
{
  public static void main(String[] args)
  {
    try
    {
      int checkSum = 0;
      int curentByte;
      // read byte from file and check for last byte
      while((curentByte=System.in.read())!=-1)
      {
        // work out new msb from curent lsb
        int msb = checkSum%2==0?0:0x8000;
        // shift checksum right by one
        checkSum = checkSum >> 1;
        // add the most significant bit and the curent byte
        checkSum += msb + curentByte;
        // bitmask to 16 bit integer
        checkSum = checkSum & 0xFFFF;
      }
      // print result
      System.out.printf("%05d\n",checkSum);
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
}
