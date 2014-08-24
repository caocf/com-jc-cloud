package com.jc.dao.app.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.jc.dao.app.IEmployee;
import com.jc.dao.app.IOrders;
import com.jc.dao.app.ITb2;
import com.jc.dao.base.DefaultDaoImpl;
import com.jc.domain.EmployeeEntity;
import com.jc.domain.OrdersEntity;
import com.jc.domain.Tb2Entity;

@Repository("ordersDao")
public class OrdersDaoImpl extends DefaultDaoImpl<OrdersEntity> implements IOrders{

}
