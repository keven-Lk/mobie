package rabbitmq.rpc;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class RPCServer {
	public static void main(String[] args) throws Exception{
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
//		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();	
		
		c.queueDeclare("rpc_queue",false,false,false,null);
		c.queuePurge("rpc_queue");//清空队列
		
		DeliverCallback deliverCallback = new DeliverCallback() {
			@Override
			public void handle(String arg0, Delivery message) throws IOException {
				//得到的消息是int整数,用来求第几个斐波那契数
				String msg = new String(message.getBody());
				int n = Integer.parseInt(msg);
				long r = f(n);//求第n个斐波那契数
				
				//取返回队列名和关联id
				String queue = message.getProperties().getReplyTo();
				String id = message.getProperties().getCorrelationId();
				
				BasicProperties prop = 
						new BasicProperties
						.Builder()
						.correlationId(id)
						.build();
				c.basicPublish("", queue, prop, (""+r).getBytes());
			}

			private long f(int n) {
				if(n==1 || n==2) {
					return 1;
				}
				return f(n-1)+f(n-2);
			}
		};
		
		CancelCallback cancelCallback = new CancelCallback() {
			@Override
			public void handle(String arg0) throws IOException {
			}
		};
		c.basicConsume("rpc_queue", true,deliverCallback,cancelCallback);
	}
}
