public class FlatterMe
{
  public static void main(String[] args)
  {
    if(args.length!=1) return;
    String name = args[0];
    System.out.println("Congratulations " + name);
    System.out.println(name + " is great!");
    System.out.println("Why, " + name + " your looking fine today! :)");
  }
}