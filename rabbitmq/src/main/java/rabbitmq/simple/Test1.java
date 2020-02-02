package rabbitmq.simple;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Test1 {
	public static void main(String[] args) throws Exception {
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		
		//建立了连接
		Connection con = f.newConnection();
		//在连接中,创建一个信道
		Channel c = con.createChannel();
		
		//定义队列,如果队列不存在会新建,如果已经存在相当于空操作
		/*
		 * false-持久队列,服务器重启,队列是否任然存在
		 * false-独占队列,只能被一个客户端使用
		 * false-是否自动删除,没有客户端使用该队列时,自动删除
		 * null-其他可配置参数
		 */
		c.queueDeclare("helloworld",false,false,false,null);
		c.basicPublish("", "helloworld", null, 
				("hello world"+System.currentTimeMillis()).getBytes());
		System.out.println("消息已经发送");
		c.close();
	}
}
