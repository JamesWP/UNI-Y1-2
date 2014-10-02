

numbers = [3 8];
howmany = 250;
ks = 1:10;

[training,labels] = getTestData(numbers,howmany,maindata);
[testdata,testlabels] = getTestData(numbers,howmany,maindata);

notests = size(testdata,1);
graph = [];
for k = ks
    success = 0;
    for tdi = 1:notests
        result = knearest(k,testdata(tdi,:),training,labels);
        success = success + (result==testlabels(tdi));
    end    
    acc = success / notests;
    graph = [graph; [acc k]];
end
filename = datestr(now,'yyyy-mm-dd_HH:MM:ss');
save(strcat('graph',filename,'.mat'),'graph');
plot(graph(:, 2),graph(:,1));
ylabel('accuracy');
xlabel('k');
