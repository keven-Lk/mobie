package rabbitmq.rpc;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RPCClient {
	public long call(int n) throws Exception{
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
//		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();	
		
		//返回队列的队列名和关联id
		String queue = c.queueDeclare().getQueue();
		String id = UUID.randomUUID().toString();
		
		BasicProperties prop = 
				new BasicProperties
				.Builder()
				.replyTo(queue)
				.correlationId(id)
				.build();
		c.basicPublish("", "rpc_queue", prop, (""+n).getBytes());
		
		ArrayBlockingQueue<Long> q = new ArrayBlockingQueue<Long>(10);
		
		DeliverCallback deliverCallback = new DeliverCallback() {
			
			@Override
			public void handle(String arg0, Delivery message) throws IOException {
				String s = new String(message.getBody());
				long r = Long.parseLong(s);
				q.offer(r);
			}
		};
		
		CancelCallback cancelCallback = new CancelCallback() {
			@Override
			public void handle(String arg0) throws IOException {
			}
		};
		
		c.basicConsume(queue, true,deliverCallback,cancelCallback);
		return q.take();
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("输入求第几个斐波那契数");
		int n = new Scanner(System.in).nextInt();
		
		RPCClient client = new RPCClient();
		long r = client.call(n);
		System.out.println(r);
	}
}
