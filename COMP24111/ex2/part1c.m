numbers = [3 8];
ks = 1:10;
howmany = 100;

total = 500;

all = [];
alllabels = [];
for n = numbers
		all = [all; maindata(:,randperm(total),n)'];
		alllabels = [alllabels; repmat(n,1,total)'];
end

all

training = all(;
traininglabels = [];
testing = [];
testinglabels = [];

return;
%%
notests = size(testdata,1);
graph = [];
for k = ks
    success = 0;
    
    [ids d] = knnsearch(training,testdata,'k',k);
    if k~=1
        success = sum(mode(labels(ids(:,:))')'==testlabels);
    else
        success =  sum(labels(ids(:,:))==testlabels);
    end
    acc = success / notests;
    graph = [graph; [k acc]];
end

