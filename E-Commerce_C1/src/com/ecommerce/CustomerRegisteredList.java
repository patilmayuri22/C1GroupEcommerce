package com.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerRegisteredList {

	public static void customerRegisteredList() {
		
		Connection conn = null;
		PreparedStatement pst = null;
		conn = DBUtility.getConnection();
		
		Scanner scan = new Scanner(System.in);
		
		String query="select * from customerregister";
		try {
			pst = conn.prepareStatement(query);
						
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				System.out.println("Customer ID: "+rs.getString(1)+"\t"
						+" Customer Name: "+rs.getString(2)+"\t"
						+" Customer Contact No: "+rs.getString(3)+"\t"
						+" Customer Address: "+rs.getString(4)+"\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void customerWisePurchaseHistory() {
		
		Connection conn = null;
		PreparedStatement pst = null;
		conn = DBUtility.getConnection();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please Enter Customer ID: ");
		int custID = scan.nextInt();
		String query="select * from userhistory where customerId= ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, custID);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				System.out.println("Customer ID >> "+rs.getInt(1));
				System.out.println("Purchase Date >> "+ rs.getString(2));
		    	System.out.println("Prodct ID >> "+ rs.getInt(3));
		    	System.out.println("Prodct Desc >> "+ rs.getString(4));
		    	System.out.println("Prodct Price >> "+ rs.getDouble(5));
		    	System.out.println("Product Name >> "+ rs.getString(6));
		    	System.out.println("Product Quantity >> "+ rs.getInt(7));
		    	System.out.println("Total Purchase Bill >> "+ rs.getDouble(8)+"\n");
				
				/*
				 * System.out.println("Customer ID: "+rs.getString(1)+"\t"
				 * +" Customer Name: "+rs.getString(2)+"\t"
				 * +" Customer Contact No: "+rs.getString(3)+"\t"
				 * +" Customer Address: "+rs.getString(4)+"\n");
				 */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

}
