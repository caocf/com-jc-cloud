package com.jc.order.service.bussiness.order;

import java.util.List;

import com.jc.domain.OrderEntity;

/*测试服务
 * *
 */
public interface IFirstDubboService {
	public String dealSomething(String val);
	public List<OrderEntity> getOrderList(int count);
}
