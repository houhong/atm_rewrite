package com.woniuxy.atm;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserService {
	public static void main(String[] args) throws SQLException {
		System.out.println("欢迎使用ATM...");
		while(true) {
			mainMenu();
			
		}
	}
	
	public static void mainMenu() throws SQLException {
		UserDao userDao = new UserDao();
		Scanner scan = new Scanner(System.in);
		System.out.println("请选择操作:1.登陆;2.注册.");
		int operation = scan.nextInt();
		if(operation == 1) {
			
			System.out.println("请输入用户名:");
			String username = scan.next();
			System.out.println("请输入密码:");
			String password = scan.next();
			login(userDao, username, password);
		}else if(operation == 2) {
			System.out.println("请设置用户名:");
			String username = scan.next();
			System.out.println("请设置密码:");
			String password = scan.next();
			userDao.add(username, password);//设置不重名
			System.out.println("注册成功!");
		}else {
			System.out.println("操作有误!请重新操作...");
		}
	}
	
	public static void login(UserDao userDao, String username, String password) throws SQLException {
		List<User> list = userDao.findUserName();
		boolean flag = false;
		for (User user : list) {
			if(user.getUsername().equals(username)) {//用户名正确
				flag = true;
				break;
			}
		}
		if(flag == false) {
			System.out.println("不存在该用户!请重新操作...");
			mainMenu();
		}else {
			String query = userDao.findPasswordByUsername(username);
			if(query.equals(password)) {
				System.out.println("登陆成功!");
				userOperation(userDao, username);
			}else {
				System.out.println("密码错误!请重新操作...");
			}
		}
	}
	
	public static void userOperation(UserDao userDao, String username) throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("请选择操作:1.查询;2.存款;3.取款;4.转账;5.记录;6.退出.");//注销:UserDao del()
		int operation = scan.nextInt();
		if(operation == 1) {
			query(userDao, username);
			userOperation(userDao, username);
		}else if(operation == 2) {
			save(userDao, username);
			userOperation(userDao, username);
		}else if(operation == 3) {
			take(userDao, username);
			userOperation(userDao, username);
			
			
		}else if(operation == 4) {
			transfer(userDao, username);
			userOperation(userDao, username);
			
		}else if(operation == 5) {
			
		}else if(operation == 6) {
			System.out.println("退出成功!");
			mainMenu();
		}else {
			System.out.println("操作有误!请重新选择操作...");
			userOperation(userDao, username);
		}
	}
	
	//查询
	public static void query(UserDao userDao, String username) throws SQLException {
		int money = userDao.findMoneyByUserName(username);
		System.out.println("您的余额为:" + money);
	}
	
	//存钱
	public static void save(UserDao userDao, String username) throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入存款金额:");
		int save = scan.nextInt();
		userDao.update(username, userDao.findMoneyByUserName(username) + save);
		System.out.println("存款成功!您的余额为:" + userDao.findMoneyByUserName(username));
	}
	
	//取款
	public static void take(UserDao userDao, String username) throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入取款金额:");
		int take = scan.nextInt();
		if(take > userDao.findMoneyByUserName(username)) {
			System.out.println("余额不足!请重新选择操作...");
			userOperation(userDao, username);
		}else {
			userDao.update(username, userDao.findMoneyByUserName(username) - take);
			System.out.println("取款成功!您的余额为:" + userDao.findMoneyByUserName(username));
		}
	}
	
	//转账操作
	public static void transfer(UserDao userDao, String username) throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入转账金额:");
		int trans = scan.nextInt();
		if(trans > userDao.findMoneyByUserName(username)) {
			System.out.println("余额不足!请重新选择操作...");
			userOperation(userDao, username);
		}else {
			System.out.println("请输入对方账户:");
			String target = scan.next();
			
			List<User> list = userDao.findUserName();
			boolean flag = false;
			for (User user : list) {
				if(user.getUsername().equals(target)) {//目标用户名存在
					flag = true;
					break;
				}
			}
			if(flag == false) {
				System.out.println("不存在该用户!请重新选择操作...");
				userOperation(userDao, username);
			}else {
				userDao.update(username, userDao.findMoneyByUserName(username) - trans);
				userDao.update(target, userDao.findMoneyByUserName(target) + trans);
				System.out.println("转账成功!");
				
			}
		}
	}
}
