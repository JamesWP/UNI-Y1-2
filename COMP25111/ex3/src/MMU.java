class Page_Table_Entry
{
  public boolean resident;
  public boolean used;
  public boolean dirty;
  public int page_frame;
  public int last_used;
  public int time_in;

  public Page_Table_Entry()
  {
    resident = used = dirty = false;
    last_used = 0;
  }
}

public class MMU
{

  int page_faults = 0, rejected_pages = 0, writebacks = 0,page_frame_high =0;

  final int no_pages, no_page_frames;

  final Page_Table_Entry[] page_table;

  MMU(int no_pages, int no_page_frames)
  {
    this.no_pages = no_pages;
    this.no_page_frames = no_page_frames;
    this.page_table = new Page_Table_Entry[no_pages];
    for (int i = 0; i < no_pages; i++)
      page_table[i] = new Page_Table_Entry();
  }

  public int memRead(int addr, int timestamp)
  {
    int page_frame_no = getOrLoadPageFrame(addr, timestamp);
    if (page_frame_no == -1) return -1;
    return page_frame_no;
  }

  public void memWrite(int addr, int timestamp)
  {
    int page_frame_no = getOrLoadPageFrame(addr, timestamp);
    if (page_frame_no == -1) return;

    System.out.println("addr" + addr + " allocated" + page_frame_no);
    page_table[addr].dirty = true;
  }

  public int getOrLoadPageFrame(int addr, int timestamp)
  {
    if (addr > no_pages)
    {
      rejected_pages++;
      return -1;
    }


    if (page_table[addr].resident)
    {
      page_table[addr].last_used = timestamp;
      return page_table[addr].page_frame;
    }

    page_faults++;


    //  miss not in table
    int page_frame_no;

    if (page_frame_high<no_page_frames){
      page_frame_no = page_frame_high++;
    }else{
      page_frame_no = getLRUPageFrameNo();
      //page_frame_no = getFIFOPageFrameNo();
    }


    for(int i = 0;i<no_pages;i++)
      if(page_table[i].resident && page_table[i].page_frame==page_frame_no){
        if(page_table[i].dirty) writebacks++;
        page_table[i].dirty = false;
        page_table[i].resident = false;
      }

    page_table[addr].page_frame = page_frame_no;
    page_table[addr].resident = true;
    page_table[addr].dirty = false;
    page_table[addr].last_used = timestamp;
    page_table[addr].time_in = timestamp;

    return page_frame_no;
  }

  public int getLRUPageFrameNo()
  {

    int oldestTime = 0x7FFFFF;
    int oldestFrame = 0;
    for (int i = 0; i < no_pages; i++)
    {
      if (oldestTime > page_table[i].last_used && page_table[i].resident)
      {
        oldestTime = page_table[i].last_used;
        oldestFrame = i;
      }
    }
    return page_table[oldestFrame].page_frame;
  }

  public int getFIFOPageFrameNo()
  {
    int oldestTime = 0x7FFFFF;
    int oldestFrame = 0;
    for (int i = 0; i < no_pages; i++)
    {
      if (oldestTime > page_table[i].time_in && page_table[i].resident)
      {
        oldestTime = page_table[i].time_in;
        oldestFrame = i;
      }
    }
    return page_table[oldestFrame].page_frame;
  }
}