numbers = [1:10];
allDigits = [];
for number = numbers
    allDigits = [ allDigits; repmat(number,1,1) mean(applymyextract(maindata(:,:,number)')) std(applymyextract(maindata(:,:,number)')) ];
end
%%

feats = size(allDigits(1,2:end),2)/2;
feat = 2;

    nums = allDigits(:,1);
    means = allDigits(:,feat+1);
    stds = allDigits(:,feats+feat+1);
    errorbar(nums,means,stds);

xlabel('Digit 1-9 0');
ylabel('feature value (mean)');
title('Feature evaluation');
