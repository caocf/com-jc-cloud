package com.jc.order.service.bussiness.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.provider.ApplicationStart;

import com.jc.dao.app.IEmployee;
import com.jc.dao.app.IOrders;
import com.jc.dao.app.ITest;
import com.jc.domain.EmployeeEntity;
import com.jc.domain.OrderEntity;
import com.jc.order.service.bussiness.order.IFirstDubboService;


@Service("firstDubboService")
@SuppressWarnings("unused")
public class FirstDubboServiceImpl implements IFirstDubboService {
	@Autowired
	private IOrders ordersDao;
	@Autowired
	private IEmployee employeeDao;
	@Autowired
	private ITest test;
	public String dealSomething(String val) {
		// employeeDao=(IEmployee) ApplicationStart.instance().ctx.getBean("employeeDao");
		//ordersDao.delete(1);
		
		EmployeeEntity entity=employeeDao.getById(101);
		return val+":"+entity.getName()+test.r();
	}

	@Override
	public List<OrderEntity> getOrderList(int count) {
		List<OrderEntity> listOrderEntity = new ArrayList<OrderEntity>();
		for (int i = 0; i < count; i++) {
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setOrderid("ORDERID" + String.valueOf(count));
			orderEntity.setOrdernumber("ORDERNUMBER" + String.valueOf(count));
			orderEntity.setOrderPrice(count * 10);
			orderEntity.setMark("Test:" + String.valueOf(count));
			listOrderEntity.add(orderEntity);

		}
		return listOrderEntity;
	}

}
