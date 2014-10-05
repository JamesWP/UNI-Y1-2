function x = extractmyfeatures( digdata )
dreshape = reshape(digdata,16,16);
x = [ sum(dreshape(1:2:16),1)  sum(dreshape(:,1:2:16)',1) ];

