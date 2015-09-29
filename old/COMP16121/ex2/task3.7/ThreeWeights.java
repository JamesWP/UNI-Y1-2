public class ThreeWeights
{
  public static void main(String[] args)
  {
    double weight1 = Double.parseDouble(args[0]);
    double weight2 = Double.parseDouble(args[1]);
    double weight3 = Double.parseDouble(args[2]);

    //1-3
    System.out.println(- weight1 - weight2 - weight3);
    System.out.println(- weight1 - weight2);
    System.out.println(- weight1 - weight2 + weight3);
    //4-6
    System.out.println(- weight1 - weight3);
    System.out.println(- weight1);
    System.out.println(- weight1 + weight3);
    //7-9
    System.out.println(- weight1 - weight3 + weight2);
    System.out.println(- weight1 + weight2);
    System.out.println(- weight1 + weight3 + weight2);


    System.out.println(- weight2 - weight3);
    System.out.println(- weight2);
    System.out.println(- weight2 + weight3);

    System.out.println(- weight3);
    System.out.println(0);
    System.out.println(+ weight3);

    System.out.println(- weight3 + weight2);
    System.out.println(+ weight2);
    System.out.println(+ weight3 + weight2);


    System.out.println(- weight2 - weight3 + weight1);
    System.out.println(- weight2 + weight1);
    System.out.println(- weight2 + weight3 + weight1);

    System.out.println(- weight3 + weight1);
    System.out.println(+ weight1);
    System.out.println(+ weight3 + weight1);

    System.out.println(- weight3 + weight2 + weight1);
    System.out.println(+ weight2 + weight1);
    System.out.println(+ weight3 + weight2 + weight1);
    
  }
}