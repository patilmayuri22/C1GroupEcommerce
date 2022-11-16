package com.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductCart 
{
	public static void productCartDetail(int customerID) {	
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs= null;
		try {
			conn = DBUtility.getConnection();
			String query="select * from productcart where customerID = ?;";
		    pst =  conn.prepareStatement(query);
		    pst.setInt(1, customerID);
		    //System.out.println("Customer ID ="+customerID);
		    rs = pst.executeQuery();
		    
		    while(rs.next()) {
		    	int cusId=rs.getInt(1);
		    	System.out.println("Customer ID >> "+ cusId);
		    	int prId=rs.getInt(2);
		    	System.out.println("Product ID >> "+ prId);
		    	String prName=rs.getString(3);
		    	System.out.println("Name >> "+ prName);
		    	String prDesc=rs.getString(4);
		    	System.out.println("Name Desc>> "+ prDesc);
		    	String prQuantity = rs.getString(5);
		    	System.out.println("Quantity >> "+ prQuantity);
		    	double prPrice= rs.getInt(6);
		    	System.out.println("Price per piece >> "+ prPrice);
		    	double totalPrice= rs.getInt(7);
		    	System.out.println("Total Price = "+ totalPrice);
		    	System.out.println();
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void processOnCart(int CustID) {
		Connection conn=DBUtility.getConnection();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please press the No for perform operation on cart: ");
		System.out.println("\b"+"  1. Insert new Product in Cart");
		System.out.println("\b"+"  2. Delete Product from Cart");
		System.out.println("\b"+"  3. Update quantity of Product in Cart");
		
		int input= sc.nextInt();			//Input for operation on cart
		
		String str = "Yes";
		if (input ==  1) 
		{
			Purchasing.productInsert(CustID);			//Product re-adding
		}
		else if (input == 2) {//Delete product from cart
			while (str.length() > 2) {
			PreparedStatement pst=null;
			System.out.println("Please enter Product ID which you want to Delete from cart: ");
			int prId=sc.nextInt();
			String query="delete from ProductCart where productId=?;";
			try {
				pst=conn.prepareStatement(query);
				pst.setInt(1, prId);
				pst.executeUpdate();
				System.out.println("The Product Deleted from Cart.");
				productCartDetail(CustID);
				System.out.println("Did you want to Delete more Product from Cart (Yes/No).");
				str=sc.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  }
		}
		
		else if (input == 3) {//Update product form cart 
			while (str.length() > 2) {
				PreparedStatement pst1=null;
				System.out.println("Please enter Product ID which you want to Update quantity: ");
				int prId=sc.nextInt();
				System.out.println("Please enter new quantity: ");
				int quantity=sc.nextInt();
				String query1="update ProductCart set product_Quantity=?, total_price=? where ProductId=?;;";
				try {
					pst1=conn.prepareStatement(query1);
					pst1.setInt(1, quantity);
					pst1.setInt(2, (Purchasing.productPrice(prId)*quantity));
					pst1.setInt(3, prId);
					pst1.executeUpdate();
					System.out.println("The new Quantity of product is Updated!");
					
					productCartDetail(CustID);
					
					System.out.println("Did you want to Update more Product from Cart (Yes/No).");
					str=sc.next();
				} catch (SQLException e) {
					e.printStackTrace();}
			}		
		}		
	}
			
	
	public static void orderConform(int CustID) 
	{
		productCartDetail(CustID);
		
		int i=1;
		Scanner sc1= new Scanner(System.in);
		while (i==1) {
		System.out.println("Did you want to continue for billing(Yes/No)");
		String ordCon = sc1.next();
		if (ordCon.equalsIgnoreCase("No")) {
			ProductCart.processOnCart(CustID);
			i=1;
		}
		else {
			PurchaseCart.totalProductBill(CustID);		//total Billing amount process
			i=0;
		}
		}	
	}
}
