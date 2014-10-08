howmanytimes = 1:30;
ks = 1:5;
results = [];
for it = howmanytimes
    graph = testpartitionedData([1:9],ks,maindata);
    result = graph(:,2)';
    results = [results; result];
end

data = results;
dmin = min(data);
dmax = max(data);
dstddev = std(data);
dmean = mean(data);
x = ks;

errorbar(x,dmean,dstddev);
xlabel('k');
axis([min(ks)-1 max(ks)+1 0.88 1]);
ylabel('accuracy');
