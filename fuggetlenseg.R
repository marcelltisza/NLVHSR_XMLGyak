fugetlenseg = function(A) {
	sor =c ()
	oszlop =c ()
	for (i in 1:length(A)) {
		for (j in 1:length(dim(A)[2])) {
			oszlop[i] = oszlop[i] + A[c(i),c(j)]
		}	
	}

	for (i in 1:length(dim(A)[2])) {
		for (j in 1:length(A)) {
			sor[i] = sor[i] + A[c(i),c(j)]
		}	
	}
	
	sum = 0
	for (i in 1:length(A)) {
		for (j in 1:length(dim(A)[2])) {
			sum = sum + (A[c(i),c(j)]
		}	
	}

}