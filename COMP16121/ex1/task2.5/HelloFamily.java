import java.util.*;
public class HelloFamily
{
  public static void main(String[] args)
  {
    ArrayList<String> names = new ArrayList<String>();
    names.add("Jane");
    names.add("Joyce");
    names.add("Wiamina");
    names.add("Wilfred");
    names.add("Me");
    names.add("Ruth");
    names.add("Roy");
    names.add("Tony");
  
    Collections.sort(names);

    for(String name:names)
    {
      System.out.println("Hello " + name + "!");
    }
  }
}