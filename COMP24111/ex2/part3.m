
numbers = [1 8];
total = 500; % of each number
testRatio = 0.1; % of test to training data

[trainingo,traininglabels,testingo,testinglabels,numberOfTests]...
    = getPartitionedData(maindata,numbers,testRatio,total);

training = applymyextract(trainingo);
testing = applymyextract(testingo);

featCount = size(training,2);

classification = JPperceptron(testing, training, traininglabels,numbers);

[conf,labels] = confusionmat(testinglabels,classification);

totalCorrect = sum(classification==testinglabels)/size(classification,1);

score = totalCorrect*100/featCount;

%graph
showdata(testingo,testinglabels,classification);

%results
score
totalCorrect
featCount
          
%nice confusion mat
dataset({conf 'n3','n6'},'obsnames', {'n3','n6'})