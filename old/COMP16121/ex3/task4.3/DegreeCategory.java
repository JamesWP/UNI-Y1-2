public class DegreeCategory
{
  public static void main(String[] args)
  {
    double studentMark = Double.parseDouble(args[0]);

    if(studentMark>=70)
      System.out.println("Mark "+ studentMark + " receives "+
                  "Honours, first class");
    else if(studentMark>=60)
      System.out.println("Mark "+ studentMark + " receives "+
                  "Honours, second class, division one");
    else if(studentMark>=50)
      System.out.println("Mark "+ studentMark + " receives "+
                  "Honours, second class, division two");
    else if(studentMark>=40)
      System.out.println("Mark "+ studentMark + " receives "+
                  "Honours, third class");
    else if(studentMark>=32)
      System.out.println("Mark "+ studentMark + " receives "+
                  "Pass / ordinary degree");
    else
      System.out.println("Mark "+ studentMark + " receives "+
                  "Fail");
    
  }
}
