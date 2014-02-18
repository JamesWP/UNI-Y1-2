import java.awt.Color;

public class MagicBall extends Ball
{
  private int curentCountingNo = 0;
  private State curentState;
  private Ball nextBall,previousBall;
  private DramaticMachine machine;
  private Type myType = Type.getDefaultType();
  public MagicBall(final int value, final Color color)
  {
    super(value,color);
    curentState = State.getDefaultState();
  }

  public void setType(Type type)
  {
    this.myType = type;
  }

  public void setNext(Ball nextBall)
  {
    this.nextBall = nextBall;
  }

  public void setPrevious(Ball previousBall)
  {
    this.previousBall = previousBall;
  }

  public void setMachine(DramaticMachine curentMachine)
  {
    this.machine = curentMachine;
  }

  public void doMagic(int spellNumber)
  {
    switch (spellNumber)
    {
      case 3:
        switch(myType)
        {
          case CAHOS:
            // chaos breaks due to stack overflow
            if(this.nextBall!=null&&this.nextBall instanceof MagicBall)
              ((MagicBall)(this.nextBall)).doMagic(3);
            if(this.previousBall!=null)
              ((MagicBall)(this.previousBall)).doMagic(3);
            this.curentState = curentState.getNextState();
            break;
          case SWAP:
            // swap ball
            if(machine!=null)
            {
              int myPosition = whereIs(machine);
              int randomPosition = (int)(Math.random()*(machine.getNoOfBalls()-1));
              machine.swapBalls(myPosition,randomPosition);
            }
            break;
        }
        break;
      case 1:
        this.curentState = curentState.getNextState();
        break;
      case 2:
        this.curentState = State.getDefaultState();
        break;
      default:
        break;
    }
    getImage().update();
  }

  public int whereIs(Machine machine)
  {
    for(int index = 0;index<machine.getNoOfBalls();index++)
    {
      if(machine.getBall(index)==this)
        return index;
    }
    return -1;
  }

  public boolean isVisible()
  {
    return curentState!=State.INVISIBLE;
  }

  public boolean isFlashing()
  {
    return curentState==State.FLASHING||curentState==State.COUNTING;
  }

  @Override
  public int getValue()
  {
    if(curentState==State.COUNTING)
    {
      if(curentCountingNo>99)
        curentCountingNo = 1;
      return curentCountingNo++; 
    }else{
      return super.getValue();
    }
  }

  @Override
  public BallImage makeImage()
  {
    return new MagicBallImage(this);
  }
  public static enum State
  {
    NORMAL,
    INVISIBLE,
    FLASHING,
    COUNTING;

    public static State getDefaultState()
    {
      return State.NORMAL;
    }

    public State getNextState()
    {
      switch (this)
      {
        case NORMAL:
          return INVISIBLE;
        case INVISIBLE:
          return FLASHING;
        case FLASHING:
          return COUNTING;
        case COUNTING:
          return NORMAL;
        default:
          return NORMAL;
      }
    }
  }
  public static enum Type
  {
    CAHOS,
    SWAP,
    NORMAL;

    public static Type getDefaultType()
    { return NORMAL; }
  }
}
