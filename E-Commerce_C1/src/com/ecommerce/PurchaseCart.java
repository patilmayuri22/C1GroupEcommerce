package com.ecommerce;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.print.attribute.HashPrintJobAttributeSet;

public class PurchaseCart {
	
	public static void totalProductBill(int CustID){
		Connection conn = null;
		PreparedStatement pst = null;
		conn = DBUtility.getConnection();

		String query = "Select sum(Total_price)from productcart where customerID = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, CustID);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				System.out.println("\n"+"\t"+"Total Bill= "+rs.getInt(1));
				billProcess(CustID);
			}
			} catch (SQLException e) {
				e.printStackTrace();
				}
	   }

	   public static void billProcess(int CustID) {
		   int  i = 1;
		   Scanner sc1 = new Scanner(System.in);
		   while(i==1) {
			   System.out.println("\n"+"Do you want to Process your Bill(Yes/No)");
		       String BillProcess = sc1.next();
		       if (BillProcess.equalsIgnoreCase("No")) {
		    	   PurchaseCart.billProcess(CustID);
		    	   i=1;		    	   
		       } else{
		    	   System.out.println("Your Bill is processsed!!! ");
		    	   i=0;
		    	   insertPurchaseCartDetails(CustID);
		       }
		   }
	   }
	   
	   public static void insertPurchaseCartDetails(int CustID) 
	   {	
			Connection conn = null, connection = null;
			PreparedStatement pst =null, ps = null,pst2 = null;
			ResultSet rs= null;
			int prId = 1;
			String product_Detail = null, prName = null; 
			double prPrice = 0, prTotalPrice= 0;
			int prQuantity = 0; 
			//Purchase Date 
			LocalDate myObj = LocalDate.now(); 
			String strDate = myObj.toString();
			
			try {
				conn = DBUtility.getConnection();

				String query="select * from productcart where customerId= ?";
			    pst =  conn.prepareStatement(query);
			    pst.setInt(1, CustID);
			    rs = pst.executeQuery();
			        
			    while(rs.next()) {
			    	
			    	prId=rs.getInt(2);
			    	System.out.println("Prodct ID >> "+ prId);
			    	prName=rs.getString(3);
			    	System.out.println("Prodct Name >> "+ prName);
			    	product_Detail= rs.getString(4);
			    	System.out.println("Prodct Details >> "+ product_Detail);
			    	prQuantity = rs.getInt(5);
			    	System.out.println("Product Quantity >> "+ prQuantity);
			    	prPrice= rs.getDouble(6);
			    	System.out.println("Prodct Price >> "+ prPrice);
			    	prTotalPrice = rs.getDouble(7);
			    	System.out.println("Total Price >> "+ prTotalPrice+"\n");
			    	
			    	//totalProductBill(CustID);
			    	   	
			    	connection = DBUtility.getConnection(); 
			    	String query1 = "insert into userhistory(customerId, purchaseDate , productId, productDescription, "
			    			+ "productPrice, productName, productQuantity, totalPrice) values (?,?,?,?,?,?,?,?)"; 
			    	ps = connection.prepareStatement(query1); 
			    	ps.setInt(1, CustID);
			    	ps.setString(2, strDate);
			   		ps.setInt(3, prId); 
			   		ps.setString(4, product_Detail); 
			   		ps.setDouble(5,prPrice); 
			   		ps.setString(6, prName); 
			   		ps.setInt(7, prQuantity);
			   		ps.setDouble(8, prTotalPrice);
			   		int i = ps.executeUpdate(); 
			   		System.out.println("Data stored successfully! Thank you.");
			   		
			   		if(i>0)
			   		{
			   			deletePurchaseCart(CustID);
			   		}else 
			   		{
			   			System.out.println("Purchase Cart insertPurchaseCartDetails()");
			   		}
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					conn.close();
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	   

	  public static void deletePurchaseCart(int CustID) {

		  	Connection connection = null;
      		PreparedStatement ps = null;
      	try {
      		connection = DBUtility.getConnection();
      		String query = "delete from productcart where customerId=?";
	        ps = connection.prepareStatement(query);
	        ps.setInt(1, CustID);
	        int i = ps.executeUpdate();
	        //System.out.println(i+ " Product is deleted successfully..");
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        } finally {
	        	try {
	        		connection.close();
	        		ps.close();
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        }
      }
}
