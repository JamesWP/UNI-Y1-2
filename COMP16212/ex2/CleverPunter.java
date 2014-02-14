import java.awt.Color;

/*
 * A kind of moody person.
 */

public class CleverPunter extends MoodyPerson
{
  // The game which is currently being played.
  private Game currentGame = null;

  // The guess of what balls will come out.
  private int [] currentGuess = null;


  // Constructor is given the person's name.
  public CleverPunter(String name)
  {
    super(name);
  }  // CleverPunter


  // Returns the name of the type of Person.
  public String getPersonType()
  {
    return "Clever Punter";
  } // getPersonType


  // Returns the Person's name, with the current guess included.
  public String getPersonName()
  {
    String result = super.getPersonName();
    if (currentGuess != null && currentGuess.length != 0)
    {
      result += "(guess " + currentGuess[0];
      for (int index = 1; index < currentGuess.length; index++)
        result += "," + currentGuess[index];
      result += ")";
    } // if
    return result;
  } // getPersonName


  // Returns the Person's colour.
  public Color getColour()
  {
    return Color.green;
  } // getColour


  // Returns the Person's current saying.
  public String getCurrentSaying()
  {
    if (currentGame == null)
    {
      setHappy(false);
      return "I need a game to play!";
    } // if
    else
    {
      int noOfMatches = getNoOfMatches();
      int noOfNonMatches
        = currentGame.getRackNoOfBalls() - noOfMatches;
      // Is happy if and only if there are no non-matches.
      setHappy(noOfNonMatches == 0);
      if (noOfMatches == currentGame.getRackSize())
        return "Yippee!! I've won the jackpot!";
      else if (noOfMatches == currentGame.getRackNoOfBalls())
        if (noOfMatches == 0) // I.e. the rack is still empty
          return "I'm excited!";
        else
          return noOfMatches + " matched so far!";
      else
        return "Doh! " + noOfNonMatches + " not matched";
    } // else
  } // getCurrentSaying


  // Helper method to find out how many of the guesses
  // currently match the game rack.
  private int getNoOfMatches()
  {
    int noMatchedSoFar = 0;
    for (int index = 0; index < currentGuess.length; index++)
      if (currentGame.rackContains(currentGuess[index]))
        noMatchedSoFar++;
    return noMatchedSoFar;
  } // getNoOfMatches


  // Set the game being currently played.
  public void setGame(Game requiredGame)
  {
    currentGame = requiredGame;
    currentGuess = new int[currentGame.getRackSize()];
    // An easy way to obtain a guess is to play a mock game!
    Game mockGame = new Game("", currentGame.getMachineSize(),
                             "", currentGame.getRackSize());
    Worker mockWorker = new Worker("");
    mockWorker.fillMachine(mockGame);
    for (int index = 0; index < currentGame.getRackSize(); index++)
      currentGuess[index] = mockGame.ejectBall().getValue();
    getImage().update();
  } // setGame

} // class CleverPunter
