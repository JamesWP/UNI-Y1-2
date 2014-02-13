package robot;

public class WorldObject
{
    public int xLL;
    public int yLL;
    public int xUR;
    public int yUR;
    
    public WorldObject(final int xll, final int yll, final int xur, final int yur) {
        super();
        this.xLL = xll;
        this.yLL = yll;
        this.xUR = xur;
        this.yUR = yur;
    }
    
    public boolean isIn(final int n, final int n2) {
        return n >= this.xLL && n <= this.xUR && n2 >= this.yLL && n2 <= this.yUR;
    }
    
    public double getInterceptDistance(final int n, final int n2, final int n3) {
        final double n4 = 6.283185307179586 * n3 / 100.0;
        final double n5 = n;
        final double n6 = n2;
        final double n7 = this.xLL - 0.2;
        final double n8 = this.yLL - 0.2;
        final double n9 = this.xUR + 0.2;
        final double n10 = this.yUR + 0.2;
        if (this.isIn(n, n2)) {
            return 0.0;
        }
        if (n3 == 0 && n < this.xLL && this.yLL <= n2 && n2 <= this.yUR) {
            return n7 - n5;
        }
        if (n3 == 25 && n2 > this.yUR && this.xLL <= n && n <= this.yUR) {
            return n6 - n10;
        }
        if (n3 == 50 && n > this.xUR && this.yLL <= n2 && n2 <= this.yUR) {
            return n5 - n9;
        }
        if (n3 == 75 && n2 < this.yLL && this.xLL <= n && n <= this.xUR) {
            return n8 - n6;
        }
        if ((n3 < 25 || n3 > 75) && n < this.xLL) {
            final double n11 = n6 - (n7 - n5) * Math.tan(n4);
            if (n8 <= n11 && n11 <= n10) {
                return (n7 - n5) / Math.cos(n4);
            }
        }
        if (n3 < 50 && n2 > this.yUR) {
            final double n12 = n5 + (n6 - n10) / Math.tan(n4);
            if (n7 <= n12 && n12 <= n9) {
                return (n6 - n10) / Math.sin(n4);
            }
        }
        if (n3 > 25 && n3 < 75 && n > this.xUR) {
            final double n13 = n6 + (n5 - n9) * Math.tan(n4);
            if (n8 <= n13 && n13 <= n10) {
                return -(n5 - n9) / Math.cos(n4);
            }
        }
        if (n3 > 50 && n2 < this.yLL) {
            final double n14 = n5 - (n8 - n6) / Math.tan(n4);
            if (n7 <= n14 && n14 <= n9) {
                return -(n8 - n6) / Math.sin(n4);
            }
        }
        return -1.0;
    }
}
