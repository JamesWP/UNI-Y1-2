import java.io.*;
import java.util.*;

class MemAccess {
  //obtained from trace file by getAccess
  //atype is coded: Read = 0, Write = 1, Fetch = 2;
  int atype;
  int addr;
}

public class MMUSim {

  static BufferedReader in;
  final static int Read = 0, Write = 1, Fetch = 2;
  static int inst_fetches;
  static int vmem_pages, vmem_bits, rmem_pages; // virtual & real memory sizes (in pages)
  static MMU mm_unit;
  static int timestamp;
  static int accesses;

  public static void main(String args[]){
    try {
      in = new BufferedReader(new FileReader(args[0]));
      vmem_pages = 4096; vmem_bits = 12;  // these must be consistent
      rmem_pages = 64;
      mm_unit = new MMU(vmem_pages,rmem_pages);
      doSimul(true);
      in.close();
    } catch (FileNotFoundException e) {
      System.out.println("File "+args[0]+" Not Found");
    } catch (IOException ex) {
      System.out.println("File Close Error");
    }
  }

  private static void doSimul(boolean printing) {
    //If printing is true statistics are printed
    System.out.println("Virtual Size = "+vmem_pages+" Real Size = "+rmem_pages);
    inst_fetches = 0;
    timestamp = 0;
    accesses = 0;
    try {
      while(true) {
        MemAccess access = getAccess();
        int addr = access.addr;
        int vpage_no = addr >> 24 - vmem_bits; // assumes a 24 bit virt addr
        if (access.atype == Read){
          mm_unit.memRead(vpage_no,timestamp);
        }
        else if (access.atype == Write){
          mm_unit.memWrite(vpage_no,timestamp);

        }
        else if (access.atype == Fetch){
          inst_fetches++;
          mm_unit.memRead(vpage_no,timestamp);
        }
        else System.out.println("Unknown Access Type");
        timestamp++;
        accesses++;
      }
    } catch (NullPointerException e) {
      //end of trace file and simulation run
      if (printing) {
        System.out.println("Trace Input Ended");
        System.out.println("Total Accesses "+accesses);
        System.out.println("Total Inst Fetches "+inst_fetches);
        System.out.println("Total Page Faults "+mm_unit.page_faults);
        System.out.println("Total Page Rejections "+mm_unit.rejected_pages);
        System.out.println("Total Page Write Backs "+mm_unit.writebacks);
      }
    }
  }

  private static MemAccess getAccess() throws NullPointerException {
    // return values from next line of trace file or null at end of file
    try {
      MemAccess res = new MemAccess();
      StringTokenizer st = new StringTokenizer(in.readLine());
      res.atype = Integer.valueOf(st.nextToken()).intValue();
      res.addr = Integer.valueOf(st.nextToken(),16).intValue();
      return res;
    } catch (IOException e) {
      System.out.println("An unexpected I/O exception occured");
      return null;
    }
  }

}