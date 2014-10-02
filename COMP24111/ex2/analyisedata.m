a = [];
files = dir('graph*.mat');
for i = 1:size(files);
  load(fullfile('.',file(i).name));
  a = [a; graph(:,1)'];
end
data = a
dmin = min(data)
dmax = max(data)
dmean = mean(data)
x = 1:10

errorbar(x,dmean,dmean-dmin,dmax - dmean)
