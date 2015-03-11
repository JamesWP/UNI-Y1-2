import java.util.HashMap;

/**
 * Created by mbax3jp2 on 19/02/15.
 */
public class DirectMappedCache extends Cache {

    private final int cachelines;

    private int cacheWriteIndex;

    private Object[] data;
    private int[] dataTags;
    private boolean[] dataSaved;

    public DirectMappedCache(int cachesize, int linesize) {
        this.size = cachesize;
        this.line_size = linesize;
        this.cachelines = cachesize/linesize;
        data = new Object[cachelines];
        dataTags = new int[cachelines];
        dataSaved = new boolean[cachelines];
        for(int i=0;i<cachelines;i++) dataSaved[i]=false;
    }

    public int getTag(int addr) { return (addr / line_size) / cachelines;}
    public int getIndex(int addr) { return (addr / line_size) % cachelines;}

    @Override
    Object cacheSearch(int addr) {
        int index = getIndex(addr);
        // finds the item at the address if it is in the cache and saves the location (cache line) if found
        if(dataSaved[index] && dataTags[index] == getTag(addr)){
            cacheWriteIndex = index;
            return data[index];
        }

        // otherwise returns null
        return null;
    }

    @Override
    OldCacheLineInfo cacheNewEntry(int addr) {
        int index = getIndex(addr);
        OldCacheLineInfo info = new OldCacheLineInfo();
        info.data = data[index];
        info.old_valid = dataSaved[index];

        dataSaved[index] = true;
        dataTags[index] = getTag(addr);
        data[index] = new Integer(0);
        return info;
    }

    @Override
    void cacheWriteData(Object entry) {data[cacheWriteIndex] = entry;}
}
