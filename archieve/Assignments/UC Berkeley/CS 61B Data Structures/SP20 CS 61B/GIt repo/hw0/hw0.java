public class hw0 {

    public static int max(int[] a) {
        if (a.length != 0) {
            int result = a[0];
            for(int i : a) {
                if (i > result){
                    result = i;
                }
            }
            return result;
        }
        return 0;
    }

    public static boolean threeSum(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int g = 0; g < a.length; g++) {
                    if (a[i] + a[j] + a[g] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean threeSumDistinct(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                for (int g = i+2; g < a.length; g++) {
                    if (a[i] + a[j] + a[g] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
