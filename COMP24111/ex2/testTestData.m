function testTestData(numbers,ks,maindata)

[images,labels] = getTestData(numbers,100,maindata);
noimages = size(labels,1);
%ks = 1:100;

graph = [];

for k = ks
  t = 0;
  for i = 1:noimages
     res = knearest(k,images(i,:),images,labels)==labels(i);
     t = t + res;
  end
  graph = [graph; [sum(t)/noimages] k];
end

results = graph;
scatter(results(: , 2),results(:,1));
ylabel('accuracy');
xlabel('k');
