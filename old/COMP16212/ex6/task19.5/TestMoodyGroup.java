public class TestMoodyGroup
{
  public static void main(String[] args)
  {
    /* creating a group of teenagers to test they all respond to setHappy*/
    MoodyGroup<Teenager> teenagers = new MoodyGroup<Teenager>();
    
    /* the teenager ben's reference is used in second half*/
    Teenager teenager = new Teenager("Ben");
    teenagers.addPerson(teenager);
    teenagers.addPerson(new Teenager("Percy"));
    teenagers.addPerson(new Teenager("Harry"));
    teenagers.addPerson(new Teenager("Ron"));
    
    teenagers.setHappy(false);
    System.out.println(teenagers);
    teenagers.setHappy(true);
    System.out.println(teenagers);
    
    /* creating a group of workers and ben and testing both groups with
     * setHappy */
    MoodyGroup<MoodyPerson> otherGroup = new MoodyGroup<MoodyPerson>();
    
    otherGroup.addPerson(new Worker("Terry"));
    otherGroup.addPerson(new Worker("Billy"));
    otherGroup.addPerson(new Worker("Francine"));
    otherGroup.addPerson(new Worker("Grace"));
    otherGroup.addPerson(teenager);
    
    otherGroup.setHappy(true);
    System.out.println(otherGroup);
    otherGroup.setHappy(false);
    System.out.println(otherGroup);
    System.out.println(teenagers);
  }
}
