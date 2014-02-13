package robot;

public class RobotBeliefState
{
    protected double[][][] beliefMatrix;
    protected double[][] positionBeliefMatrix;
    protected double[] orientationBeliefMatrix;
    protected double maxPositionProbability;
    protected double maxOrientationProbability;
    protected double[][][] workMatrix;
    protected String statusString;
    public WorldMap map;
    protected ProbActionToggler probActionToggler;
    
    public RobotBeliefState(final WorldMap map, final ProbActionToggler probActionToggler) {
        super();
        this.map = map;
        this.statusString = "Student's code";
        this.initializeMatrices();
        this.probActionToggler = probActionToggler;
    }
    
    public void initializeMatrices() {
        this.beliefMatrix = new double[100][100][100];
        this.workMatrix = new double[100][100][100];
        this.positionBeliefMatrix = new double[100][100];
        this.orientationBeliefMatrix = new double[100];
        int n = 0;
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                n += (this.map.isOccupied(i, j) ? 1 : 0);
            }
        }
        final double n2 = 1.0 / ((10000 - n) * 100);
        for (int k = 0; k < 100; ++k) {
            for (int l = 0; l < 100; ++l) {
                for (int n3 = 0; n3 < 100; ++n3) {
                    this.beliefMatrix[k][l][n3] = (this.map.isOccupied(k, l) ? 0.0 : n2);
                }
            }
        }
        this.updateMaxProbabilities();
    }
    
    public double getPoseProbability(final Pose pose) {
        return this.beliefMatrix[pose.x][pose.y][pose.theta];
    }
    
    public double getPositionProbability(final int n, final int n2) {
        return this.positionBeliefMatrix[n][n2];
    }
    
    public double getOrientationProbability(final int n) {
        return this.orientationBeliefMatrix[n];
    }
    
    protected void fixWorkMatrix(final Observation observation) {
    }
    
    public void updateProbabilityOnObservation(final Observation observation) {
        final Pose pose = new Pose();
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                for (int k = 0; k < 100; ++k) {
                    pose.x = i;
                    pose.y = j;
                    pose.theta = k;
                    this.beliefMatrix[i][j][k] = this.map.getObservationProbability(pose, observation);
                }
            }
        }
        this.updateMaxProbabilities();
    }
    
    public void updateProbabilityOnAction(final Action action) {
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                for (int k = 0; k < 100; ++k) {
                    this.workMatrix[i][j][k] = 0.0;
                }
            }
        }
        final Pose pose = new Pose();
        for (int l = 0; l < 100; ++l) {
            for (int n = 0; n < 100; ++n) {
                for (int n2 = 0; n2 < 100; ++n2) {
                    this.map.fillPoseOnAction(pose, l, n, n2, action);
                    this.workMatrix[pose.x][pose.y][pose.theta] = this.beliefMatrix[l][n][n2];
                }
            }
        }
        this.beliefMatrix = this.workMatrix;
        this.updateMaxProbabilities();
    }
    
    public double getMaxPositionProbability() {
        return this.maxPositionProbability;
    }
    
    public double getMaxOrientationProbability() {
        return this.maxOrientationProbability;
    }
    
    protected void updateMaxProbabilities() {
        this.maxPositionProbability = 0.0;
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                double n = 0.0;
                for (int k = 0; k < 100; ++k) {
                    n += this.beliefMatrix[i][j][k];
                }
                this.positionBeliefMatrix[i][j] = n;
                if (this.positionBeliefMatrix[i][j] > this.maxPositionProbability) {
                    this.maxPositionProbability = this.positionBeliefMatrix[i][j];
                }
            }
        }
        this.maxOrientationProbability = 0.0;
        for (int l = 0; l < 100; ++l) {
            double n2 = 0.0;
            for (int n3 = 0; n3 < 100; ++n3) {
                for (int n4 = 0; n4 < 100; ++n4) {
                    n2 += this.beliefMatrix[n3][n4][l];
                }
            }
            this.orientationBeliefMatrix[l] = n2;
            if (this.orientationBeliefMatrix[l] > this.maxOrientationProbability) {
                this.maxOrientationProbability = this.orientationBeliefMatrix[l];
            }
        }
    }
}
