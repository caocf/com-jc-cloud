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
package com.jc.domain;

import java.io.Serializable;

/**
 * <p>
 * 系统领域对象的基类
 * </p>
 * <br>
 * 系统领域对象的基类，实现了Serializable接口。Allen. Lee DomainObject还覆写了hashCode()方法和equals()方法。这两个方法都将基于代理主键进行操作。
 * 只有代理主键相等，领域对象才相等。如果创建的对象的entityId均未赋值，则直接比较物理地址。
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see

 */
public class DomainObject implements Serializable {
	   /**
		 * 
		 */
	    private static final long serialVersionUID = 6234793910389347162L;

	    /**
	     * 领域对象的代理主键.
	     */
	    private Integer id;

	    /*-------------------- construtors --------------------*/

	    /**
	     * 默认构造函数。
	     * 
	     */
	    public DomainObject() {

	    }

	    /**
	     * 通过代理主键构造领域对象。
	     * 
	     * @param id
	     */
	    public DomainObject(Integer id) {
	        this();
	        this.id = id;
	    }

	    /*----------------- public methods --------------------*/

	    /**
	     * 获取领域对象代理主键。
	     * 
	     * @return
	     */
	    public Integer getId() {
	        return id;
	    }

	    /**
	     * 设置领域对象代理主键。
	     * 
	     * @param id
	     */
	    public void setId(Integer id) {
	        this.id = id;
	    }

}
