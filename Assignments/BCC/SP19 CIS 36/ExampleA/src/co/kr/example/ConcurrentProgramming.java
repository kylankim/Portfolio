package co.kr.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class ConcurrentProgramming extends RecursiveTask<Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private int count = 0;
	private int workLoad = 0;
	private int low = 0;
	private int high = 0;
	private Point[] p;
	private Point[] q;
	private int taskNum;
	private int block = 0;

	public ConcurrentProgramming(int id,Point[] p, Point[] q, int low, int high, int taskNum) {
		this.id = id;
		this.p = p;
		this.q = q;
		this.low = low;
		this.high = high;
		this.workLoad = high - low;
		this.taskNum = taskNum;
		block =p.length / (p.length / taskNum)  ;
	}
	
	public ConcurrentProgramming(int id,Point[] p, Point[] q,int taskNum) {
		this.id = id;
		this.p = p;
		this.q = q;
		this.low = 0;
		this.high = q.length;
		this.workLoad = high - low;
		this.taskNum = taskNum;
		block =p.length / ((int)Math.ceil(p.length)/ taskNum)   ;
	}
	
	public void run() {
		
	}
	
	
	public double distance(Point a, Point b) {
		double result = (double)Math.sqrt(	Math.pow((a.getx()-b.getx()), 2) + 
											Math.pow((a.gety()-b.gety()), 2) + 
											Math.pow((a.getz()-b.getz()), 2)
								);
		return result;
	}

	@Override
	protected Integer compute() {
		System.out.println(id+" "+workLoad+" taskNum"+taskNum+" low: "+ low + "high" + high);
		
		if(workLoad > taskNum){
			int mid = low + (block);
			System.out.println("mid "+mid);
			
			ConcurrentProgramming left  = new ConcurrentProgramming(id,p,q, low, mid,this.taskNum);
			ConcurrentProgramming right = new ConcurrentProgramming(id+1,p,q, mid, q.length,this.taskNum);
			
			left.fork();
			left.join();
			right.compute();
		}else {
			System.out.println("asd");
			for(int i = low; i < high; i++) {
				System.out.println("asd");
				double dis = distance(p[i],q[i]);
				System.out.print("\n"+ id + "번 쓰레드 "+ (i) +" 번 좌표 계산 동작 중..." );
				System.out.print("두 점 사이의 거리는"+ dis + "입니다.\n");
				
				Random r = new Random(System.currentTimeMillis());
				try {
					long s = r.nextInt(100);
					Thread.sleep(s);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} 
		return null;
	}
	
	
}
