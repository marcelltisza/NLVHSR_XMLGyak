m = function(x, y) {
	sum = 0
print(sum)
	for (i in 1:length(x)){
		sum = sum + ((x[i] - mean(x)) * (y[i] - mean(y)))}

print(x)
	sum / (length(x) - 1)
}

szoras = function(x) {
 	sum = 0
 	for (i in 1:length(x))
 		sum = sum + (x[i] - mean(x)) * (x[i] - mean(x))
	sum / (length(x) - 1)
}
