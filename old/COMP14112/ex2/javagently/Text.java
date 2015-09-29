package javagently;

import java.io.*;
import java.util.*;
import java.text.*;

public class Text {

  public Text () {};

  /* The All New Famous Text class     by J M Bishop  Aug 1996
   *            revised for Java 1.1 by Alwyn Moolman Aug 1997
   *            revised for efficiency by J M Bishop Dec 1997
   *            revised with renamed output methods
   *              by J M Bishop in July 1999
   * 
   * Provides simple input from the keyboard and files.
   * Now also has simple output formatting methods
   * and file opening facilities.
   *
   * public static void   prompt (String s)
   * public static int    readInt (BufferedReader in)
   * public static double readDouble (BufferedReader in)  
   * public static String readString (BufferedReader in)  
   * public static char   readChar (BufferedReader in) 
   * public static String writeInt (int number, int align)
   * public static String writeDouble 
                   (double number, int align, int frac)
   * public static BufferedReader open (InputStream in)
   * public static BufferedReader open (String filename)
   * public static PrintWriter create (String filename)
   */

  private static StringTokenizer T;
  private static String S;

  public static BufferedReader open (InputStream in)  {
    return new BufferedReader(new InputStreamReader(in));
  }

  public static BufferedReader open (String filename) 
	throws FileNotFoundException {
    return new BufferedReader (new FileReader (filename));
  }
  
  public static PrintWriter create 
	 (String filename) throws IOException {
    return new PrintWriter (new FileWriter (filename));
  }

  public static void prompt (String s) {
    System.out.print(s + " ");
    System.out.flush();
  }

  public static int readInt (BufferedReader in) throws IOException {
      if (T==null) refresh(in);
      while (true) {
        try {
          return Integer.parseInt(T.nextToken());
        }
        catch (NoSuchElementException e1) {
          refresh (in);
        }
        catch (NumberFormatException e2) {
          System.out.println("Error in number, try again.");
        }
      }
   }

 public static char readChar (BufferedReader in) throws IOException {
      if (T==null) refresh(in);
      while (true) {
        try {
          return T.nextToken().trim().charAt(0);
        }
        catch (NoSuchElementException e1) {
          refresh (in);
        }
      }
   }

 public static double readDouble (BufferedReader in) throws IOException {
      if (T==null) refresh(in);
      while (true) {
        try {
          String item = T.nextToken();
          return Double.valueOf(item.trim()).doubleValue();
        }
        catch (NoSuchElementException e1) {
          refresh (in);
        }
        catch (NumberFormatException e2) {
          System.out.println("Error in number, try again.");
        }
      }
   }

  public static String readString (BufferedReader in) throws IOException {
    if (T==null) refresh (in);
    while (true) {
      try {
        return T.nextToken();
      }
      catch (NoSuchElementException e1) {
        refresh (in);
      }
    }
  }

  private static void refresh (BufferedReader in) throws IOException {
    S = in.readLine ();
    if (S==null) throw new EOFException();
    T = new StringTokenizer (S);
  }

  //  Format methods
  //  --------------

  private static DecimalFormat N = new DecimalFormat();
  private static final String spaces = "                    ";
  
  public static String format(double number, int align, int frac) {
    N.setGroupingUsed(false);
	N.setMaximumFractionDigits(frac);
	N.setMinimumFractionDigits(frac);
	String num = N.format(number);
	if (num.length() < align)
	   num = spaces.substring(0,align-num.length()) + num;
    return num;
  }
  
  public static String format(int number, int align) {
    N.setGroupingUsed(false);
    N.setMaximumFractionDigits(0);
    String num = N.format(number);
    if (num.length() < align)
       num = spaces.substring(0,align-num.length()) + num;
    return num;
  }

  public static String writeDouble (double number, int align, int frac) {
    // Deprecated in July 1999 but retained for compatability
	return format (number, align, frac);
  }
  
  public static String writeInt (int number, int align) {
    // Deprecated in July 1999 but retained for compatability
  	return format (number, align);
  }
}
