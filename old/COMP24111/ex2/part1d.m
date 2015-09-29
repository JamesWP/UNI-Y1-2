noplot = true;

times = 1:10;

graphs = [];
for time = times
    part1c;% this defines graph and ks
    graphs = [graphs; graph(:,2)'];
end

meanval = mean(graphs);
stdval = std(graphs);

errorbar(ks,meanval,stdval);
title('accuracy vs k 10 time average');
xlabel('k');
ylabel('accuracy');