package com.x.framework.dao;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.CannotCreateTransactionException;

public class ConnectionPool {
	private static Map<String, Connection> map = new ConcurrentHashMap<String, Connection>();
	private static DataSource dataSource;

	public ConnectionPool() {
		
	}

	/**
	 * 返回唯一id
	 *
	 * @param length
	 *            int
	 * @return String
	 */
	private static String getUid(int length) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		StringBuffer stringBuffer = new StringBuffer(df.format(date));
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(random.nextInt(10));
		}
		return stringBuffer.toString();
	}

	/**
	 * 从缓冲池删除一个连接
	 *
	 * @param key
	 *            String
	 */
	private static void removeConnection(String key) throws SQLException {
		Connection connection = map.get(key);
		if (connection != null) {
			connection.close();
			map.remove(key);
		}
	}

	/**
	 * 获取一个连接
	 *
	 * @param key
	 *            String
	 * @return Connection
	 */
	public static Connection getConnection(String key) throws SQLException {
		Connection connection = map.get(key);
		if (connection == null) {
			throw new SQLException("=====connection is null=====");
		}
		return connection;
	}

	/**
	 * 手动开始一个事务
	 *
	 * @return String
	 */
	public static String beginTransaction() {
		try {
			String key = getUid(8);
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			new DefaultTransactionStatus(connection, true, true, false, true, null);
			map.put(key, connection);
			return key;
		} catch (SQLException e) {
			throw new CannotCreateTransactionException("=====begin transaction failed with SQLException=====", e);
		}
	}

	/**
	 * 手动提交一个事务
	 *
	 * @param key
	 *            String
	 */
	public static void commitTransaction(String key) {
		Connection connection = map.get(key);
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new TransactionSystemException("=====commit transaction failed with SQLException=====", e);
			} finally {
				try {
					removeConnection(key);
				} catch (SQLException e) {
					throw new TransactionSystemException("=====close connection failed with SQLException=====", e);
				}
			}
		}
	}

	/**
	 * 手动回滚一个事务
	 *
	 * @param key
	 *            String
	 */
	public static void rollbackTransaction(String key) {
		Connection connection = map.get(key);
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new UnexpectedRollbackException("=====rollback transaction failed with SQLException=====", e);
			} finally {
				try {
					removeConnection(key);
				} catch (SQLException e) {
					throw new TransactionSystemException("=====close connection failed with SQLException=====", e);
				}
			}
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		ConnectionPool.dataSource = dataSource;
	}
}
