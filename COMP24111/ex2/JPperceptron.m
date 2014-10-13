%% Perception classifier 
% clasification = JPperceptron( k, x, data, datalabels )
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
function classification = JPperceptronn(x, data, datalabels,classes)


%training

%initialise weights to random numbers in range -1 to +1 
numFeatures = size(x,2);
t = rand()*2-1;
w = rand(1,numFeatures).*2-1;
itterations = 1:30;
learningrate = 0.05;
exampleIndexes = 1:size(data,1);
for n = itterations
    for ei = exampleIndexes
        example = data(ei,:);
        class = datalabels(ei)==classes(1);
        output = neuron(example,w,t);
        diff = (class - output);
        w = learningUpdate(w,example,diff,learningrate);
        t = t + learningrate * diff * -1;
    end
end

%testing
classification = [];
for test = x'
    output = neuron(test',w,t);
    if output==1
        class = classes(1);
    else
        class = classes(2);
    end
    classification = [classification; class];
end


end

%% NEURONLEARNING
function newWeights = learningUpdate(ow,example,diff,learningrate)
    %ALGO
    %new weight = old weight + 0.1 * ( trueLabel ? output ) * input
    
    newWeights = ow + learningrate .* diff .* example;
end

%% NEURON
% x - data to be tested - 1xX double features
% w - weights for features- 1xX double featureweights 
% t - threashold - double threashold
% classes - [ nofireclass fireclass]
function class = neuron(x,w,t)
    if sum(x.*w)>t
        class = 1;
    else
        class = 0;
    end
end
