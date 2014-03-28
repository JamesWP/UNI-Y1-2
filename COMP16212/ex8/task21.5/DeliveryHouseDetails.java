/**
 * Created by james on 21/03/2014.
 *
 * an object to reperesent a delivery house address and house number
 */
public class DeliveryHouseDetails implements Comparable<DeliveryHouseDetails>
{

  final private String houseDetails;
  final private int    houseNumber;

  public DeliveryHouseDetails(int houseNumber, String houseName)
  {
    this.houseDetails = houseNumber + " " + houseName;
    this.houseNumber = houseNumber;
  }

  public DeliveryHouseDetails(String fileLine)
  {
    String[] parts = fileLine.split(" ");
    int houseNumber = Integer.parseInt(parts[0]);
    this.houseDetails = fileLine;
    this.houseNumber = houseNumber;
  }

  public boolean isEvenHouseNumber()
  {
    return houseNumber % 2 == 0;
  }

  public String getHouseDetails()
  {
    return houseDetails;
  }

  @Override
  public int compareTo(DeliveryHouseDetails other)
  {
    if (!isEvenHouseNumber() && !other.isEvenHouseNumber())
      return houseNumber - other.houseNumber;
    else if (isEvenHouseNumber() && other.isEvenHouseNumber())
      return other.houseNumber - houseNumber;
    else if (!isEvenHouseNumber())
      return -1;
    else return 1;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof DeliveryHouseDetails)
      return this.compareTo((DeliveryHouseDetails) obj)==0;
    else return super.equals(obj);
  }

  @Override
  public String toString()
  {
    return houseDetails;
  }
}
