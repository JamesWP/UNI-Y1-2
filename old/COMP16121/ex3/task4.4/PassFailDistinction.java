public class PassFailDistinction
{
  public static void main(String[] args)
  {
    double studentMark = Double.parseDouble(args[0]);
    
    System.out.println("Student mark: " + studentMark);


    if(studentMark >= 50)
      System.out.println("Pass");
    else
      System.out.println("Fail");

    if(studentMark>=70)
      System.out.println("Distinction");

  }
}
