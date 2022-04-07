illeszkedes = function(k, p, N) {
	A = 0
	for (i in 1:length(p)) {
		A = A + (k[i] - N * p[i])^2 / (p[i] * N)
	}
	A
}