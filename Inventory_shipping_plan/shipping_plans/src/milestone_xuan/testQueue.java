package milestone_xuan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class testQueue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> l1=new ArrayList<String>();
		l1.add("a");
		List<String> l2=new ArrayList<String>();
		l2.add("b22");
		
		List<String> l3=new ArrayList<String>();
		l3.add("cs");
		
		
		
		
		Comparator<List<String>> c1=new Comparator<List<String>>(){

			@Override
			public int compare(List<String> o1, List<String> o2) {
				return o1.size()-o2.size();
			}
			
		};
		Queue<List<String>> q1=new PriorityQueue<List<String>>(c1);
		
		q1.add(l1);
		q1.add(l3);
		q1.add(l2);
		while(!q1.isEmpty()){
			System.out.println("line 38:"+q1.poll());
		}
		
		
		q1.add(l1);
		q1.add(l2);
		q1.add(l3);
		
		Queue<List<String>> q2=new PriorityQueue<List<String>>(c1);
		while(!q1.isEmpty()){
			q2.add(q1.poll());
			System.out.print(q2.poll());
		}
		
		

	}

}
