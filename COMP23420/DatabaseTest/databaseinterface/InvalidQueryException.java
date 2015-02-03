
/**
 * This exception will be thrown if you attempt to make an invalid
 * query on the database such as trying to access data with an invalid
 * id. If you get this exception it means there's a bug in your code.
 */

public class InvalidQueryException extends RuntimeException
{

  /**
   * Create an InvalidQueryException
   * @param problem A string which tries to explain the problem which can
   * be accessed using getMessage on the exception object when it's caught.
   */
  public InvalidQueryException(String problem)
  {
    super(problem);
  }

}
