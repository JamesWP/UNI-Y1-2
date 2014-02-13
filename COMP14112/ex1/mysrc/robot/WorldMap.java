package robot;

import java.util.*;

public class WorldMap
{
    public LinkedList<WorldObject> objects;
    private boolean[][] occupancyMatrix;
    public double[][][] distanceMatrix;
    
    public WorldMap() {
        super();
        this.objects = new LinkedList<WorldObject>();
        this.occupancyMatrix = new boolean[100][100];
        this.distanceMatrix = new double[100][100][100];
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                this.occupancyMatrix[i][j] = false;
            }
        }
        for (int k = 0; k < 100; ++k) {
            for (int l = 0; l < 100; ++l) {
                for (int m = 0; m < 100; ++m) {
                    if (this.getMinDistanceToBorder(k, l, m) < 0.0) {
                        System.out.println("Problem: neg distance" + this.getMinDistanceToBorder(k, l, m));
                        System.out.println("i: " + k);
                        System.out.println("j: " + l);
                        System.out.println("k: " + m);
                    }
                    this.distanceMatrix[k][l][m] = this.getMinDistanceToBorder(k, l, m);
                }
            }
        }
    }
    
    public boolean isOccupied(final int n, final int n2) {
        return this.occupancyMatrix[n][n2];
    }
    
    public void addObject(final WorldObject e) {
        this.objects.add(e);
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                this.occupancyMatrix[i][j] = (this.occupancyMatrix[i][j] || e.isIn(i, j));
            }
        }
        for (int k = 0; k < 100; ++k) {
            for (int l = 0; l < 100; ++l) {
                for (int n = 0; n < 100; ++n) {
                    final double interceptDistance = e.getInterceptDistance(k, l, n);
                    if (interceptDistance >= 0.0 && interceptDistance < this.distanceMatrix[k][l][n]) {
                        this.distanceMatrix[k][l][n] = interceptDistance;
                    }
                }
            }
        }
    }
    
    public double getMinDistanceToBorder(final int n, final int n2, final int n3) {
        final double n4 = n;
        final double n5 = n2;
        final double n6 = 99;
        final double n7 = 6.283185307179586 * n3 / 100.0;
        if (n3 == 0) {
            return n6 - n4;
        }
        if (n3 == 25) {
            return n5;
        }
        if (n3 == 50) {
            return n4;
        }
        if (n3 == 75) {
            return n6 - n5;
        }
        if (n == 0 && n3 > 25 && n3 < 75) {
            return 0.0;
        }
        if (n2 == 0 && n3 > 25 && n3 < 50) {
            return 0.0;
        }
        if (n == 99 && (n3 < 25 || n3 > 75)) {
            return 0.0;
        }
        if (n2 == 99 && n3 > 50) {
            return 0.0;
        }
        if (n3 < 25 || n3 > 75) {
            final double n8 = n5 - (n6 - n4) * Math.tan(n7);
            if (0.0 <= n8 && n8 <= n6) {
                return (n6 - n4) / Math.cos(n7);
            }
        }
        if (n3 > 0 && n3 < 50) {
            final double n9 = n4 + n5 / Math.tan(n7);
            if (0.0 <= n9 && n9 <= n6) {
                return n5 / Math.sin(n7);
            }
        }
        if (n3 > 25 && n3 < 75) {
            final double n10 = n5 + n4 * Math.tan(n7);
            if (0.0 <= n10 && n10 <= n6) {
                return -n4 / Math.cos(n7);
            }
        }
        if (n3 > 50) {
            final double n11 = n4 - (n6 - n5) / Math.tan(n7);
            if (0.0 <= n11 && n11 <= 100.0) {
                return -(n6 - n5) / Math.sin(n7);
            }
        }
        System.out.println("System Error: Consult Ian Pratt-Hartmann");
        return 0.0;
    }
    
    public double getMinDistanceToTarget(final int n, final int n2, final int n3) {
        double minDistanceToBorder = this.getMinDistanceToBorder(n, n2, n3);
        final Iterator<WorldObject> iterator = this.objects.iterator();
        while (iterator.hasNext()) {
            final double interceptDistance = iterator.next().getInterceptDistance(n, n2, n3);
            if (interceptDistance >= 0.0 && interceptDistance < minDistanceToBorder) {
                minDistanceToBorder = interceptDistance;
            }
        }
        return minDistanceToBorder;
    }
    
    public void fillPoseOnAction(final Pose pose, final int n, final int n2, final int n3, final Action action) {
        pose.x = n;
        pose.y = n2;
        pose.theta = n3;
        if (action.type == ActionType.TURN_RIGHT) {
            pose.theta = (pose.theta + action.parameter) % 100;
        }
        else if (action.type == ActionType.TURN_LEFT) {
            pose.theta = (pose.theta + 100 - action.parameter) % 100;
        }
        else {
            final double n4 = 6.283185307179586 * n3 / 100.0;
            final double min = Math.min(action.parameter, this.distanceMatrix[n][n2][n3]);
            pose.x += (int)(min * Math.cos(n4));
            pose.y -= (int)(min * Math.sin(n4));
            if (pose.x < 0 || pose.x >= 100 || pose.y < 0 || pose.y >= 100) {
                System.out.println("pose.x: " + pose.x);
                System.out.println("pose.y: " + pose.y);
                System.out.println("x: " + n);
                System.out.println("y: " + n2);
                System.out.println("theta: " + n3);
                System.out.println("distanceMatrix[x][y][theta]" + this.distanceMatrix[n][n2][n3]);
                System.out.println("forwardStep: " + min);
            }
        }
    }
    
    public static int pollRandom(final int n, final int n2) {
        final double n3 = Math.random() / 2.0;
        int n4 = 0;
        for (double normalDistribution = normalDistribution(0, n2, 0); normalDistribution < n3 && n4 < 3 * n2; ++n4, normalDistribution += normalDistribution(0, n2, n4)) {}
        if (Math.random() > 0.5) {
            return n + n4;
        }
        return n - n4;
    }
    
    public static double probabilify(final int n, final int n2) {
        return normalDistribution(n, n / 3, n2);
    }
    
    public static double normalDistribution(final int n, final int n2, final int n3) {
        if (n2 != 0) {
            final double n4 = (n3 - n + 0.5) / n2;
            return Math.exp(-0.5 * n4 * n4) / (Math.sqrt(6.283185307179586) * n2);
        }
        if (n3 == n) {
            return 1.0;
        }
        return 0.0;
    }
    
    public double getObservationProbability(final Pose pose, final Observation observation) {
        int theta;
        if (observation.sensor == SensorType.FORWARD_SENSOR) {
            theta = pose.theta;
        }
        else if (observation.sensor == SensorType.RIGHT_SENSOR) {
            theta = (pose.theta + 25) % 100;
        }
        else {
            theta = (pose.theta + 75) % 100;
        }
        return probabilify((int)this.distanceMatrix[pose.x][pose.y][theta], observation.reading);
    }
}
