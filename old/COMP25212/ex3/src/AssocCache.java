/**
 * Created by james on 19/03/15.
 */
public class AssocCache extends Cache  {

    private final int cachelines;

    private int cacheWriteIndex;

    private Object[] data;
    private int[] dataTags;
    private boolean[] dataSaved;
    private int nextInsertIndex;

    public AssocCache(int cachesize, int linesize) {
        this.size = cachesize;
        this.line_size = linesize;
        this.cachelines = cachesize/linesize;
        nextInsertIndex = 0;
        data = new Object[cachelines];
        dataTags = new int[cachelines];
        dataSaved = new boolean[cachelines];
        for(int i=0;i<cachelines;i++) dataSaved[i]=false;
    }
    public int getTag(int addr) { return addr / line_size;}

    @Override
    Object cacheSearch(int addr) {
        // search each line of the cache for the tag for the address
        int addrTag = getTag(addr);
        for(int i=0;i<dataTags.length;i++) {
            int tag = dataTags[i];
            // if match then return data
            if (tag == addrTag) {
                cacheWriteIndex = i;
                return data[i];
            }
        }
        // otherwise returns null
        return null;
    }

    @Override
    OldCacheLineInfo cacheNewEntry(int addr) {
        OldCacheLineInfo info = new OldCacheLineInfo();
        info.data = data[nextInsertIndex];
        info.old_valid = dataSaved[nextInsertIndex];

        dataSaved[nextInsertIndex] = true;
        dataTags[nextInsertIndex] = getTag(addr);
        data[nextInsertIndex] = new Integer(0);

        nextInsertIndex = (nextInsertIndex+1)%data.length;

        return info;
    }

    @Override
    void cacheWriteData(Object entry) {data[cacheWriteIndex] = entry;}
}
