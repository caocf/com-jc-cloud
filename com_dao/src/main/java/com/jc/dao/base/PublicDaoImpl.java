/**  
 * Description: <类功能描述-必填>DAO基类针对数据库的读、写操作分别采用不同的Template,可以在一定程度上防止大量并发造成的死锁。 
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.jc.domain.DomainObject;

public class PublicDaoImpl {
    private SqlExecutor sqlExecutor;

    @Autowired
    @Qualifier("SqlExecutorExt")
    public void setSqlExecutor(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }


    /**
     * 分页statement后缀：_count
     */
    protected static final String COUNT_STATEMENT_SUFFIX = "_count";
    /**
     * 
     */
    private String entityClass;

    /**
     * 用于写操作的Template
     */
    private SqlMapClientTemplate writeTemplate;

    /**
     * 用于读操作的Template
     */
    private SqlMapClientTemplate readTemplate;

    public SqlMapClientTemplate getWriteTemplate() {
        return writeTemplate;
    }

    /**
     * @return the readTemplate
     */
    public SqlMapClientTemplate getReadTemplate() {
        return readTemplate;
    }

    /**
     * 需要在配置文件中配置id为writeSqlMapClient的bean
     * 
     * @param sqlMapClient
     *            the SqlMapClient object to set
     */
    // @Resource(name="readSqlMapClient")
    @Autowired
    @Qualifier("sqlMapClient")
    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        readTemplate = new SqlMapClientTemplate(sqlMapClient);
        writeTemplate = readTemplate;
    }

    /**
     * 新增实体类根据泛型新增 <br>
     * 
     * @param statement
     * @param entity
     * @return
     * @see
     */
    protected DomainObject save(String statement, DomainObject entity) {
        Integer entityId = (Integer) writeTemplate.insert(statement, entity);
        return entity;
    }

    /**
     * 跟据对象新增 <br>
     * 
     * @param statement
     * @param entity
     * @return
     * @see
     */
    protected Integer saveByObj(String statement, Object entity) {
        Integer entityId = (Integer) writeTemplate.insert(statement, entity);
        return entityId;
    }

    /**
     * 新增返回本次会话自增Id。 <br>
     * 
     * @param statement
     * @param entity
     * @return
     * @see
     */
    protected int savegetId(String statement, Object entity) {
        return (Integer) (writeTemplate.insert(statement, entity));
    }

    /**
     * 
     * 新增返回本次会话自增id<br>
     * 
     * @param statement
     * @param entity
     * @see
     */
    protected void saveid(String statement, Object entity) {
        writeTemplate.insert(statement, entity);
    }

    /**
     * 修改<br>
     * 
     * @param statement
     * @param params
     * @see
     */
    protected void update(String statement, Object params) {
        writeTemplate.update(statement, params);
    }

    /**
     * 修改 <br>
     * 
     * @param statement
     * @param params
     * @see
     */
    protected void delete(String statement, Object params) {
        writeTemplate.delete(statement, params);
    }

    /**
     * 批量删除 <br>
     * 
     * @param statement
     * @param params
     * @see
     */
    protected void deleteByIds(String statement, Object params) {
        writeTemplate.delete(statement, params);
    }

    /**
     * 根据参数取得<code>T</code>类型实体<br>
     * 
     * @param statement
     * @param params
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    protected Object getEntity(String statement, Object params) {
        return (Object) readTemplate.queryForObject(statement, params);
    }

    /**
     * 根据参数取得任意类型实体 <br>
     * 
     * @param statement
     * @param param
     * @return
     * @see
     */
    protected Object getObject(String statement, Object param) {
        return readTemplate.queryForObject(statement, param);
    }


   
   

    /**
     * 查询列表，不提供分页功能 <br>
     * 
     * @param statement
     * @param params
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    protected List<Object> query(String statement, Object params) {
        return (List<Object>) readTemplate.queryForList(statement, params);
    }

    /**
     * 无参数查询列表，不提供分页功能 <br>
     * 
     * @param statement
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    protected List<Object> query(String statement) {
        return (List<Object>) readTemplate.queryForList(statement);
    }

    /**
     * 查询任意类型的对象列表。不局限于T类型的<br>
     * 
     * @param statement
     * @param params
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    protected List<Object> queryEntities(String statement, Object params) {
        return readTemplate.queryForList(statement, params);
    }

    /**
     * 批量新增/修改/删除。注意批量新增时是无法正确的获取自增主键的值（批处理中最后一个新增可以获取正确的值，其它皆不可以），
     * 所以如果需要获取自增主键的值，不应该使用该方法。
     * 
     * @param statement
     * @param params
     * @return 返回操作影响的行数
     */
    /*
     * protected int batch(final String statement, final Object[] params) {
     * return (Integer)writeTemplate.execute(new SqlMapClientCallback() { public
     * Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
     * executor.startBatch(); for(Object param : params) { if(param == null) {
     * continue; } executor.update(statement, param); } return
     * executor.executeBatch(); } }); }
     */

    /**
     * 根据参数判断该记录是否已存在（逻辑上存在）<br>
     * 
     * @param statement
     * @param params
     * @return
     * @see
     */
    protected boolean isExistEntity(String statement, Object params) {
        return (Integer) readTemplate.queryForObject(statement, params) > 0;
    }

    /**
     * 
     * 取得指定的statement的完全限定名称。形式为<code>namespace</code> + "." +
     * <code>statement</code> <br>
     * 
     * @param namespace
     * @param statement
     * @return
     * @see
     */
    protected String getQualifiedName(String namespace, String statement) {
        return new StringBuffer().append(namespace).append(".")
                .append(statement).toString();
    }

    

    /**
     * 根据条件查询整数结果。<br>
     * 
     * @param statement
     * @param params
     * @return
     * @see
     */
    protected int uniqueIntResult(String statement, Object params) {
        if (params == null) {
            return (Integer) readTemplate.queryForObject(statement);
        }
        return (Integer) readTemplate.queryForObject(statement, params);
    }

    /**
     * 查询符合条件的记录数，不需要分页。 <br>
     * 增加params非空判断，如果为空表示查询所有的数量
     * 
     * @param statement
     * @param params
     * @return
     * @see
     */
    protected int queryCount(String statement, Object entity) {
        if (entity == null) {
            return (Integer) readTemplate.queryForObject(statement);
        }
        return (Integer) readTemplate.queryForObject(statement, entity);
    }
}
