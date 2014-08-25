package org.jackychen.toolkits.session.metadata;

import java.io.Serializable;

public class SessionMetaData
	implements Serializable
{
/*	a)     id属性：Session实例的ID。
	b)     maxIdle属性：Session的最大空闲时间，默认情况下是30分钟。
	c)     lastAccessTm属性：Session的最后一次访问时间，每次调用Request.getSession方法时都会去更新这个值。
		用来计算当前Session是否超时。如果lastAccessTm+maxIdle小于System. currentTimeMillis()，就表示当前Session超时。
	d)     validate属性：表示当前Session是否可用，如果超时，则此属性为false。
	e)     version属性：这个属性是为了冗余Znode的version值，用来实现乐观锁，对Session节点的元数据进行更新操作。*/
	private static final long serialVersionUID = 0xa68a9688c5f2f8b3L;
	private String id;
	private Long createTm;
	private Long maxIdle;
	private Long lastAccessTm;
	private Boolean validate;
	private int version;

	public SessionMetaData()
	{
		validate = Boolean.valueOf(false);
		version = 0;
		createTm = Long.valueOf(System.currentTimeMillis());
		lastAccessTm = createTm;
		validate = Boolean.valueOf(true);
	}

	public Long getCreateTm()
	{
		return createTm;
	}

	public void setCreateTm(Long createTm)
	{
		this.createTm = createTm;
	}

	public Long getMaxIdle()
	{
		return maxIdle;
	}

	public void setMaxIdle(Long maxIdle)
	{
		this.maxIdle = maxIdle;
	}

	public Long getLastAccessTm()
	{
		return lastAccessTm;
	}

	public void setLastAccessTm(Long lastAccessTm)
	{
		this.lastAccessTm = lastAccessTm;
	}

	public Boolean getValidate()
	{
		return validate;
	}

	public void setValidate(Boolean validate)
	{
		this.validate = validate;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}
}