function [ Classes, Values, Prior, Likelihood ] = NBTrain( AttributeSet, LabelSet )

%the distinct values
values = unique(AttributeSet);
%count of features
features = size(AttributeSet,2);
%the distinct classes
classes = unique(LabelSet);
%total examples
total = size(LabelSet,1);

Prior = [];
for class = 1:size(classes,1)
    classMask = find(LabelSet == classes(class)); % the indecies of the examples in this class
    classtotal = size(classMask,1);
    Prior = [Prior; classtotal / total];
    
    attrProbs = [];
    
    for feature = 1:features
        valuetotals = histc(AttributeSet(classMask,feature),values)'; % count occurances of value in examples for this class
        attrProb = valuetotals ./ classtotal;
        attrProbs = [attrProbs; attrProb];
    end
    
    Likelihood(class,:,:) = attrProbs;
end

Classes = classes;
Values = values;


end

