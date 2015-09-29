function d = applymyextract(ds)
  d = [];
  for x = 1:size(ds,1)
    d = [d ; extractmyfeatures(ds(x,:))];
  end
end


function x = extractmyfeatures( digdata )
  dreshape = reshape(digdata,16,16);
  %a1 = mean(dreshape);
  %a2 = mean(dreshape');
  x = [ mean(dreshape)  mean(dreshape') ];
  %x = [ a1 a2 ];
end

function [data,labels] = getTestData(numbers,howmany,maindata)
  data = [];
  labels = [];
  for n = numbers
    d = maindata(:,randperm(500,howmany),n);
    data = [data; d'];
    labels = [labels; n.*ones(howmany,1)];
  end
end
