package rabbitmq.publishsubscribe;

import java.io.IOException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class Test2 {
	public static void main(String[] args) throws Exception{
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
//		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();	
		
		//1.定义交换机 2.定义队列 3.绑定
		c.exchangeDeclare("logs", "fanout");
		
		DeliverCallback deliverCallback = new DeliverCallback() {
			@Override
			public void handle(String arg0, Delivery message) throws IOException {
				String msg = new String(message.getBody());
				System.out.println("收到:"+msg);
			}
		};
		
		CancelCallback cancelCallback = new CancelCallback() {
			@Override
			public void handle(String arg0) throws IOException {
			}
		};
		
		c.queueDeclare();//随机命名,false,true,true,null
		//						     非持久,独占,自动删除
		String queue = c.queueDeclare().getQueue();
		c.queueBind(queue, "logs", "");
		c.basicConsume(queue, true,deliverCallback,cancelCallback);
	}
}
