public class FahrenheitToCelsius
{
  public static void main(String[] args)
  {
    double fahrenheit = Double.parseDouble(args[0]);
    System.out.println("F" + fahrenheit + " = C" + (fahrenheit-32)*5/9);
  }
}