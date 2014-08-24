package com.jc.base.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("jdbcUtils")
public class JDBCUtils {
	private static Logger log = Logger.getLogger(JDBCUtils.class);
	private Connection con;
	@Value("#{jdbcConfig['jdbcread.url']}")
	String url = "";
	@Value("#{jdbcConfig['jdbcread.username']}")
	String username = "";
	@Value("#{jdbcConfig['jdbcread.password']}")
	String password = "";
	@Value("#{jdbcConfig['jdbc.driverClassName']}")
	String driver = "";

	public Connection getConnection() throws ClassNotFoundException {
		try {
			Class.forName(driver);// 加载数据库驱动程序
			con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			//throw new RuntimeException(e);
		}
	}

	public void closeConnection() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public DatabaseMetaData getDatabaseMetaData() throws ClassNotFoundException, SQLException
	{
		Connection conn = getConnection();
		java.sql.DatabaseMetaData dbmd = conn.getMetaData();
		return dbmd;
	}
	/**
	 * 取一个数据库中所有表的信息
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<String> getAllUserTablesInfo() throws Exception
	{
		List<String> lsTables=new ArrayList<String>();
		java.sql.DatabaseMetaData dbmd = getDatabaseMetaData();
		ResultSet tSet = (ResultSet) dbmd.getTables(null, "%", "%",
				new String[] { "TABLE", "VIEW" });
		while (tSet.next()) {
			/*log.info(tSet.getRow() + "_表类别:" + tSet.getString("TABLE_CAT")
					+ "_表模式:" + tSet.getString("TABLE_SCHEM") + "_表名称:"
					+ tSet.getString("TABLE_NAME") + "_表类型:"
					+ tSet.getString("TABLE_TYPE")*/
			lsTables.add(tSet.getString("TABLE_NAME"));
		}
		closeConnection();
		return lsTables;
	}

	public Map<String,String> getTablesPrimaryKeys(String tableName) throws Exception
	{
		Map<String,String> mapTables=new HashMap<String,String>();
		ResultSet primaryKey = getDatabaseMetaData().getPrimaryKeys(null, null,
				tableName);
		while (primaryKey.next()) {
		/*	log.info("表名:" + primaryKey.getString("TABLE_NAME") + ",列名:"
					+ primaryKey.getString("COLUMN_NAME") + " 主键名:"
					+ primaryKey.getString("PK_NAME"));*/
			mapTables.put(primaryKey.getString("TABLE_NAME"), primaryKey.getString("COLUMN_NAME"));
			// 表名:SYS_ROLE_RES,列名:SYS_RES_ID 主键名:CONSTRAINT_9
			// 表名:SYS_ROLE_RES,列名:SYS_ROLE_ID 主键名:CONSTRAINT_9
		}
		closeConnection();
		return mapTables;
	}
	/**
	 * 取一个数据库中所有表的综合信息
	 * @throws Exception 
	 */
	public void infoDB() throws Exception {
		Connection conn = getConnection();
		log.info("######  DatabaseMetaData关于数据库的整体综合信息====");
		java.sql.DatabaseMetaData dbmd = conn.getMetaData();

		log.info("数据库产品名: " + dbmd.getDatabaseProductName());
		log.info("数据库是否支持事务: " + dbmd.supportsTransactions());
		log.info("数据库产品的版本号:" + dbmd.getDatabaseProductVersion());
		log.info("数据库的默认事务隔离级别:" + dbmd.getDefaultTransactionIsolation());
		log.info("支持批量更新:" + dbmd.supportsBatchUpdates());
		log.info("DBMS 的 URL:" + dbmd.getURL());
		log.info("数据库的已知的用户名称:" + dbmd.getUserName());
		log.info("数据库是否处于只读模式:" + dbmd.isReadOnly());
		log.info("数据库是否支持为列提供别名:" + dbmd.supportsColumnAliasing());
		log.info("是否支持指定 LIKE 转义子句:" + dbmd.supportsLikeEscapeClause());
		log.info("是否为外连接提供受限制的支持:" + dbmd.supportsLimitedOuterJoins());
		log.info("是否允许一次打开多个事务:" + dbmd.supportsMultipleTransactions());
		log.info("是否支持 EXISTS 表达式中的子查询:" + dbmd.supportsSubqueriesInExists());
		log.info("是否支持 IN 表达式中的子查询:" + dbmd.supportsSubqueriesInIns());
		log.info("是否支持给定事务隔离级别:" + dbmd.supportsTransactionIsolationLevel(1));
		log.info("此数据库是否支持事务:" + dbmd.supportsTransactions());
		log.info("此数据库是否支持 SQL UNION:" + dbmd.supportsUnion());
		log.info("此数据库是否支持 SQL UNION ALL:" + dbmd.supportsUnionAll());
		log.info("此数据库是否为每个表使用一个文件:" + dbmd.usesLocalFilePerTable());
		log.info("此数据库是否将表存储在本地文件中:" + dbmd.usesLocalFiles());
		log.info("底层数据库的主版本号:" + dbmd.getDatabaseMajorVersion());
		log.info("底层数据库的次版本号:" + dbmd.getDatabaseMinorVersion());

		log.info("JDBC 驱动程序的主版本号:" + dbmd.getJDBCMajorVersion());
		log.info("JDBC 驱动程序的次版本号:" + dbmd.getJDBCMinorVersion());
		log.info("JDBC 驱动程序的名称:" + dbmd.getDriverName());
		log.info("JDBC 驱动程序的 String 形式的版本号:" + dbmd.getDriverVersion());

		log.info("可以在不带引号的标识符名称中使用的所有“额外”字符:" + dbmd.getExtraNameCharacters());
		log.info("用于引用 SQL 标识符的字符串:" + dbmd.getIdentifierQuoteString());
		log.info("允许用于类别名称的最大字符数:" + dbmd.getMaxCatalogNameLength());
		log.info("允许用于列名称的最大字符数:" + dbmd.getMaxColumnNameLength());
		log.info("允许在 GROUP BY 子句中使用的最大列数:" + dbmd.getMaxColumnsInGroupBy());
		log.info("允许在 SELECT 列表中使用的最大列数:" + dbmd.getMaxColumnsInSelect());
		log.info("允许在表中使用的最大列数:" + dbmd.getMaxColumnsInTable());
		log.info("数据库的并发连接的可能最大数:" + dbmd.getMaxConnections());
		log.info("允许用于游标名称的最大字符数:" + dbmd.getMaxCursorNameLength());
		log.info("在同一时间内可处于开放状态的最大活动语句数:" + dbmd.getMaxStatements());

		// 获取所有表 new String[]{"TABLE"}
		// String[] type = {"TABLE","VIEW"} null
		log.info("###### 获取表的信息");
		List<String> LsTables=getAllUserTablesInfo();
		for(String table:LsTables)
		{
			System.out.println(table);
		}

		log.info("###### 获取当前数据库所支持的SQL数据类型");
		ResultSet tableType = dbmd.getTypeInfo();
		while (tableType.next()) {
			log.info("数据类型名:" + tableType.getString(1) + ",短整型的数:"
					+ tableType.getString(2) + ",整型的数:"
					+ tableType.getString(3) + ",最小精度:"
					+ tableType.getString(14) + ",最大精度:"
					+ tableType.getString(15) + "表注释:");
			// 数据类型名:TIMESTAMP,短整型的数:93,整型的数:23,最小精度:0,最大精度:10
			// 数据类型名:VARCHAR,短整型的数:12,整型的数:2147483647,最小精度:0,最大精度:0
		}

		log.info("###### 表的主键列信息");
		Map<String,String> map=getTablesPrimaryKeys("t_employee");
		for (Map.Entry<String,String> entry:map.entrySet()) {
		     System.out.println("列名称:"+entry.getKey()+"___主键:"+entry.getValue());
		}


		/*log.info("###### 表的外键列信息");
		ResultSet foreinKey = dbmd.getImportedKeys("MANOR", "PUBLIC",
				"SYS_ROLE_RES");
		while (foreinKey.next()) {
			log.info("主键名:" + foreinKey.getString("PK_NAME") + ",外键名:"
					+ foreinKey.getString("FKCOLUMN_NAME") + ",主键表名:"
					+ foreinKey.getString("PKTABLE_NAME") + ",外键表名:"
					+ foreinKey.getString("FKTABLE_NAME") + ",外键列名:"
					+ foreinKey.getString("PKCOLUMN_NAME") + ",外键序号:"
					+ foreinKey.getString("KEY_SEQ"));
			// 主键名:PRIMARY_KEY_95,外键名:SYS_RES_ID,主键表名:SYS_RESOURCE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
			// 主键名:PRIMARY_KEY_A,外键名:SYS_ROLE_ID,主键表名:SYS_ROLE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
		}*/

	/*	log.info("###### 获取数据库中允许存在的表类型");
		ResultSet tableTypes = dbmd.getTableTypes();
		while (tableTypes.next()) {
			log.info("类型名:" + tableTypes.getString(1));
			*//**
			 * H2 类型名:SYSTEM TABLE 类型名:TABLE 类型名:TABLE LINK 类型名:VIEW
			 *//*
		}*/

		// 此外还可以获取索引等的信息
		conn.close();
	}

	/**
	 * PreparedStatement 信息 ResultSetMetaData 信息
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void getDBParameterMetaData() throws SQLException,
			ClassNotFoundException {
		Connection conn = getConnection(); // id,name
		PreparedStatement pre = conn
				.prepareStatement("SELECT * FROM SYS_APPTYPE where id = ?");
		pre.setInt(1, 3);
		java.sql.ParameterMetaData pmd = pre.getParameterMetaData();
		log.info("参数的个数:" + pmd.getParameterCount());
		log.info("获取指定参数的 SQL 类型:" + pmd.getParameterType(1));
		log.info("culomn的参数类型:" + pmd.getParameterTypeName(1));
		log.info("Java 类的完全限定名称:" + pmd.getParameterClassName(1));
		log.info("获取指定参数的模式:" + pmd.getParameterMode(1));
		log.info("获取指定参数的指定列大小:" + pmd.getPrecision(1));
		log.info("获取指定参数的小数点右边的位数:" + pmd.getScale(1));
		log.info("是否允许在指定参数中使用 null 值:" + pmd.isNullable(1));
		log.info("指定参数的值是否可以是带符号的数字:" + pmd.isSigned(1));

		// 获取结果集元数据
		ResultSet rs = pre.executeQuery();
		while (rs.next()) {
			log.info(rs.getString(1) + "___" + rs.getString(2));
		}
		rs.close();
	}

	/**
	 * 获取所有Driver信息
	 */
	public void getAllDriverMsg() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver d = drivers.nextElement();
			log.info(d.getClass().getName() + "_" + d.getMajorVersion());
		}

	}

	public Map<String, String> getRemarks(String tablename)
			throws ClassNotFoundException {
		return getRemarks(tablename, null);
	}

	/* 获得表字段的注释 */
	public Map<String, String> getRemarks(String tablename, Connection conn)
			throws ClassNotFoundException {
		// Properties props = new Properties();
		DatabaseMetaData dbmd;
		Map<String, String> map = new HashMap<String, String>();
		try {
			// props.put("remarksReporting", "true");
			if (conn == null) {
				dbmd = getConnection().getMetaData();
			} else {
				dbmd = conn.getMetaData();
			}
			ResultSet resultSet = dbmd.getColumns(null, null,
					tablename.toUpperCase(), "%");
			StringBuffer result = new StringBuffer();
			while (resultSet.next()) {
				map.put(resultSet.getString("COLUMN_NAME"),
						resultSet.getString("REMARKS"));
				/*
				 * result.append(resultSet.getString("COLUMN_NAME")).append("\t")
				 * .append(resultSet.getString("TYPE_NAME")).append("\t")
				 * .append(resultSet.getString("IS_NULLABLE"))
				 * .append("\t").append(resultSet.getString("REMARKS"))
				 * .append("\t").append("\n");
				 */
			}
			// System.out.println(result.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (null != con) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return map;
	}
}
