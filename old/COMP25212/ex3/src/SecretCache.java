public class SecretCache
        extends Cache
{
    int[] dataTags;
    boolean[] dataSaved;
    Object[] jim;
    int mary;

    SecretCache(int paramInt1, int paramInt2)
    {
        this.size = paramInt1;
        this.line_size = paramInt2;
        this.dataTags = new int[this.size / this.line_size];
        this.jim = new Object[this.size / this.line_size];
        this.dataSaved = new boolean[this.size / this.line_size];
        for (int i = 0; i < this.size / this.line_size; i++) {
            this.dataSaved[i] = false;
        }
    }

    public Object cacheSearch(int paramInt)
    {
        int i = paramInt % (2 * this.size) / (this.line_size * 2);
        if ((this.dataSaved[i]) && (this.dataTags[i] == paramInt / this.line_size))
        {
            this.mary = i;
            return this.jim[i];
        }
        return null;
    }

    public OldCacheLineInfo cacheNewEntry(int paramInt)
    {
        int i = paramInt % (2 * this.size) / (this.line_size * 2);
        OldCacheLineInfo localOldCacheLineInfo = new OldCacheLineInfo();
        localOldCacheLineInfo.old_valid = this.dataSaved[i];
        this.dataTags[i] = (paramInt / this.line_size);
        this.dataSaved[i] = true;
        localOldCacheLineInfo.data = (this.jim[i] = -1);
        return localOldCacheLineInfo;
    }

    public void cacheWriteData(Object paramObject)
    {
        this.jim[this.mary] = paramObject;
    }
}
