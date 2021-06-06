package ExampleC;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Test {

    static int sum = 0;
    static ArrayList<Integer> list = new ArrayList<Integer>();
    static ArrayList<Date> dlist = new ArrayList<Date>();

    public static void main (String[] args) {
        IntegerListProcessor li = new IntegerListProcessor();
        ListProcessor.forEach(li.arrayList, (c)->{
            System.out.print(c + " / ");
        });
        ListProcessor.forEach(li.arrayList, (c)->{
            sum += c;
        });
        System.out.println("\n--------pow--------");
        ListProcessor.forEach(li.arrayList, (c)->{
            System.out.print("pow : "+Integer.parseInt(String.valueOf(Math.round(Math.pow(c,2)))) +" / ");
        });
        System.out.println("\n--------sum--------");
        System.out.print("sum : " + sum);
        ListProcessor.forEach(li.arrayList, (c)->{
            list.add(c);
        });
        int x = 100;
        int y = 400;
        System.out.println("\n--------between "+x+" and "+y+" --------");
        for(int i = 0; i< list.size(); i++) {
            if(x < list.get(i) && list.get(i) < y) {
                System.out.print(" "+list.get(i));
            }
        }
        System.out.println("\n-------- Date --------");
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        DateListProcessor datelist = new DateListProcessor();
        ListProcessor.forEach(datelist.dateList , (c)->{
            String release_Dt_start=formatter.format(c);
            dlist.add(c);
            System.out.print(release_Dt_start+" / ");
        });
        Date d = new java.util.Date();
        System.out.println("\n-------- Date after today --------");

        for(int i = 0 ; i < dlist.size(); i++) {
            if(d.getTime() < dlist.get(i).getTime()) {
                String release_Dt_start=formatter.format(dlist.get(i));
                System.out.print(release_Dt_start+" / ");
            }
        }
        System.out.println("\n-------- Not Date after today --------");

        for(int i = 0 ; i < dlist.size(); i++) {
            if(!(d.getTime() < dlist.get(i).getTime())) {
                String release_Dt_start=formatter.format(dlist.get(i));
                System.out.print(release_Dt_start+" / ");
            }
        }

    }
}
