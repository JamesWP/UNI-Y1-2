function x = extractmyfeatures( digdata )
dreshape = reshape(digdata,16,16);
%a1 = mean(dreshape);
%a2 = mean(dreshape');
x = [ mean(dreshape)  mean(dreshape') ];
%x = [ a1 a2 ];

