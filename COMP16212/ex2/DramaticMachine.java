public class DramaticMachine extends Machine
{
  public DramaticMachine(final String name, final int size)
  {
    super(name,size);
  }

  @Override
  public String getType()
  {
    return "Dramatic lottery machine";
  }

  @Override
  public Ball ejectBall()
  {
    if (this.getNoOfBalls() <= 0)
    {
      return null;
    }
    final int randomBallNo = (int)(Math.random() * this.getNoOfBalls());
    
    for(int curentBallIndex = 0;
      curentBallIndex < randomBallNo;
      curentBallIndex++)
    {
      final Ball ball = this.getBall(curentBallIndex);
      ball.flash(1, 2);
    }
    final Ball ball = this.getBall(randomBallNo);
    this.swapBalls(randomBallNo, this.getNoOfBalls() - 1);
    this.removeBall();
    return ball;
  }

  @Override
  public void addBall(Ball ball)
  {
    if(ball instanceof MagicBall)
    {
      MagicBall magicBall = (MagicBall) ball;
      magicBall.setMachine(this);
    }
    super.addBall(ball);
  }
}
