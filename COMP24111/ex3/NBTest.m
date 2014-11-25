function [ Predictions, Accuracy ] = NBTest(Classes, Values, Prior, Likelihood, testAttributeSet, Labels )
    Predictions = [];
    Accuracy = [];
    features = size(testAttributeSet,2);
    for example = 1:size(testAttributeSet,1)
        classProbs = [];
        for class = 1:size(Classes,1)
            classProb = 1% Prior(class);
            for feature = 1:features
                for value = 1:size(Values,1)
                    if Values(value)==testAttributeSet(example,feature)
                        classProb = classProb * Likelihood(class,feature,value);
                    end
                end
            end
            classProbs = [classProbs; classProb];
        end
        Prediction = Classes(find(max(classProbs)==classProbs));
        Predictions = [Predictions; Prediction];
    end
    Accuracy = sum(Labels==Predictions)/size(Labels,1);
end


