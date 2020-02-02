package rabbitmq.workqueue;

import java.io.IOException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.sun.swing.internal.plaf.basic.resources.basic;

public class Test2 {
	public static void main(String[] args) throws Exception{
		ConnectionFactory f = new ConnectionFactory();
		f.setHost("192.168.96.138");
//		f.setPort(5672);
		f.setUsername("admin");
		f.setPassword("admin");
		Connection con = f.newConnection();
		Channel c = con.createChannel();
		
		//定义队列
		c.queueDeclare("task_queue",false,false,false,null);
		
		DeliverCallback deliverCallback = new DeliverCallback() {
			@Override
			public void handle(String consumerTag, Delivery message)
					throws IOException {
				String msg = new String(message.getBody());
				System.out.println("开始处理消息:"+msg);
				for (int i = 0; i < msg.length(); i++) {
					if(msg.charAt(i) == '.') {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
						}
					}
				}
				//手动确认,发回回执
				//参数:消息标签,是否确认与以前收到过的多条消息
				c.basicAck(message.getEnvelope().getDeliveryTag(), false);
				System.out.println("消息处理完毕");
				System.out.println("-------------------------------");
			}
		};
		
		CancelCallback cancelCallback = new CancelCallback() {
			@Override
			public void handle(String arg0) throws IOException {
			}
		};
		
		c.basicQos(1);//每次只接受一条消息,消息处理完之前(ack),不接受任何消息
		
		//消费数据
		/*
		 * 第二个参数
		 * 	true - 自动确认,服务器发送数据后,会自动删除数据
		 * 	false - 手动确认,消费者处理完数据发送回执后,才删数据
		 */
		c.basicConsume("task_queue", false,
				deliverCallback,cancelCallback);
			
	}
}
