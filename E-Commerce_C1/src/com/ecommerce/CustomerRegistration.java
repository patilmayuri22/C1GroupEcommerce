package com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerRegistration {

	public static void adminInsert() {
		Connection conn = null;
		PreparedStatement pst = null,pst1 = null;
		int customerID = 100;
		
		conn = DBUtility.getConnection();
		Scanner scan = new Scanner(System.in);
		System.out.println("\t"+"\b"+" WelCome to Store! "+"\b"+"\n"+"Please Enter the mobile No: ");
		String customerMbNo = scan.next();
		System.out.println("Please Enter your Name: ");
		String customerName = scan.next();
		System.out.println("Please Enter your City Name: ");
		String customerAddress = scan.next();
		
		try 
		{
			String selectLastID = "select * from customerregister where customerID = ?";
			pst1 = conn.prepareStatement(selectLastID);
			pst1.setInt(1, customerID);
			ResultSet rs = pst1.executeQuery();
			while(rs.next())
			{
				customerID = rs.getInt(1);
				customerID = customerID + 1;
			}
			
			customerID++;
			String query = "insert into customerregister(customerID,customerName,customerContactNo,customerAddress) values(?,?,?,?)";
			pst = conn.prepareStatement(query);
			pst.setInt(1, customerID);
			pst.setString(2, customerName );
			pst.setString(3, customerMbNo);
			pst.setString(4, customerAddress);
			int record = pst.executeUpdate();
			
			System.out.println("Dear Customer, ID: "+customerID+" Name : "+customerName+" your registration completed successfully!"+"\n");
			
			Purchasing.productInsert(customerID); //Insert insert into cart
			
			//Purchasing.productDetail(); //Product adding in cart	
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pst.close();
				pst1.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public static void adminMenu(String customerMbNo)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome Administration : "+customerMbNo+"\n");
		
		System.out.println("Press 1 : for Registered Customer List");
		System.out.println("Press 2 : for Product Stock List");
		System.out.println("Press 3 : for Customer Wise Purchase History");
		System.out.println("Press 4 : for Update Product in Product list");
		System.out.println("Press 5 : Exit");
		
		System.out.println("\n"+"Please Press any No : ");			
        int choice = scanner.nextInt();
        
        switch (choice) 
        {
			case 1: 
			{
				System.out.println("\t"+"Registered Customer List"+"\n");
				CustomerRegisteredList.customerRegisteredList();
				
				System.out.println("Do you want to check any others details? (Yes/No)");
				String repeat = scanner.next();
				if(repeat.equalsIgnoreCase("Yes")) {
				adminMenu(customerMbNo);
				}
				break;
			}
			case 2:
			{
				System.out.println("\t"+"Product Stock details "+"\n");
				ProductStockDetails.productStock();
				
				System.out.println("Do you want to check any others details? (Yes/No)");
				String repeat = scanner.next();
				if(repeat.equalsIgnoreCase("Yes")) {
				adminMenu(customerMbNo);
				}
				break;
			}
			case 3:
			{
				System.out.println("\t"+"Customer Wise Purchase History"+"\n");
				CustomerRegisteredList.customerWisePurchaseHistory();
				
				System.out.println("Do you want to check any others details? (Yes/No)");
				String repeat = scanner.next();
				if(repeat.equalsIgnoreCase("Yes")) {
				adminMenu(customerMbNo);
				}
				break;
			}
			case 4:
			{
				System.out.println("\t"+"Update ProductS  in Product list"+"\n");
				ProductDetails.productList();
				
				System.out.println("Do you want to check any others details? (Yes/No)");
				String repeat = scanner.next();
				if(repeat.equalsIgnoreCase("Yes")) {
				adminMenu(customerMbNo);
				}
				break;
			}
			case 5:
			{
				break;
			}
              	// default - invalid input
             default:
                        	System.out.println("Invalid Input");
                        	//adminMenu(customerMbNo);
			}
	}
	
	public static void admincheck() {
		Connection conn = null;
		PreparedStatement pst = null;
		conn = DBUtility.getConnection();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("\t"+"\b"+" Welcome to store "+"\b");
		System.out.println("Please enter Mobile number: ");
		String customerMbNo =scan.next();
		String custName = null;
		String adminNo = "9960049095";
		
		if(customerMbNo.length() == 10)
		{
			if(customerMbNo.equals(adminNo))
			{
				adminMenu(customerMbNo);
			}else
			{
				String query="select * from customerregister where customerContactNo = ?";
				try {
					pst = conn.prepareStatement(query);
					pst.setString(1, customerMbNo);
								
					ResultSet rs = pst.executeQuery();
					if(rs.next())
					{
						int customerID = rs.getInt(1);
						custName = rs.getString(2);
						System.out.println("\t"+"WelCome to Store- "+custName);
						System.out.println("\t"+"Customer ID : "+rs.getInt(1)+"\n");	
						Purchasing.productInsert(customerID); //If customer exits in DB its redirect to product purchase.
						System.out.println("");
					}else {
						System.out.println("\n"+"You are not register Customer, Please register your details: ");
						adminInsert();			//If Customer mobile is not exits redirect to insert new customer details
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		}else {
			System.out.println("\n"+"SORRY!... Please enter 10 digit mobile No:");
			admincheck();
		}
	}	
	
	public static void main(String[] args)
	{
		CustomerRegistration.admincheck();	//Checks customer Mobile No is exits or not
		
		//CustomerRegisteredList.customerWisePurchaseHistory();
	}
}
