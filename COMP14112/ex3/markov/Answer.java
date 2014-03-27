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

    // Task 4
  }
}
