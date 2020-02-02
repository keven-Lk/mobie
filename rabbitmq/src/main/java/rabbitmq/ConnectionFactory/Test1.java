package rabbitmq.ConnectionFactory;

import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Test1 {
	public static void main(String[] args) throws Exception {
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
//		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();	
		
		//定义交换机
		c.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT);
		
		while(true) {
			System.out.println("输入消息:");
			String msg = new Scanner(System.in).nextLine();
			System.out.println("输入路由键");
			String key = new Scanner(System.in).nextLine();
			
			c.basicPublish("direct_logs", key,//路由键
					null, msg.getBytes());
			System.out.println("-------消息已发送---------------------");
		}
	}
}
