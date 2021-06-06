public class HW1 {
	public int max(int[] a) {
		int index = 0;
		int max_value = a[0];
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max_value) {
				max_value = a[i];
				index = i;
			} 
		}
		return a[index];
	}

	public boolean threeSum(int[] a){
		for (int i = 0; i < a.length; i++){
			for (int k = 0; k < a.length; k++){
				for (int m = 0; m < a.length; m++){
					if (a[i] + a[k] + a[m] == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean threeSumDistinct(int[] a){
		for (int i = 0; i < a.length; i++){
			for (int k = 0; k < a.length; k++){
				for (int m = 0; m < a.length; m++){
					if ((a[i] + a[k] + a[m] == 0) && (i != k) && (k != m) && (i != m)) {
						return true;
					}
				}
			}
		}	
		return false;
	}	
}

