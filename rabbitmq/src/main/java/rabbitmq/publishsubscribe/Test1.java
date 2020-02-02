package rabbitmq.publishsubscribe;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Test1 {
	public static void main(String[] args) throws Exception{
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
//		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();		
		
		//定义交换机,不存在,创建,存在,空操作
		c.exchangeDeclare("logs", "fanout");
		
		while(true) {
			System.out.println("输入:");
			String msg = new Scanner(System.in).nextLine();
			
			//只指定交换机.不用指定队列
			//fanout交换机向所有绑定的队列进行群发
			c.basicPublish("logs", "", null, msg.getBytes());
		}
	}
}
