package com.woniuxy.dbcp;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class Demo {
	private static BasicDataSource bds;
	//用properties配置文件
	static {
		//提供基本数据资源配置对象
		bds = new BasicDataSource();
		bds.setDriverClassName("");
		bds.setUrl("");
		bds.setUsername("");
		bds.setPassword("");
		//以下四个在初期可以不配置而是用默认值如setMaxIdle=8,setMinIdle=0
		bds.setMaxActive(100);//连接池最大数量
		bds.setMaxIdle(30);//连接池最大空闲数量
		bds.setMinIdle(10);//连接池最小空闲数量
		bds.setInitialSize(20);//连接池初始化数量
		
	}
	//获取连接池对象
	public static Connection getSource() {
		Connection connection = null;//局部变量要赋初值
		try {
			connection = bds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
