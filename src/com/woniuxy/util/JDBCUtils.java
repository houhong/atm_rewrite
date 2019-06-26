package com.woniuxy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class JDBCUtils {
	private static String driveName;
	private static String url;
	private static String sqlName;
	private static String sqlPassword;
	private static Connection connection;
	//类加载前执行注册驱动
	static {
		try {
			Properties properties = new Properties();
			String path = JDBCUtils.class.getResource(".").getPath();//当前类的绝对路径
			System.out.println(path);
			path = path + "database.properties";
			properties.load(new FileInputStream(path));
			driveName = properties.getProperty("driveName");
			url = properties.getProperty("url");
			sqlName = properties.getProperty("sqlName");
			sqlPassword = properties.getProperty("sqlPassword");
			
			
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.err.println("未找到文件");//"err"和"out"打印颜色不一样
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Class.forName(driveName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("注册驱动失败");
		}
	}
	
	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection(url, sqlName, sqlPassword);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("获取连接失败");
		}
		return connection;
		
	}
	
	public static void close(Connection connection, PreparedStatement ps, ResultSet result) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("连接关闭失败");
			}
		}
		if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("预编译对象关闭失败");
			}
		}
		if(result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("结果集关闭失败");
			}
		}
	}
	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String theDate = dateFormat.format(date);
		return theDate;
	}
}
