package com.woniuxy.demo;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.woniuxy.util.JDBCUtils;

public class DbUtilsDemo {
	
	
	
	public static void main(String[] args) throws SQLException {
		QueryRunner qr = new QueryRunner();
		
		String sql = "update atm set username = ? where id = ?";
		Connection connection = JDBCUtils.getConnection();
		Object[] params = {"任正非",4};
		int row = qr.update(connection, sql, params);//返回的是受影响的行
		System.out.println("受影响的行数为" + row);
		connection.close();
		
	}
	
	
	
	@Test
	public void test() {
		System.out.println("test");
	}

}
