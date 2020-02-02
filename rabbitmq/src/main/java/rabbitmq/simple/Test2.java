package rabbitmq.simple;

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
		//f.setPort(5672); 默认可以省略
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();
		
		//定义队列
		c.queueDeclare("helloworld",false,false,false,null);
		
		//处理消息的回调对象
		DeliverCallback deliverCallback = new DeliverCallback() {
			@Override
			public void handle(String consumerTag, Delivery message) throws IOException {
				byte[] a = message.getBody();
				String s = new String(a);
				System.out.println("收到"+s);
			}
		};
		
		//取消接收消息的回调对象
		CancelCallback cancelCallback = new CancelCallback() {
			@Override
			public void handle(String arg0) throws IOException {
			}
		};
		
		//接收消息
		c.basicConsume("helloworld",true, deliverCallback, cancelCallback);
	}
}
