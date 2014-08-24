/**  
 * Description: IBatis操作工具类
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

package com.jc.base.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("iBatisUtils")
public class IBatisUtils {
	@Autowired
	private JDBCUtils jDBCUtils;
	@Autowired
	private LogUtils logUtils;
	/**
     * 执行插入数据库的语句
     * @param sql
     * @param params
     * @return 返回生成的主键
	 * @throws SQLException 
     */
    public int executeInsert(String sql, Object[] params,Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //1 获得连接
           // conn = jDBCUtils.getConnection();
            //2 设置提交方式为程序控制
           // conn.setAutoCommit(false);
            //3 获得语句对象
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //4 设置SQL语句的参数
            if (null != params && 0 < params.length) {
                setParams(pstmt, params);
            }
            //5 打印SQL语句
           // if (MyDBConstants.showSQL) {
                getPreparedSQL(sql, params);
           // }
            //6 执行语句
          //  pstmt.executeUpdate();
            //7 程序提交
            //conn.commit();
            //8 返回生成的主键
           // rs = pstmt.getGeneratedKeys();
            int generatedKey = 0;
           /* if (rs.next()) {
                generatedKey = rs.getInt(1);
            }*/
            if (0 < generatedKey)
                throw new SQLException("插入记录时出错");
            return generatedKey;
        } catch (SQLException e) {
            //回滚
        	//conn.rollback();
           // MyDBUtil.rollBack(conn);
            throw new SQLException(e);
        } finally {
            //关闭打开的操作
        	conn.close();
          //  MyDBUtil.close(conn, pstmt, rs);
        }
    }
    /**
     * 获得PreparedStatement向数据库提交的SQL语句
     * @param sql
     * @param params
     * @return
     */
    private Object[] getPreparedSQL(String sql, Object[] params) {
        //1 如果没有参数，说明是不是动态SQL语句
                int paramNum = 0;
                if (null != params)  paramNum = params.length;
        if (1 > paramNum) return null;
        //2 如果有参数，则是动态SQL语句
        StringBuffer returnSQL = new StringBuffer();
        String[] subSQL = sql.split("\\?");
        for (int i = 0; i < paramNum; i++) {
            if (params[i] instanceof Date) {
            	
                returnSQL.append(subSQL[i]).append(" '").append(DatetimeUtils.DateUtil2SQL((java.util.Date)params[i])).append("' ");
            } else {
/*            	if(subSQL[i].toUpperCase().indexOf("CREATEUSERNAME")>-1)
            	{
            		params[i]="JACKYCHEN";
            	}
            	if(subSQL[i].toUpperCase().indexOf("UPDATEDATETIME")>-1)
            	{
            		params[i]=DatetimeUtils.strToDate(DatetimeUtils.getCurDate("yyyy-MM-dd HH:mm:ss"));
            	}*/
                returnSQL.append(subSQL[i]).append(" '").append(params[i]).append("' ");
            }
        }

        if (subSQL.length > params.length) {
            returnSQL.append(subSQL[subSQL.length - 1]);
        }
        return params;
        //return returnSQL.toString();
    }

    /**
     * 为PreparedStatement预编译的SQL语句设置参数
     * @param pstmt
     * @param params
     * @throws SQLException 
     */
    private void setParams(PreparedStatement pstmt, Object[] params) throws SQLException {
            if (null != params) {
                for (int i = 0, paramNum = params.length; i < paramNum; i++) {
                    try {
                        if (null != params[i] &&
                            params[i] instanceof java.util.Date) {
                            pstmt.setDate(i + 1, DatetimeUtils.DateUtil2SQL((java.util.Date) params[i]));
                        } else {
                            pstmt.setObject(i + 1, params[i]);
                        }
                    } catch (SQLException e) {
                        throw new SQLException(e);
                    }
                }
                logUtils.info(pstmt.toString());
            }
    }
    /**
     * 执行更新或者删除数据库的语句
     * @param sql
     * @param params
     * @return 返回执行成功与否
     * @throws SQLException 
     */
    public Object[] executeUpdateDel(String sql, Object[] params,Connection conn) throws SQLException {
        boolean isSuccess = false;
        PreparedStatement pstmt = null;
        Object[] resultParams=null;
        try {
            //1 获得连接
            //2 设置提交方式为程序控制
           // conn.setAutoCommit(false);
            //3 获得语句对象
            pstmt = conn.prepareStatement(sql);
            //4 设置SQL语句的参数
            if (null != params && 0 < params.length) {
                setParams(pstmt, params);
            }
            //5 打印SQL语句
            //if (MyDBConstants.showSQL) {
               resultParams= getPreparedSQL(sql, params);
           // }
            //6 执行语句
           // pstmt.executeUpdate();
            //7 程序提交
            //conn.commit();
            //8 设置语句执行的标记
           // isSuccess = true;
        } catch (SQLException e) {
            //回滚
       conn.rollback();
        } finally {
            //关闭打开的操作
          // conn.close();
        }
        return resultParams;
    }
    /**
     * 执行查询数据库的语句;
     *
     * @return
     * @throws SQLException 
     */
    public void executeQuery(String sql, Object[] params,Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //1 获得连接
            //conn = MyDBConnection.getInstance().getConnection();
            //2 设置提交方式为程序控制
           // conn.setAutoCommit(false);
            //3 获得语句对象
            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //4 设置SQL语句的参数
            if (null != params && 0 < params.length) {
                setParams(pstmt, params);
            }
            //5 打印SQL语句
            //if (MyDBConstants.showSQL) {
                getPreparedSQL(sql, params);
           // }
            //6 执行语句
            //rs = pstmt.executeQuery();

            //9 程序提交
          //  conn.commit();

            //10 获得记录
          /*  Object vo = new Object();
            if (null != rs && rs.next()) {
                vo = rs2vo(rs);
            }*/
//            return results;
            //return vo;
        } catch (SQLException e) {
            //回滚
        	conn.rollback();
        } finally {
            //关闭打开的操作
           conn.close();
        }
    }
}
