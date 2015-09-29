%  K-nearest Neighbour classifier
%  improved to use built in knnsearch by JP

% clasification = JPknearest( k, x, data, datalabels )
% PARAMS
% k - int - the value of k
% x - MxN matrix of values to clasify
%       M : the number of of things to test
%       N : the number of features per example
% data - MxN matrix of the training data
%       M : the number of of examples
%       N : the number of features per example
% datalabels - Mx1 correct labels for data
%       must have the same number of rows as data
% RETURNS
% classification Mx1 array of the predicted class of each of x
function classification = JPknearest(k, x, data, datalabels, ignorefirst)

% optional parameter parseing
if (~exist('ignorefirst', 'var'))
	ignorefirst = false;
end

% if the first value is to be ignored (usefull when testing with testdata)
if ignorefirst == true
	startindex = 2;
	% ensure we get at least 2 indexes so we can skip one
	if k==1 
		k=2; 
	end
else
	startindex = 1;
end

% knnsearch takes arguments as doubles not uint8's
data = double(data);
x = double(x);

% search the data for the nearest k points in data
% ranking of distance calcs:
%   euclidiean (default) score: 14.5502
%   minkowski            score: 14.4444
%   cosine               score: 14.3030
ids = knnsearch(data,x,'k',k);



% lookup the labels for the nearest elements
% if k is not 1 then find the most common class and return that
% else just lookup the class directly
if k~=1
  classification = mode(datalabels(ids(:,startindex:k)),2);
else
  classification = datalabels(ids(:));
end

