valami = function() {
	set.seed(100)
	x = 0.2 * qgamma(runif(100), 1.2)
	hist(x, col="green")
	a =c (0, 0.5, 1.5, 3.5, 6, 20)
	g = vector()
	for (i in 1:length(a)-1)
		p[i] = pexp(a[i+1]) - pexp(a[i])

}