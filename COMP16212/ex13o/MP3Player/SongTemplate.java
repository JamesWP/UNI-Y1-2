/**
 * Partially completed class to represent a song for the Song Database.
 * <P>
 * This file should be copied as Song.java and then modified to provide
 * the implementation.
 */
public class SongTemplate implements Comparable<SongTemplate>
{
  /**
   * Sort: use title field<BR>
   * Match: use substring of title field
   */
  public static final int USE_TITLE = 0;
  /**
   * Sort: use artist<BR>
   * Match: use substring of artist field
   */
  public static final int USE_ARTIST = 1;
  /**
   * Sort: use genre field<BR>
   * Match: use substring of genre field
   */
  public static final int USE_GENRE = 2;
  /**
   * Sort: use times played field<BR>
   * Match: use substring of times played field
   */
  public static final int USE_TIMES_PLAYED = 3;
  /**
   * Sort: not used<BR>
   * Match: use exact match with title field
   */
  public static final int USE_EXACT_TITLE = 4;
  /**
   * Sort: not used<BR>
   * Match: use exact match with artist field
   */
  public static final int USE_EXACT_ARTIST = 5;
  /**
   * Sort: not used<BR>
   * Match: use exact match with genre field
   */
  public static final int USE_EXACT_GENRE = 6;
  
  /**
   * @return return title field
   */
  public String getTitle()
  {
    return "";
  }

  /**
   * @return return artist field
   */
  public String getArtist()
  {
    return "";
  }


  /**
   * @return return genre field
   */
  public String getGenre()
  {
    return "";
  }


  /**
   * @return return URL field
   */
  public String getURL  ()
  {
    return "";
  }


  public int getTimesPlayed  ()
  {
    return 0;
  }


  public void incTimesPlayed  ()
  {
  }


  /** Matches this song against a given
   *  title, artist or genre,
   *  depending on the given match mode, which may be as a substring or exact.
   *  (note: there is no match for url/timesPlayed)
   *  @param matchMode match mode to use
   *  @param matchValue match value to use (either exact or substring, depending on 
   *  matchMode)
   *  @return true if and only if song matches matchValue with respect to matchMode
   */
  public  boolean matches(int matchMode, String matchValue)
  {
     return false;
  }

  /**
   * Sets the sort mode to use in future comparisons.
   * This should be over-ridden in an implementation.
   * @param requiredSortMode required sort mode
   */
  public static void setSortMode(int requiredSortMode) 
  {
  }

  /**
   * Modify to Implement {@link Comparable Comparable} interface, to give a total 
   * ordering on <CODE>SongTemplate</CODE> objects, but dependent upon the
   * current sort mode.
   * (note: consistent {@link #equals(Object) equals()} 
   * and {@link #hashCode() hashCode()} methods should be implemented.
   * @param other the object to be compared
   * @return -ve(<), 0(=), +ve(>)
   */
  public int compareTo(SongTemplate other)
  { 
      return -1;
  };

}
