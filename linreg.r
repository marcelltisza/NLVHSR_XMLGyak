linreg = function(X, Y) {
	a = m(x, y) / szoras(x)
	b = mean(y) - mean(x) * m(x, y) / szoras(x)
	
	print(a)
	print(b)
}

szoras = function(x) {
 	sum = 0
 	for (i in 1:length(x))
 		sum = sum + (x[i] - mean(x)) * (x[i] - mean(x))
 	return (sum / (length(x) - 1))
}

m = function(x, y) {
	sum = 0
	for (i in 1:length(x)){
		sum = sum + ((x[i] - mean(x)) * (y[i] - mean(y)))}
	 return (sum / (length(x) - 1))
}