package com.jc.dao.app;


import com.jc.dao.base.Dao;
import com.jc.domain.base.TicketsEntity;

public interface ITickets extends Dao<TicketsEntity> {
	Integer saveReturnID(TicketsEntity entity);
}
