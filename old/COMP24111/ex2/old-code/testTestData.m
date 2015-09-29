function testTestData(numbers,ks,maindata)
ofeach = 100;
[images,labels] = getTestData(numbers,ofeach,maindata);
noimages = size(labels,1);
%ks = 1:100;

graph = [];

for k = ks
  t = 0;
  for i = 1:noimages
     noi = 1:noimages;
     noi(i) = [];
     res = knearest(k,images(i,:),images(noi,:),labels(noi))==labels(i);
     t = t + res;
  end
  graph = [graph; [sum(t)/noimages] k];
end

results = graph;
scatter(results(: , 2),results(:,1));
ylabel('accuracy');
xlabel('k');
