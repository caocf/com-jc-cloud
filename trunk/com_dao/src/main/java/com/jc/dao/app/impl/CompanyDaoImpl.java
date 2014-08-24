package com.jc.dao.app.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.jc.dao.app.ICompany;
import com.jc.dao.app.IEmployee;
import com.jc.dao.app.ITb2;
import com.jc.dao.base.DefaultDaoImpl;
import com.jc.domain.CompanyEntity;
import com.jc.domain.EmployeeEntity;
import com.jc.domain.Tb2Entity;

@Repository("companyDao")
public class CompanyDaoImpl extends DefaultDaoImpl<CompanyEntity> implements ICompany{

}
