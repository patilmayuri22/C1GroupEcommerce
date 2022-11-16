package com.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Purchasing {
	
	//Show the product available in store.
	public static void productDetail() {	
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs= null;
		try {
			conn = DBUtility.getConnection();
			String query="select productId, productName, ProductDescription, productPrice from productdetails;";
		    pst =  conn.prepareStatement(query);
		    rs = pst.executeQuery();
		    while(rs.next()) {
		    	int prId=rs.getInt(1);
		    	System.out.println("Prodct ID >>"+ prId);
		    	
		    	String prName=rs.getString(2);
		    	System.out.println("Prodct Name >>"+ prName);
		    	
		    	String prDesc= rs.getString(3);
		    	System.out.println("Prodct Description >>"+ prDesc);
		    	
		    	double prPrice= rs.getInt(4);
		    	System.out.println("Prodct Price per piece >>"+ prPrice+"\n");
		    	
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
	
	public static int productPrice(int id) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs= null;
		int prId=0;
		try {
			conn = DBUtility.getConnection();
			String query="select productPrice from productdetails where productId= ?;";
		    pst =  conn.prepareStatement(query);
		    pst.setInt(1, id);
		    rs = pst.executeQuery();
		    while(rs.next()) {
		    prId=rs.getInt(1);}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				pst.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return prId;
	}
	
	public static String productName(int id) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs= null;
		String name= null;
		try {
			conn = DBUtility.getConnection();
			String query="select productName from productdetails where productId= ?;";
		    pst =  conn.prepareStatement(query);
		    pst.setInt(1, id);
		    rs = pst.executeQuery();
		    while(rs.next()) {
		    name=rs.getString(1);}
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
		return name;
	}
	
	public static String productDescription(int id) {
		Connection conn = null;
		PreparedStatement pst =null;
		ResultSet rs= null;
		String desc= null;
		try {
			conn = DBUtility.getConnection();
			String query="select ProductDescription from productdetails where productId= ?;";
		    pst =  conn.prepareStatement(query);
		    pst.setInt(1, id);
		    rs = pst.executeQuery();
		    while(rs.next()) {
		    desc=rs.getString(1);}
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
		return desc;
	}
	
	public  static void productInsert(int custID) 
	{
		Scanner sc= new Scanner(System.in);
		String str = "Yes";
		System.out.println("\t"+"\b"+"Below products are available at our Store."+"\b"+"\n");
		while ( str.length() >2 ) {
			Purchasing.productDetail();
			System.out.println("Please enter product ID you want to add in Cart : ");
			int prId= sc.nextInt();
			System.out.println("Please enter Quantity : ");
			int quantity= sc.nextInt();
			
			Connection  conn= DBUtility.getConnection();
			PreparedStatement pst = null, pst1=null, pst2= null;
			ResultSet rs = null,rs2 = null;
			String queryCustID = "select * from customerregister";
			String queryProdID = "select * from productcart where productId=?;";
			String queryProdCart = "insert into productCart (customerId, productId, product_Name,"
					+ "product_Detail,product_Quantity,product_Price,Total_Price) values(?,?,?,?,?,?,?)";
			try {
				pst = conn.prepareStatement(queryProdID);
				pst.setInt(1, prId);
				rs = pst.executeQuery();
				
				pst2 = conn.prepareStatement(queryCustID);
				rs2 = pst2.executeQuery();
				System.out.println("Customer ID : "+custID);
				/*
				 * while(rs2.next()) { custID =rs2.getInt(1);
				 * //System.out.println("Customer ID : "+custID); }
				 */
				
				if (rs.next()) {
					System.out.println("The product alredy available at Cart."
							+ "Please add another product");
				}
				else {
					pst1=conn.prepareStatement(queryProdCart);
					pst1.setInt(1, custID);
					pst1.setInt(2, prId);
					pst1.setString(3, Purchasing.productName(prId));
					pst1.setString(4, Purchasing.productDescription(prId));
					pst1.setInt(5, quantity);
					pst1.setInt(6, Purchasing.productPrice(prId));
					pst1.setInt(7, (Purchasing.productPrice(prId)*quantity));
					pst1.executeUpdate();
					
					System.out.println("Product "+Purchasing.productName(prId)+" added to Cart");			
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
			System.out.println();
			System.out.println("Did you want to add more Product to Card(Yes/No) ?");
			str=sc.next();
		}
		System.out.println("Total Products in your cart >>"+"\n");
		ProductCart.orderConform(custID);			// Ask for order Confirmation to customer
		ProductCart.productCartDetail(custID);
	}	
}
