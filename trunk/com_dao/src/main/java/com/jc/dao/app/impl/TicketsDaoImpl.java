package com.jc.dao.app.impl;

import org.springframework.stereotype.Repository;


import com.jc.dao.app.ITickets;
import com.jc.dao.base.DefaultDaoImpl;
import com.jc.domain.base.TicketsEntity;

@Repository("ticketsDao")
public class TicketsDaoImpl extends DefaultDaoImpl<TicketsEntity> implements ITickets {

	@Override
	public Integer saveReturnID(TicketsEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
