package rabbitmq.workqueue;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Test1 {
	public static void main(String[] args) throws Exception{
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
//		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();
		
		//定义队列
		c.queueDeclare("task_queue",true,false,false,null);
		
		while (true) {
			System.out.println("输入:");
			String msg = new Scanner(System.in).nextLine();
			c.basicPublish("", "task_queue", 
					MessageProperties.PERSISTENT_TEXT_PLAIN, 
					msg.getBytes());
			System.out.println("已发送消息");
		}
		
	}
}
