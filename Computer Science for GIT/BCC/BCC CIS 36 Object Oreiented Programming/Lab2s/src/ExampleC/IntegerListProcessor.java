package ExampleC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerListProcessor extends ListProcessor{
    public List<Integer> arrayList;
    public IntegerListProcessor() {
        arrayList = new ArrayList<Integer>();
        for(int i = 0; i < 25; i++) {
            arrayList.add(randomNum());
        }
    }

    private int randomNum() {

        Random random = new Random();

        int randNum = random.nextInt(1000);
        int result = randNum;
        for(int j = 0 ; j< arrayList.size(); j++) {
            if(randNum == arrayList.get(j)) {
                result = randomNum();
            }else {
                result = randNum;
            }
        }
        return result;
    }


}
