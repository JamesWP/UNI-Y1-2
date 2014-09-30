function [data,labels] = getTestData(numbers,howmany,maindata)
  data = [];
  labels = [];
  for n = numbers
    d = maindata(:,randperm(500,howmany),n);
    data = [data; d'];
    labels = [labels; n.*ones(howmany,1)];
  end
end
