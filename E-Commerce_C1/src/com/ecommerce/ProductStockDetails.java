package com.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductStockDetails 
{
	public static void productStock() 
	{
		Connection conn = null;
		PreparedStatement pst = null;
				
		conn = DBUtility.getConnection();
		String query = "select * from productdetails";
		
		try {
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) 
			{
				System.out.println("Product ID: "+ rs.getInt(1)+"\t"
				+" Name: "+rs.getString(2)+"\t"
				+" Discription: "+rs.getString(3)+"\t"
				+" Price: Rs."+rs.getDouble(4)+"\t"
				+" Total Quantity: "+rs.getInt(5)+"\n");
				//System.out.println("Product Name: "+rs.getString(2));
				//System.out.println("Product Discription: "+rs.getString(3));
				//System.out.println("Product Price: Rs."+rs.getDouble(4));
				//System.out.println("Product Quantity: "+rs.getInt(5));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void productStockAgainestProID() {
		Connection conn = null;
		PreparedStatement pst = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("Please Enter ProductID : ");
		int productID = scan.nextInt();
				
		conn = DBUtility.getConnection();
		String query = "select * from productdetails where productID = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, productID);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) 
			{
				System.out.println("Product ID: "+ rs.getInt(1)+"\t"
				+" Product Name: "+rs.getString(2)+"\t"
				+" Discription: "+rs.getString(3)+"\t"
				+" Product Price: Rs."+rs.getDouble(4)+"\t"
				+" Total Quantity in Stock: "+rs.getInt(5)+"\n");
				//System.out.println("Product Name: "+rs.getString(2));
				//System.out.println("Product Discription: "+rs.getString(3));
				//System.out.println("Product Price: Rs."+rs.getDouble(4));
				//System.out.println("Product Quantity: "+rs.getInt(5));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
