package A;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerService {

    private static Point[] p = new Point[10000000];
	private static Point[] q = new Point[10000000];
  	private static ArrayList<Integer> list = new ArrayList<Integer>();
    public void startWork(){
        // Start work with ExecutorService

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
    		tasks.invoke();
        }
        	
        
    		
      
    }

    public static void main(String args[]){
    	CustomerService customerService = new CustomerService();
    	list.add(100);
    	list.add(1000);
    	list.add(10000);
    	list.add(100000);
    	list.add(1000000);
        customerService.startWork();

    }
}