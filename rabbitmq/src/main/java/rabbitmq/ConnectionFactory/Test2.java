package rabbitmq.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
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
		c.exchangeDeclare("direct_logs",BuiltinExchangeType.DIRECT);
		String queue = c.queueDeclare().getQueue();
		System.out.println("输入用逗号隔开的多个绑定键");
		String s = new Scanner(System.in).nextLine();
		//["a","b","c"]
		String[] a = s.split(",");
		for (String key : a) {
			c.queueBind(queue, "direct_logs", key);
		}
		DeliverCallback deliverCallback = new DeliverCallback() {
			@Override
			public void handle(String consumerTag, Delivery message) throws IOException {
				String msg = new String(message.getBody());
				String key = message.getEnvelope().getRoutingKey();
				System.out.println(key+"------");
				System.out.println(msg);
			}
		};
		CancelCallback cancelCallback = new CancelCallback() {
			@Override
			public void handle(String arg0) throws IOException {
			}
		};
		c.basicConsume(queue, true,deliverCallback,cancelCallback);
	}
}
