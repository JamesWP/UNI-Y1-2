public class FieldPerimeter
{
  public static void main(String[] args)
  {
    int width = Integer.parseInt(args[0]);
    int height = Integer.parseInt(args[1]);
    int perimeter = width + width + height + height;
    System.out.println("Total fence required: " + perimeter);
  }
}