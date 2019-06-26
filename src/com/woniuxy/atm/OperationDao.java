package com.woniuxy.atm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import sun.applet.Main;

import com.woniuxy.util.JDBCUtils;

public class OperationDao {
	
	public static QueryRunner qr=null;
	
	
	public void add(int id, String type, int money, String username, String time) throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		id+=1;
		String sql = "insert into useroperation (id,type,money,username,time) values(?,?,?,?,?)";
		time = JDBCUtils.getDate();
		Object[] params = {id, type, money, username, time};
		qr.update(connection, sql, params);
	}
	
	
	public static void main(String[] args) throws SQLException {
		
		OperationDao operationDao = new  OperationDao();
		operationDao.add(1,"登录",10,"houhong",JDBCUtils.getDate());
		
	}
	

	public void add(String username, String password) throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "insert into atm(username,password) values(?,?)";
		Object[] params = { username, password };
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
		Object[] params = { money, username };
		int row = qr.update(connection, sql, params);// 闁哄洤鐡ㄩ弻濠囧箼瀹ュ嫮绋� 閺夆晜鏌ㄥú鏍儍閸曨剚笑闁告瑦顨呮總鏍传瀹ュ洦鐣遍悶娑虫嫹
		// System.out.println("闁告瑦顨呮總鏍传瀹ュ洦鐣遍悶娑樻湰閺嗙喐绋夐敓锟�:" + row);
		connection.close();
	}

	public List<User> findUserName() throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "select username from atm";

		List<User> list = qr.query(connection, sql, new BeanListHandler<>(User.class));
		return list;

		// User user = qr.query(connection, sql, new BeanHandler<User>(User.class));
		// System.out.println(user.getUsername());//閺夆晜鏌ㄥú鏍箔椤戣法顏遍柡澶涙嫹
	}

	public String findPasswordByUsername(String username) throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "select password from atm where username = ?";
		String[] arr = { username };
		String query = qr.query(connection, sql, new ScalarHandler<>(), arr);// 闁烩偓鍔嬬花顒勫础閺囩喐娈堕柟璇″枟閹奸攱鎷呴敓锟�
		// System.out.println(query);
		return query;
	}

	public int findMoneyByUserName(String username) throws SQLException {
		qr = new QueryRunner();
		Connection connection = JDBCUtils.getConnection();
		String sql = "select money from atm where username = ?";
		String[] arr = { username };
		int query = qr.query(connection, sql, new ScalarHandler<>(), arr);// 闁烩偓鍔嬬花顒勫础閺囩喐娈堕柟璇″枟閹奸攱鎷呴敓锟�
		return query;
	}

}
