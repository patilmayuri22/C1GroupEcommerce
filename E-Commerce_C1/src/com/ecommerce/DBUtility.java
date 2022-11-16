package com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility 
{

	public static Connection getConnection()
	{
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url="jdbc:mysql://localhost:3306/ecommerce?characterEncoding=utf8";
			conn = DriverManager.getConnection(url,"root","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
