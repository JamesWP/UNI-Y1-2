function d = applymyextract(ds)
d = [];
for x = 1:size(ds,1)
  d = [d ; extractmyfeatures(ds(x,:))];
end
