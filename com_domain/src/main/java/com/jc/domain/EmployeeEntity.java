package com.jc.domain;

import java.io.Serializable;

public class EmployeeEntity extends DomainObject {

	private String name;
	private int sharding_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSharding_id() {
		return sharding_id;
	}

	public void setSharding_id(int sharding_id) {
		this.sharding_id = sharding_id;
	}

}
