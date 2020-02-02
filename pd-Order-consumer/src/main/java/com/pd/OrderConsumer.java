package com.pd;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pd.pojo.PdOrder;
import com.pd.service.OrderService;

@Component
public class OrderConsumer {
	@Autowired
	OrderService orderService;
	
	@RabbitListener(queues = "orderQueue")
	public void save(PdOrder pdOrder) throws Exception{
		orderService.saveOrder(pdOrder);
	}
}
