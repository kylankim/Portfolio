package ExampleA;

import java.util.ArrayList;
import java.util.Random;

public class CustomerService {

    private static Point[] p = new Point[100000];
    private static Point[] q = new Point[100000];
    private static ArrayList<Integer> list = new ArrayList<Integer>();

    //Strating Time
    private long startTime = 0;

    //Total time spent on the programm
    private static long totalTime = 0;


    public static ArrayList<Integer> getList() {
        return list;
    }

    public static long getTotalTime() {
        return totalTime;
    }

    public void startWork(){
        int threadIdSeq = 0;
        Random oRandom = new Random();
        for(int i = 0; i < p.length; i++) {
            double x1 = oRandom.nextDouble();
            double y1 = oRandom.nextDouble();
            double z1 = oRandom.nextDouble();

            double x2 = oRandom.nextDouble();
            double y2 = oRandom.nextDouble();
            double z2 = oRandom.nextDouble();
            p[i] = new Point(x1,y1,z1);
            q[i] = new Point(x2,y2,z2);
        }
        for(int i = 0 ; i < list.size(); i++) {
            ConcurrentProgramming tasks = new ConcurrentProgramming(threadIdSeq,p,q,list.get(i));
            startTime = System.currentTimeMillis();
            tasks.invoke();
            System.out.println((System.currentTimeMillis()-startTime)+" Millisec was taken");
            totalTime += (System.currentTimeMillis()-startTime);

        }

    }
}