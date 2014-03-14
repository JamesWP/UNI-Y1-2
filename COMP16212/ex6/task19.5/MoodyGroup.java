import java.util.ArrayList;
public class MoodyGroup <MoodyType extends MoodyPerson>
{
  /* internal array list to look after adding persons to list*/
  private ArrayList<MoodyType> persons = new ArrayList<MoodyType>(); 

  public void addPerson(MoodyType person)
  {
    persons.add(person);
  }
  
  public void setHappy(boolean happy)
  {
    for (MoodyType person:persons)
    {
      person.setHappy(happy);
    }
  }

  /* prints out each sub person in persons and their hapyness*/
  @Override
  public String toString()
  {
    String result = "";
    for(MoodyType person:persons)
      result+=person+" happy:"+ person.isHappy() +"\n";
    return result;
  }
}
