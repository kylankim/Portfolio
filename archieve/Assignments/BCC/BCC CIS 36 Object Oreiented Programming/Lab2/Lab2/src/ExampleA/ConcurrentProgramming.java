package ExampleA;

import java.util.concurrent.RecursiveTask;

public class ConcurrentProgramming extends RecursiveTask<Integer>{

    private static final long serialVersionUID = 1L;

    //Id of the thread
    private int id = -1;

    //The amount of the work that one thread can handle at once
    private int workLoad = 0;

    //The start point of the work of thread
    private int low = 0;

    //End point of the work of thread
    private int high = 0;

    //The location of the first point
    private Point[] p;

    //The location of the second point
    private Point[] q;

    //Number of threads
    private int taskNum;

    //How much each thread can handle at once
    private int block = 0;

    //Constructor of the Program putting on the starting point and end point of each thread
    public ConcurrentProgramming(int id,Point[] p, Point[] q, int low, int high, int taskNum) {
        try {
            this.id = id;
            this.p = p;
            this.q = q;
            this.low = low;
            this.high = high;
            this.workLoad = high - low;
            this.taskNum = taskNum;
            block = p.length / (p.length / taskNum);
        } catch (ArithmeticException e) { }
    }

    //Construnctor of the program w/o defining the starting and end point of each thread
    public ConcurrentProgramming(int id,Point[] p, Point[] q,int taskNum) {
        try {
            this.id = id;
            this.p = p;
            this.q = q;
            this.low = 0;
            this.high = q.length;
            this.workLoad = high - low;
            this.taskNum = taskNum;
            block =p.length / ((int)Math.ceil(p.length)/ taskNum);
        }catch(ArithmeticException e) { }
    }


    //Method to find the distance between two points
    public double distance(Point a, Point b) {
        double result = (double)Math.sqrt(   Math.pow((a.getx()-b.getx()), 2) +
                Math.pow((a.gety()-b.gety()), 2) +
                Math.pow((a.getz()-b.getz()), 2)
        );
        return result;
    }


    @Override
    protected Integer compute() {
        //System.out.println(id+" "+workLoad+" taskNum"+taskNum+" low: "+ low + "high" + high);
        if(workLoad > taskNum){
            int mid = low + (block);
            //System.out.println("mid "+mid);
            ConcurrentProgramming left  = new ConcurrentProgramming(id,p,q, low, mid,this.taskNum);
            ConcurrentProgramming right = new ConcurrentProgramming(id+1,p,q, mid, q.length,this.taskNum);

            left.fork();
            left.join();
            right.compute();
        } else {
            for(int i = low; i < high; i++) {
                double dis = distance(p[i],q[i]);
            }
        }
        return null;
    }
}