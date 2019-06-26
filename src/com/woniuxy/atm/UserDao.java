package com.woniuxy.atm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.woniuxy.util.JDBCUtils;
/**
 * 每一个方法之后都写了close(); 改用线程池...
 * @author RingoChen
 *
 */
public class UserDao {
	public static QueryRunner qr;
	
	public void add(String username, String password) throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "insert into atm(username,password) values(?,?)";
		Object[] params = {username,password};
		qr.update(connection, sql, params);
	}
	
	public void del(String username) throws SQLException {
		qr = new QueryRunner();
		String sql = "delete from atm where username = ?";
		Connection connection = JDBCUtils.getConnection();
		qr.update(sql, username);
	}
	
	public void update(String username, int money) throws SQLException {
		qr = new QueryRunner();
		String sql = "update atm set money = ? where username = ?";
		Connection connection = JDBCUtils.getConnection();
		Object[] params = {money,username};
		int row = qr.update(connection, sql, params);//更新操作 返回的是受影响的行
//		System.out.println("受影响的行数为:" + row);
		connection.close();
	}
	

	
	public List<User> findUserName() throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "select username from atm";
		
		List<User> list = qr.query(connection, sql, new BeanListHandler<>(User.class));
		return list;
		
		
//		User user = qr.query(connection, sql, new BeanHandler<User>(User.class));
//		System.out.println(user.getUsername());//返回第一条
	}
	
	public String findPasswordByUsername(String username) throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "select password from atm where username = ?";
		String[] arr = {username};
		String query = qr.query(connection, sql, new ScalarHandler<>(), arr);//用于单数据操作
//		System.out.println(query);
		return query;
	}
	
	public int findMoneyByUserName(String username) throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "select money from atm where username = ?";
		String[] arr = {username};
		int query = qr.query(connection, sql, new ScalarHandler<>(), arr);//用于单数据操作
		return query;
	}
	
	public void query(int id, int money) throws SQLException {//这是例子
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "select * from atm where id = ?";
		
//		return qr.query(connection, sql, new ArrayHandler());// new ArrayHandler()返回第一条数据 并以Object[]形式返回
		
//		List<Object[]> list= qr.query(connection, sql, new ArrayListHandler());返回存放Object[]的List
		
		//Java Bean对象
//		User user = qr.query(connection, sql, new BeanHandler<>(User.class));
		Object[] params = {id,money};
		User user = qr.query(connection, sql, new BeanHandler<User>(User.class),params);
		System.out.println(user.getUsername());
		
		List<User> list = qr.query(connection, sql, new BeanListHandler<>(User.class),params);
		//重写User类toString()输出或foreach遍历List输出
		
//		Object query = qr.query(connection, sql, new ScalarHandler<>());//用于单数据操作
//		System.out.println(query);
		
		List<Object> query = qr.query(connection, sql, new ColumnListHandler<>("username"));
		System.out.println(query);
		
		connection.close();
	}
}
