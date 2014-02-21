public class TestRelativeDates
{
  public static void main(String[] args)
  {
    Date startDate;
    int totDays = 365*2;//two years
    
    Date curentDate,tomorowDate,yesterDate,nextMonth,prevMonth,nextYear
          ,prevYear;
    
    try{
      startDate = new Date(1,1,2007);//nb year 2008 is leap year 
      curentDate = new Date(startDate.getDay(),startDate.getMonth()
                                ,startDate.getYear());
    } catch (Exception e){ return; }
    
    for (int days = 0;days<totDays;days++)
    {
      try{
        curentDate  = curentDate.addDay();
        tomorowDate = curentDate.addDay();
        nextMonth   = curentDate.addMonth();
        yesterDate  = curentDate.subtractDay();
        prevMonth   = curentDate.subtractMonth();
        nextYear    = curentDate.addYear();
        prevYear    = curentDate.subtractYear();
        System.out.printf("day: %10s,"
                         +"nextDay: %10s,"
                         +"prevDay: %10s,"
                         +"nextMonth: %10s,"
                         +"prevMonth: %10s,"
                         +"nextYear: %10s,"
                         +"prevYear: %10s"
                         +"\n"
                         ,curentDate,tomorowDate,yesterDate,nextMonth
                         ,prevMonth,nextYear,prevYear);
      
      } catch(Exception e) {System.out.println("Error creating date");}
    }
  }
}
