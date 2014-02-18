public class DramaticGame extends Game
{
  public DramaticGame(final String name, final int size,
                      final String rackName, final int rackSize)
  {
    super(name,size,rackName,rackSize);
  }
  
  public Machine makeMachine(final String name, final int size)
  {
    return new DramaticMachine(name, size);
  }
}
