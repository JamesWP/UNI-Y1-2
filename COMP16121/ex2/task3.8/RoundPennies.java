public class RoundPennies
{
  public static void main(String[] args)
  {
    int pennies = Integer.parseInt(args[0]);
    System.out.println(pennies + " pennies is about " + (pennies + 50)/100 + " pounds" );
  }
}