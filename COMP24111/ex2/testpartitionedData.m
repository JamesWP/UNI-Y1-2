function graph = testpartitionedData(numbers,ks,maindata)

%numbers = [3 8];
howmany = 100;
%ks = 1:10;
useMyFeatures = true;
[training,labels] = getTestData(numbers,howmany,maindata);
[testdata,testlabels] = getTestData(numbers,howmany,maindata);

if useMyFeatures == false
    training = applymyextract(training);
    testdata = applymyextract(testdata);
end

training = double(training);
testdata = double(testdata);


notests = size(testdata,1);
graph = [];
for k = ks
    success = 0;
    %for tdi = 1:notests
    %    td = testdata(tdi,:);
    %    result = knearest(k,td,training,labels);
    %    success = success + (result==testlabels(tdi));
    %end
    
    [ids d] = knnsearch(training,testdata,'k',k);
    if k~=1
        success = sum(mode(labels(ids(:,:))')'==testlabels);
    else
        success =  sum(labels(ids(:,:))==testlabels);
    end
    acc = success / notests;
    graph = [graph; [k acc]];
end

%filename = datestr(now,'yyyy-mm-dd_HH:MM:ss');
%save(strcat('graph',filename,'.mat'),'graph');

%plot(graph(:, 2),graph(:,1));
%ylabel('accuracy');
%xlabel('k');
