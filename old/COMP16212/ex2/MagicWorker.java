import java.util.ArrayList;
import java.awt.Color;

public class MagicWorker extends Worker
{
  private ArrayList<MagicBall> myBalls = new ArrayList<MagicBall>();
  private MagicBall previousBall;
  private final MagicBall.Type myType;

  public MagicWorker(final String name)
  {
    super(name);
    this.myType = MagicBall.Type.getDefaultType();
  }

  public MagicWorker(final String name,MagicBall.Type workerType)
  {
    super(name);
    this.myType = workerType;
  }

  @Override
  public Ball makeNewBall(int number, Color color)
  {
    // create new 
    MagicBall newBall = new MagicBall(number,color);
    newBall.setType(myType);
    myBalls.add(newBall);
    if(previousBall!=null)
    {
      newBall.setPrevious(previousBall);
      previousBall.setNext(newBall);
    }
    previousBall = newBall;
    return newBall;
  }

  @Override
  public PersonImage makeImage()
  {
    return new MagicWorkerImage(this);
  }

  @Override
  public String getPersonType()
  {
    return "MagicWorker";
  }

  @Override
  public String getClassHierarchy()
  {
    return "MagicWorker>" + super.getClassHierarchy();
  }

  public void doMagic(int spellNumber)
  {
    for(MagicBall ball: myBalls)
    {
      ball.doMagic(spellNumber);
    }
  }
}
