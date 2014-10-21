
numbers = [3 6 7 9];
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
          
%nice confusion mat
dataset({conf 'n3','n6','n8'},'obsnames', {'n3','n6','n8'})