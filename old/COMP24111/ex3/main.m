close all;
clear all;
fname = input('Enter a filename to load data for training/testing: (1,2,3,4)');
names = cellstr(['av2_c2.mat'; 'av3_c2.mat'; 'av7_c3.mat'; 'avc_c2.mat']);
load(names{fname});

% Put your NB training function below
[ Classes, Values, Prior, Likelihood ] = NBTrain(AttributeSet, LabelSet); % NB training
% Put your NB test function below
[predictLabel, accuracy] = NBTest(Classes, Values, Prior, Likelihood, testAttributeSet, validLabel); % NB test

fprintf('********************************************** \n');
fprintf('Overall Accuracy on Dataset %s: %f \n', names{fname}, accuracy);
fprintf('********************************************** \n');