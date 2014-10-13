function labels = clasify(data,images,truelabels)
  labels = [];
  for d = data
    class = knearest(k,extractmyfeatures(d),images,truelabels);
    labels = [labels; class];
  end
end
