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

import java.util.List;
import java.util.Map;

/**
 * 公共DAO接口<br>
 * 公共接口，包括添加、修改、删除、通过id查询、分页查询、查询总数
 * 
 * @param <T>
 *            T
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see
 */
public interface Dao<T> {

	/**
	 * 保存<br>注意：这个不返回自增id，因为是水平分割，有单独的id生成器
	 * 
	 * @param entity
	 *            实体
	 * @return
	 * @see
	 */
	Integer save(T entity);
	/**
	 * 修改 <br>
	 * 
	 * @param entity
	 * @see
	 */
	void update(T entity);

	/**
	 * 删除<br>
	 * 
	 * @param id
	 * @see
	 */
	void delete(Integer id);

	/**
	 * 批量删除<br>
	 * 
	 * @param ids
	 * @see
	 */
	void deleteByIds(String ids);

	/**
	 * 
	 * 通过id查询 <br>
	 * 
	 * @param id
	 * @return
	 * @see
	 */
	T getById(Integer id);


	/**
	 * 
	 * 根据Map查询 <br>
	 * 
	 * @param queryParams
	 * @return
	 * @see
	 */
	List<T> queryByMap(Map map);

	/**
	 * 
	 * 获取所有数据 <br>
	 * 
	 * @return
	 * @see
	 */
	List<T> queryAll();
	/**
	 * 
	 * 获取总数 <br>
	 * 
	 * @param params
	 * @return
	 * @see
	 */
	int queryCount(Object params);


}
