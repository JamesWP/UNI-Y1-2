package robot;

public final class SolutionRobotBeliefState extends RobotBeliefState
{
    public SolutionRobotBeliefState(final WorldMap map, final ProbActionToggler probActionToggler) {
        super(map, probActionToggler);
        this.map = map;
        this.statusString = "Ian's code";
        this.initializeMatrices();
    }
    
    public void initializeMatrices() {
        this.beliefMatrix = new double[100][100][100];
        this.workMatrix = new double[100][100][100];
        this.positionBeliefMatrix = new double[100][100];
        this.orientationBeliefMatrix = new double[100];
        int n = 0;
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                if (this.map.isOccupied(i, j)) {
                    for (int k = 0; k < 100; ++k) {
                        this.beliefMatrix[i][j][k] = 0.0;
                    }
                }
                else {
                    for (int l = 0; l < 100; ++l) {
                        this.beliefMatrix[i][j][l] = 1.0;
                    }
                    n += 100;
                }
            }
        }
        for (int n2 = 0; n2 < 100; ++n2) {
            for (int n3 = 0; n3 < 100; ++n3) {
                for (int n4 = 0; n4 < 100; ++n4) {
                    final double[] array = this.beliefMatrix[n2][n3];
                    final int n5 = n4;
                    array[n5] /= n;
                }
            }
        }
        this.updateMaxProbabilities();
    }
    
    public void updateProbabilityOnObservation(final Observation observation) {
        double n = 0.0;
        final Pose pose = new Pose();
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                for (int k = 0; k < 100; ++k) {
                    pose.x = i;
                    pose.y = j;
                    pose.theta = k;
                    this.workMatrix[i][j][k] = this.map.getObservationProbability(pose, observation) * this.beliefMatrix[i][j][k];
                    n += this.workMatrix[i][j][k];
                }
            }
        }
        for (int l = 0; l < 100; ++l) {
            for (int n2 = 0; n2 < 100; ++n2) {
                for (int n3 = 0; n3 < 100; ++n3) {
                    this.beliefMatrix[l][n2][n3] = this.workMatrix[l][n2][n3] / n;
                }
            }
        }
        this.updateMaxProbabilities();
    }
    
    public void updateProbabilityOnAction(final Action action) {
        final Pose pose = new Pose();
        final Action action2 = new Action();
        action2.type = action.type;
        final int n = 2 * action.parameter;
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                for (int k = 0; k < 100; ++k) {
                    this.workMatrix[i][j][k] = 0.0;
                }
            }
        }
        if (!this.probActionToggler.probActions()) {
            for (int l = 0; l < 100; ++l) {
                for (int n2 = 0; n2 < 100; ++n2) {
                    for (int n3 = 0; n3 < 100; ++n3) {
                        this.map.fillPoseOnAction(pose, l, n2, n3, action);
                        final double[] array = this.workMatrix[pose.x][pose.y];
                        final int theta = pose.theta;
                        array[theta] += this.beliefMatrix[l][n2][n3];
                    }
                }
            }
        }
        else {
            for (int n4 = 0; n4 < 100; ++n4) {
                for (int n5 = 0; n5 < 100; ++n5) {
                    for (int n6 = 0; n6 < 100; ++n6) {
                        action2.parameter = 0;
                        while (action2.parameter <= n) {
                            final double probabilify = WorldMap.probabilify(action.parameter, action2.parameter);
                            this.map.fillPoseOnAction(pose, n4, n5, n6, action2);
                            final double[] array2 = this.workMatrix[pose.x][pose.y];
                            final int theta2 = pose.theta;
                            array2[theta2] += this.beliefMatrix[n4][n5][n6] * probabilify;
                            final Action action3 = action2;
                            ++action3.parameter;
                        }
                    }
                }
            }
        }
        for (int n7 = 0; n7 < 100; ++n7) {
            for (int n8 = 0; n8 < 100; ++n8) {
                for (int n9 = 0; n9 < 100; ++n9) {
                    this.beliefMatrix[n7][n8][n9] = this.workMatrix[n7][n8][n9];
                }
            }
        }
        this.updateMaxProbabilities();
    }
}
