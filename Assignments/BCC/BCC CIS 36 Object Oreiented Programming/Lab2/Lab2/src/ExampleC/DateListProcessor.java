package ExampleC;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class DateListProcessor {
    ArrayList<Integer> randNumList;
    ArrayList<Date> dateList;


    //Adding the random number to the current date.
    public DateListProcessor() {
        randNumList = new ArrayList<Integer>();
        dateList = new ArrayList<Date>();

        for(int i = 0; i < 25; i++) {
            randNumList.add(randomNum());
        }
        Calendar cal = Calendar.getInstance();
        for(int i = 0; i < randNumList.size(); i++) {
            cal.add(Calendar.DAY_OF_MONTH, +randNumList.get(i));
            Date currentTime=cal.getTime();
            dateList.add(currentTime);
        }



    }

    //Choose the random number from -50 to 50
    private int randomNum() {
        Random random = new Random();

        int randNum = random.nextInt(100) - 50;
        int result = randNum;
        for(int j = 0 ; j< randNumList.size(); j++) {
            if(randNum == randNumList.get(j)) {
                result = randomNum();
            }else {
                result = randNum;
            }
        }
        return result;
    }
}
