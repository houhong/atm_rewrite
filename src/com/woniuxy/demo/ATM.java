package com.woniuxy.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.woniuxy.util.JDBCUtils;

//创建一个ATM数据库 用户信息 操作记录 操作类型
public class ATM {
	public static void main(String[] args) {
		try {
			Connection connection = JDBCUtils.getConnection();
			
			//3.获取SQL执行平台
			Statement createStatement = connection.createStatement();
			//4.执行sql查询语句 返回一个结果集
			
			createStatement.executeUpdate("update atm set username = '雷军' where username = '任正非'");
			
			ResultSet result = createStatement.executeQuery("select * from atm");
			//5.对结果集进行处理
			while(result.next()) {
				String username = result.getString("username");
				System.out.println(username);
			}
			//6.关闭连接 释放资源
			connection.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接失败");
		} 
	}
}
