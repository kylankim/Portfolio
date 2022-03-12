public class LeapYear {

	static boolean isLeapYear(int year) {
        if ((year % 400 == 0) || (year % 100 != 0) && (year % 4 == 0))
            return true;
        else 
            return false;
    }

	public static void main (String[] args){
		int year = 2000;
		if (isLeapYear(year)) {
			System.out.println(year + " is a leap year");
		}
		else
			System.out.println(year + " is not a leap year");
	}
}