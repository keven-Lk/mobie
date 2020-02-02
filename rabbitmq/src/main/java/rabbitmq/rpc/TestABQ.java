package rabbitmq.rpc;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class TestABQ {
	public static void main(String[] args) {
		ArrayBlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
		new Thread() {
			public void run() {
				System.out.println("输入");
				String s = new Scanner(System.in).nextLine();
				q.offer(s);
			};
		}.start();
		
		new Thread() {
			public void run() {
				System.out.println("线程2-等待获取数据");
				try {
					String s = q.take();//没有则等待
					System.out.println("线程二数据"+s);
				} catch (Exception e) {
					// TODO: handle exception
				}
			};
		}.start();
	}
}
