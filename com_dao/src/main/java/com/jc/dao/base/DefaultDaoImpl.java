/**  
 * Description: <类功能描述-必填> 
 * Copyright:   Copyright (c)2012  
 * Company:     ChunYu 
 * @author:     ChenZhao  
 * @version:    1.0  
 * Create at:   2012-12-21 下午4:22:51  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-12-21   ChenZhao      1.0       如果修改了;必填  
 */
package com.jc.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import com.jc.domain.DomainObject;

/**
 * 
 * 默认dao实现<br>
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see
 */
@Repository("defaultDao")
public class DefaultDaoImpl<T extends DomainObject> extends DaoImpl<T>
		implements Dao<T> {

	/**
	 * 获取带命名空间的函数名 <br>
	 * 
	 * @param class_
	 * @return 返回类型为 ClassName.methodName
	 * @see
	 */
	public String getStatement(Class<?> class_) {
		StackTraceElement[] stacks = new Exception().getStackTrace();
		return getStatement(class_, stacks[1].getMethodName());
	}

	/**
	 * 获取带命名空间的函数名<br>
	 * 
	 * @param class_
	 * @param methodName
	 * @return 返回类型为 ClassName.methodName
	 * @see
	 */
	public String getStatement(Class<?> class_, String methodName) {
		return class_.getName() + "." + methodName;
	}

	/**
	 * 根据T类型来算出带命名空间的函数名<br>
	 * 
	 * 
	 * @return 返回类型为 ClassName.methodName
	 * @see
	 */
	public String getStatement() {
		StackTraceElement[] stacks = new Exception().getStackTrace();
		return getStatement(stacks[1].getMethodName());
	}

	/**
	 * 根据T类型来算出带命名空间的函数名<br>
	 * 
	 * 
	 * @param methodName
	 *            方法名
	 * @return 返回类型为 ClassName.methodName
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public String getStatement(String methodName) {
		ParameterizedType type = ((ParameterizedType) getClass()
				.getGenericSuperclass());
		Class<T> entityClass = (Class<T>) (type.getActualTypeArguments()[0]);
		return getStatement(entityClass, methodName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#delete(java.lang.Integer)
	 */
	public void delete(Integer id) {
		delete(getStatement(), id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#deleteByIds(java.lang.String)
	 */
	public void deleteByIds(String ids) {
		deleteByIds(getStatement(), ids);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#getById(java.lang.Integer)
	 */
	public T getById(Integer id) {
		return this.getEntity(getStatement(), id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#save(java.lang.Object)
	 */
	public Integer save(T entity) {
		this.save(getStatement(), entity);
		return entity.getId();

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#update(java.lang.Object)
	 */
	public void update(T entity) {
		this.update(getStatement(), entity);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#query()
	 */
	@Override
	public List<T> queryByMap(Map map) {
		return this.query(getStatement(), map);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#queryAll()
	 */
	@Override
	public List<T> queryAll() {
		return this.query(getStatement());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see core.dao.Dao#queryAll()
	 */
	@Override
	public int queryCount(Object params) {
		return this.queryCount(getStatement(), params);
	}

}
