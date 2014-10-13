ks = 1:10;

numbers = [3 8];
total = 500; % of each number
testRatio = 0.1; % of test to training data

[training,traininglabels,testing,testinglabels,numberOfTests]...
    = getPartitionedData(maindata,numbers,testRatio,total);

graph = [];
for k = ks
    success = 0;
    
    [ids d] = knnsearch(training,testing,'k',k);
    if k~=1
        success = sum(mode(traininglabels(ids(:,:))')'==testinglabels);
    else
        success =  sum(traininglabels(ids(:,:))==testinglabels);
    end
    acc = success / numberOfTests;
    graph = [graph; [k acc]];
end

%figure;
if ~exist('noplot','var')
    plot(graph(:,1),graph(:,2));
    title('accuracy vs k');
    xlabel('k');
    ylabel('accuracy');
end