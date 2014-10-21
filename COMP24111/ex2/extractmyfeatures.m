function x = extractmyfeatures( digdata )
d = reshape(digdata,16,16);

%% VERSION8


%x = mean(diff(sum(d,2)));

%% VERSION7 - version 4 minus rubish ones
%this got average score of 14.4 with 6 features
%a1 = mean(d(:,2:3:10));
%b1 = mean(d(2:3:10,:),2)';
%a2 = mean(d(:,1:3:9));
%b2 = mean(d(1:3:9,:),2)';

%a = a1 + a2;
%b = b1 + b2;
%5 6 7 8 9 10
%x2 = [ a b ];
%x2 = x2([3 4 5 6]); % these are the best features
%x = [x x2];

%% VERSION6

%t = sum(sum(d(1:8,:)));
%b = sum(sum(d(9:16,:)));

%tl = mean(mean(d(1:8,1:8)));
%tr = mean(mean(d(9:16,1:8)));
%bl = mean(mean(d(1:8,9:16)));
%br = mean(mean(d(9:16,9:16)));

%m = [tl tr; bl br];

%dm = (tl-br) / (tr-bl);

%x = [t/b dm];


%% VERSION5
% mean score 22.7356 with 4 features

%quadrants
tl = d(1:8,1:8);
tr = d(9:16,1:8);
bl = d(1:8,9:16);
br = d(9:16,9:16);

%means
x = [ mean(mean(tl)) mean(mean(tr)) mean(mean(bl)) mean(mean(br))];

%% VERSION4
%this got average score of 14.4 with 6 features

a = [mean(mean(d(1:5,:)))  mean(mean(d(6:10,:))) mean(mean(d(11:15,:)))];
b = [mean(mean(d(:,1:5)))  mean(mean(d(:,6:10))) mean(mean(d(:,11:15)))];
x = [x a  b ];

%% VERSION3
%this got average score of 14.4 with 6 features
%x = [ mean(d(:,2:3:10))  mean(d(2:3:10,:)') ];

%% VERSION2
%this got average score of 9
% x = [ mean(d(:,2:2:10))  mean(d(2:2:10,:)') ];

%% VERSION1
% this got average score of 3 
% x = [ mean(dreshape)  mean(dreshape') ];

%% VERSION0
% initial code
% x = digdata