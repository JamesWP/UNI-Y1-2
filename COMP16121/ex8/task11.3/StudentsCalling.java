import java.util.Random;

public class StudentsCalling
{
  public static void main(String[] args)
  {
    Random rand = new Random();

    heading("Creating objects");
    Account account1 = createAccount("Phonz");
    Account account2 = createAccount("AT-AT");
    Account account3 = createAccount("J-Portable");
    Student student1 = createStudent("Jimbob");
    Student student2 = createStudent("Roberto");
    Student student3 = createStudent("Delbert");
    heading("Creating phones with accounts");
    Phone   phone1   = createPhone("SANSONG F1",account1);
    Phone   phone2   = createPhone("GHD 1"     ,account2);
    Phone   phone3   = createPhone("iThing",account3);
    heading("Students purchase phones");
    studentPurchasePhone(student1,phone1);
    studentPurchasePhone(student2,phone2);
    studentPurchasePhone(student3,phone3);
    heading("Students top up there phones");
    studentTopsUp(student1,10);
    studentTopsUp(student2,15);
    studentTopsUp(student3,5);
    heading("All students make a single call");
    studentAttemptsCall(student1,rand.nextInt(200));
    studentAttemptsCall(student2,rand.nextInt(1000));
    studentAttemptsCall(student3,rand.nextInt(200));
    heading("Student 3 overspends");
    studentAttemptsCall(student3,rand.nextInt(200)+2000);
    studentAttemptsCall(student3,rand.nextInt(200)+3000);
    studentAttemptsCall(student3,rand.nextInt(200));
    System.out.println("And then borrows " + student1 + "'s phone to make"
                       + "a call");
    studentPurchasePhone(student3,phone1);
    studentAttemptsCall(student3,5000);
    System.out.println("His borrowed phone " + phone1 + "is used by:");
    System.out.println(student1 + " and " + student3);

    System.out.println(student3 + "'s old " + phone3 
                       + " is not used by anyone!");
  }

  public static void heading(String name)
  {
    System.out.println("-- " + name + " --");
  }

  public static Account createAccount(String name)
  {
    Account account = new Account(name);
    System.out.println("Creating account:" + account);
    System.out.println();
    return account;
  }

  public static Student createStudent(String name)
  {
    Student student = new Student(name);
    System.out.println("Creating student:" + student);
    System.out.println();
    return student;
  }

  public static Phone createPhone(String name,Account account)
  {
    System.out.println("Creating phone called " + name + " on account "
                       + account);
    Phone phone = new Phone(name,account);
    System.out.println("new phone:" + phone);
    System.out.println();
    return phone;
  }

  public static void studentTopsUp(Student student, int amount)
  {
    System.out.println(student+ " is trying to top up £" + amount);
    if(student.topUpPhone(amount))
      System.out.println("top up successfull student is now " + student);
    else
      System.out.println("But student dosent have a phone :(");
    System.out.println();
  }

  public static void studentPurchasePhone(Student student, Phone phone)
  {
    System.out.println(student + "is going to purchase " + phone);
    student.purchasePhone(phone);
    System.out.println("And is now " + student);
    System.out.println();
  }

  public static void studentAttemptsCall(Student student, int length)
  {
    System.out.println(student + " is trying to make a "+ length
                       + " second call");
    int actualLength = student.makeCall(length);
    if(length == actualLength)
      System.out.println("The call was made successfully");
    else if(actualLength > 0)
      System.out.println("The call was made but was cut off after "
                         + actualLength + " seconds");
    else{
      System.out.println("The call wasnt made because the account is empty");
      System.out.println();
      return;
    }
    System.out.println("The student is now " + student);
    System.out.println();
  }

}
