package markov;

/**
 * Class with main method for presenting the results of the lab. At the
 * moment this just reads in the sequence data
 * and the corresponding state labels.
 */

public class Answer
{

  public static void main(String[] args)
  {
    String mfccDataDirectory = "data/yesno_uncut/mfcc/";
    String labelDirectory = "data/yesno_uncut/labels/";

    // Read in the MFCC data and state labels from each class

    DataWithLabels dataClass1 = new DataWithLabels(mfccDataDirectory + "yes",
                                                   labelDirectory + "yes");
    DataWithLabels dataClass2 = new DataWithLabels(mfccDataDirectory + "no",
                                                   labelDirectory + "no");

    // Task 1
    HiddenMarkovModel yesModel = new HiddenMarkovModel(3,
                                                       dataClass1.getMfcc(),
                                                       dataClass1.getLabels());
    HiddenMarkovModel noModel = new HiddenMarkovModel(3,
                                                      dataClass2.getMfcc(),
                                                      dataClass2.getLabels());
    System.out.println("Yes data");
    for (int startState = 0;
         startState <= yesModel.getNoStates(); startState++)
    {
      for (int nextState = 0; nextState <= yesModel.getNoStates(); nextState++)
        System.out.printf("%1.2f ",
                          yesModel.getTransitionProbability(startState,
                                                            nextState)
        );
      System.out.println();
    }
    System.out.println("No data");
    for (int startState = 0; startState <= noModel.getNoStates(); startState++)
    {
      for (int nextState = 0; nextState <= noModel.getNoStates(); nextState++)
        System.out.printf(
                         "%1.2f ",
                         noModel.getTransitionProbability(startState,
                                                          nextState)
        );
      System.out.println();
    }
    // Task 2
    double priorClass1 = (double) dataClass1.getNumberExamples() /
                         (dataClass1
                         .getNumberExamples() + dataClass2
                                                .getNumberExamples());
    Classifier yesNoClassifier = new Classifier(yesModel, noModel, priorClass1);
    int noIncorrect = 0;
    for (int sampleNo = 0;
         sampleNo < dataClass1.getNumberExamples();
         sampleNo++)
      if (yesNoClassifier.classify(dataClass1.getMfcc(sampleNo)) < 0.5)
        noIncorrect++;
    for (int sampleNo = 0;
         sampleNo < dataClass2.getNumberExamples();
         sampleNo++)
      if (yesNoClassifier.classify(dataClass2.getMfcc(sampleNo)) > 0.5)
        noIncorrect++;
    double perc = (double) noIncorrect /
                  (dataClass1.getNumberExamples() + dataClass2
                                                    .getNumberExamples());
    System.out.println("Incorrect perc: " + perc);

    // Task 3

    //       sil yes no sil stop
    // sil   +   y   n  +   +
    double sil_sil1 = yesModel.getTransitionProbability(0,0)
                     + noModel.getTransitionProbability(0,0);
    double sil_yes  = yesModel.getTransitionProbability(0,1);
    double sil_no   =  noModel.getTransitionProbability(0,1);
    double sil_sil2 = yesModel.getTransitionProbability(0,2)
                     + noModel.getTransitionProbability(0,2);
    double sil_stop = yesModel.getTransitionProbability(0,3)
                     + noModel.getTransitionProbability(0,3);
    double sil_total = sil_sil1 + sil_yes + sil_no + sil_sil2 + sil_stop;

    sil_sil1 = sil_sil1/sil_total;
    sil_yes  = sil_yes /sil_total;
    sil_no   = sil_no  /sil_total;
    sil_sil2 = sil_sil2/sil_total;

    //       sil yes no sil stop
    // yes   0   y   0  y   y
    double yes_sil1 = 0;
    double yes_yes  = yesModel.getTransitionProbability(1,1);
    double yes_no   = 0;
    double yes_sil2 = yesModel.getTransitionProbability(1,2);
    double yes_stop = yesModel.getTransitionProbability(1,3);
    
    //       sil yes no sil stop
    // no    0   0   n  n   n
    double no_sil1 = 0;
    double no_yes  = 0;
    double no_no   = noModel.getTransitionProbability(1,1);
    double no_sil2 = noModel.getTransitionProbability(1,2);
    double no_stop = noModel.getTransitionProbability(1,3);
    
    //       sil yes no sil stop
    // sil2  0   0   0  +   +
    double sil2_sil1 = 0; 
    double sil2_yes  = 0;
    double sil2_no   = 0;
    double sil2_sil2 = yesModel.getTransitionProbability(2,2)
                      + noModel.getTransitionProbability(2,2);
    double sil2_stop = yesModel.getTransitionProbability(2,3)
                      + noModel.getTransitionProbability(2,3);
    double sil2_total = sil2_sil1 + sil2_yes + sil2_no + sil2_sil2 + sil2_stop;

    sil2_sil1 = sil2_sil1/sil2_total;
    sil2_yes  = sil2_yes /sil2_total;
    sil2_no   = sil2_no  /sil2_total;
    sil2_sil2 = sil2_sil2/sil2_total;
    
    //       sil yes no sil stop
    // start 1   0   0  0   0
    double start_sil1 = 1; 
    
    
    HiddenMarkovModel combined = new HiddenMarkovModel(4);
    
    combined.setTransitionProbability(sil_sil1,0,0);
    combined.setTransitionProbability(sil_yes ,0,1);
    combined.setTransitionProbability(sil_no  ,0,2);
    combined.setTransitionProbability(sil_sil2,0,3);
    combined.setTransitionProbability(sil_stop,0,4);
    
    combined.setTransitionProbability(yes_sil1,1,0);
    combined.setTransitionProbability(yes_yes ,1,1);
    combined.setTransitionProbability(yes_no  ,1,2);
    combined.setTransitionProbability(yes_sil2,1,3);
    combined.setTransitionProbability(yes_stop,1,4);

    combined.setTransitionProbability(no_sil1,2,0);
    combined.setTransitionProbability(no_yes ,2,1);
    combined.setTransitionProbability(no_no  ,2,2);
    combined.setTransitionProbability(no_sil2,2,3);
    combined.setTransitionProbability(no_stop,2,4);

    combined.setTransitionProbability(sil2_sil1,3,0);
    combined.setTransitionProbability(sil2_yes ,3,1);
    combined.setTransitionProbability(sil2_no  ,3,2);
    combined.setTransitionProbability(sil2_sil2,3,3);
    combined.setTransitionProbability(sil2_stop,3,4);

    combined.setTransitionProbability(start_sil1,4,0);
    combined.setTransitionProbability(0.0       ,4,1);
    combined.setTransitionProbability(0.0       ,4,2);
    combined.setTransitionProbability(0.0       ,4,3);
    combined.setTransitionProbability(0.0       ,4,4);

  
    System.out.println("Combined data");
    for (int startState = 0; startState <= combined.getNoStates(); startState++)
    {
      for (int nextState = 0; nextState <= combined.getNoStates(); nextState++)
        System.out.printf(
                         "%1.2f ",
                         combined.getTransitionProbability(startState,
                                                          nextState)
        );
      System.out.println();
    }
    // Task 4

    




  }
}
