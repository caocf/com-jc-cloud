package com.jc.domain;

import java.io.Serializable;

public class CompanyEntity extends DomainObject {

	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
