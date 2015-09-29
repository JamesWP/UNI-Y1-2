public abstract class Cache {
    int size, line_size;//sizes in bytes initialised by constructor of subclass
    // counters used to generate performance statistics
    int total_accesses, read_accesses, write_accesses; //all total numbers hit or miss
    // read includes read and fetch
    int read_misses, write_misses, rejections, main_mem_accesses;
    // rejections is when a valid line in the cache is replaced by a new line

    Cache() {
        //initialise statistics when new cache created
        total_accesses = 0;
        read_accesses = 0;
        write_accesses = 0;
        read_misses = 0;
        write_misses = 0;
        rejections = 0;
        main_mem_accesses = 0;
    }

    public void write(int addr, Object indata) {
        total_accesses++;
        write_accesses++;
        Object entry = cacheSearch(addr);
        if (entry != null) cacheWriteData(indata);
        else {
            write_misses++;
        }
    }

    public Object read(int addr) {
        total_accesses++;
        read_accesses++;
        Object entry = cacheSearch(addr);
        if (entry != null) return entry;
        else {
            read_misses++;
            main_mem_accesses++;
            OldCacheLineInfo old_line_info = cacheNewEntry(addr);
            if (old_line_info.old_valid) rejections++;  // the old valid value
            return old_line_info.data;
        }
    }

    abstract Object cacheSearch(int addr);

    abstract OldCacheLineInfo cacheNewEntry(int addr);

    abstract void cacheWriteData(Object entry);

    public void dumpStats() {
        System.out.println("Total accesses = " + total_accesses);
        System.out.println("Total read accesses = " + read_accesses);
        System.out.println("Total write accesses = " + write_accesses);
        System.out.println("Read misses = " + read_misses);
        System.out.println("Write misses = " + write_misses);
        System.out.println("Total rejections = " + rejections);
        System.out.println("Total main memory accesses = " + main_mem_accesses);
        System.out.println("Read miss rate = " + (float) 100 * read_misses / read_accesses + "%");
        System.out.println("Write miss rate = " + (float) 100 * write_misses / write_accesses + "%\n");
    }

    public int totalAccesses() {
        return total_accesses;
    }

    public int readAccesses() {
        return read_accesses;
    }

    public int writeAccesses() {
        return write_accesses;
    }

    public int readMisses() {
        return read_misses;
    }

    public int writeMisses() {
        return write_misses;
    }

    public int numRejections() {
        return rejections;
    }

    public int mainMemAccesses() {
        return main_mem_accesses;
    }
}








