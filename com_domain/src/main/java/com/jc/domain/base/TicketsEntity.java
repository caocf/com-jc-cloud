package com.jc.domain.base;

import java.io.Serializable;
import java.math.BigInteger;

import com.jc.domain.DomainObject;


public class TicketsEntity extends DomainObject {
	private Integer id;
	private String stub;

	public Integer getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getStub() {
		return stub;
	}

	public void setStub(String stub) {
		this.stub = stub;
	}
}
