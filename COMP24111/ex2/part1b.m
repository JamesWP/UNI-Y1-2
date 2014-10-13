% from part1:
% training 
% labels

ks = 1:10;
numExamples = size(training,1);

X = [];
Y = [];

for k = ks
	classes = JPknearest(k,training,training,labels,true);
	acc = sum(classes == labels)/numExamples;
	X = [X k];
	Y = [Y acc]; 
end

figure;
plot(X,Y);
