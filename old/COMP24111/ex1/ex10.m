%reciprocol
sa = size(A);
cols = sa(1)
rows = sa(2)
AR = [];
for col = 1:cols
    for row = 1:rows
       AR(col,row) = 1/A(col,row);
    end;
end;
AR
sum(AR)


%without 
sum(1./A)