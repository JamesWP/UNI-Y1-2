a = [];
%folder = './graphVersion1';
folder = './graphBefore';
files = dir(strcat(folder,'/graph*.mat'));
for i = 1:size(files);
  load(fullfile(folder,files(i).name));
  a = [a; graph(:,1)'];
end
data = a;
dmin = min(data);
dmax = max(data);
dstddev = std(data);
dmean = mean(data);
x = 1:10;

errorbar(x,dmean,dstddev);
xlabel('k');
ylabel('accuracy');
