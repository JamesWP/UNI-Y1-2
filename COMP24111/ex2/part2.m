
numbers = [6 8 5 4];
total = 500; % of each number
testRatio = 0.1; % of test to training data
k = 5;

[trainingo,traininglabels,testingo,testinglabels,numberOfTests]...
    = getPartitionedData(maindata,numbers,testRatio,total);

training = applymyextract(trainingo);
testing = applymyextract(testingo);

featCount = size(training,2);

classification = JPknearest(k, testing, training, traininglabels);

[conf,labels] =  confusionmat(testinglabels,classification);

totalCorrect = sum(classification==testinglabels)/size(classification,1);

score = totalCorrect*100/featCount;

%graph
showdata(testingo,testinglabels,classification);

%results
score
totalCorrect
featCount
confmatrix = [0 labels'; labels conf]
          