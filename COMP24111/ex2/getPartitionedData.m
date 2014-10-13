function [training,traininglabels,testing,testinglabels,numberOfTests]...
    = getPartitionedData(maindata,numbers,testRatio,total)

all = []; 
alllabels = [];
for n = numbers
	all = [all; double(maindata(:,:,n)')];
	alllabels = [alllabels; repmat(n,1,total)'];
end
shuffle = randperm(size(all,1));
all = all(shuffle,:);
alllabels = alllabels(shuffle);

numberOfTests = testRatio*size(all,1);

testing = all(1:numberOfTests,:);
testinglabels = alllabels(1:numberOfTests);
training = all(numberOfTests+1:end,:);
traininglabels = alllabels(numberOfTests+1:end);
