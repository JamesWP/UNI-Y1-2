load usps_main.mat

digits = [3 8];

training = [];
labels = [];
for digit = digits
	training = [training; maindata(:,randperm(500,100),digit)'];
	labels = [labels; repmat(digit,1,100)'];
end
showdata(training,labels);
