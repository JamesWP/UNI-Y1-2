class Page_Table_Entry
{
  public boolean resident;
  public boolean used;
  public boolean dirty;

  public int page;
  public int last_used;

  public Page_Table_Entry()
  {
    resident = used = dirty = false;
    last_used = 0;
  }
}

public class MMU
{

  int page_faults = 0, rejected_pages = 0, writebacks = 0;

  final int no_pages, no_page_frames;

  final Page_Table_Entry[] page_table;

  MMU(int no_pages, int no_page_frames)
  {
    this.no_pages = no_pages;
    this.no_page_frames = no_page_frames;
    this.page_table = new Page_Table_Entry[no_page_frames];
    for (int i = 0; i < no_page_frames; i++)
      page_table[i] = new Page_Table_Entry();
  }

  public int memRead(int addr, int timestamp)
  {
    int page_offset = pageOffset(addr);
    int page_frame_no = getOrLoadPageFrame(addr, timestamp);
    if (page_frame_no == -1) return -1;
    return pageFrameMemAddr(page_frame_no, page_offset);
  }

  public void memWrite(int addr, int timestamp)
  {
    int page_offset = pageOffset(addr);
    int page_frame_no = getOrLoadPageFrame(addr, timestamp);
    if (page_frame_no == -1) return;
    page_table[page_frame_no].dirty = true;
  }

  public int getOrLoadPageFrame(int addr, int timestamp)
  {
    int page_no = pageNo(addr);
    int page_offset = pageOffset(addr);

    if (page_no > no_pages)
    {
      rejected_pages++;
      return -1;
    }

    for (int page_frame_no = 0; page_frame_no < no_page_frames; page_frame_no++)
    {
      if (page_table[page_frame_no].page == page_no
              && page_table[page_frame_no].resident)
      {
        // found
        page_table[page_frame_no].last_used = timestamp;
        return page_frame_no;
      }
    }

    page_faults++;
    //  miss not in table
    int page_frame_no = getLRUPageFrameNo();

    if (page_table[page_frame_no].resident)
      rejected_pages++;

    // if dirty write data back
    if (page_table[page_frame_no].dirty)
    {
      writebacks++;
    }
    // swap out new page
    page_table[page_frame_no].page = page_no;
    page_table[page_frame_no].resident = true;
    page_table[page_frame_no].dirty = false;
    page_table[page_frame_no].last_used = timestamp;

    return page_frame_no;
  }

  public int pageNo(int addr)
  {
    return (addr >> 4) & 0xffff;
  }

  public int pageOffset(int addr)
  {
    return addr & 0xffff;
  }

  public int pageFrameMemAddr(int pageFrame, int offset)
  {
    return (pageFrame << 4) | (offset & 0xffff);
  }

  public int getLRUPageFrameNo()
  {
    int oldestTime = page_table[0].last_used;
    int oldestFrame = 0;
    for (int i = 0; i < no_page_frames; i++)
    {
      if (oldestTime < page_table[i].last_used)
      {
        oldestTime = page_table[i].last_used;
        oldestFrame = i;
      }
    }
    return oldestFrame;
  }
}