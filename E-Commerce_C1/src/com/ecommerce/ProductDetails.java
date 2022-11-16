package com.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductDetails {
	
		//Insert new product
     	public static void insertProductDetails(int productId, String productDescription, double productPrice,
        String productName, int productQuantity) {
     	Connection connection = null;
    	PreparedStatement ps = null;
     	try {
              	connection = DBUtility.getConnection();
              	String query = "insert into productdetails(productId, productDescription, productPrice, productName, productQuantity) values (?,?,?,?,?)";
              	ps = connection.prepareStatement(query);
               	ps.setInt(1, productId);
              	ps.setString(2, productDescription);
              	ps.setDouble(3, productPrice);
              	ps.setString(4, productName);
              	ps.setInt(5, productQuantity);

              	int i = ps.executeUpdate();
              	System.out.println("Product " + i + " is inserted successfully..");
              	// System.out.println();
     	} catch (SQLException e) {
              	e.printStackTrace();
    	} finally {
              	try {
                        	connection.close();
                        	ps.close();
              	} catch (SQLException e) {
                        	e.printStackTrace();
              	}
    	}
}
     	

     	public static void getProductDetailsForInsertProduct()
     	{
     		Scanner scanner = new Scanner(System.in);
     		System.out.println("Please enter product details for products record");
     		System.out.print("Enter no of products : ");
     		int no = scanner.nextInt();

    	// for loop for get no of product details
     		for (int i = 1; i <= no; i++) 
     		{
              	System.out.print("Enter Product Id : ");
              	int productId = scanner.nextInt();
              	System.out.print("Enter Product Description : ");
              	String productDescription = scanner.next();
              	System.out.print("Enter Product Price : ");
              	double productPrice = scanner.nextDouble();
              	System.out.print("Enter Product Name : ");
              	String productName = scanner.next();
              	System.out.print("Enter Product Quantity : ");
              	int productQuantity = scanner.nextInt();

              	// insertProductDetails method invoke
              	ProductDetails.insertProductDetails(productId, productDescription, productPrice, productName,
                                  	productQuantity);
              	System.out.println("Please enter next product details..");
    	}
}

     	//method for select product details and save into product details table
     	public static void selectAllProductDetails() 
     	{
     		Connection connection = null;
     		PreparedStatement ps = null;
     		ResultSet rs = null;
     		try {
              		connection = DBUtility.getConnection();
              		String query = "select * from productdetails";
              		ps = connection.prepareStatement(query);
              		rs = ps.executeQuery();

              		System.out.println("Product ID\tProduct Description\tProduct Price\tProduct Name\tProduct Quantity");
              		System.out.println("----------------------------------------------------------------------------------");
              		while (rs.next()) 
              		{  	 
                        	  System.out.println(rs.getInt("productId") +"\t\t" +rs.getString("productDescription") 
                        	  +"\t\t" +rs.getDouble("productPrice")+"\t\t"+ rs.getString("productName")
                        	  +"\t\t"+ rs.getInt("productQuantity"));
              		}
     			} catch (SQLException e) 
     			{
     				e.printStackTrace();
     			} finally {
     			try {
                        	connection.close();
                        	ps.close();
                        	rs.close();
              	} catch (Exception e) 
     			{
                        	e.printStackTrace();
              	}
    	}
}

// method for update product details and save into product details table
     	public static void updateProductDetails(int productId, double productPrice, int productQuantity) {
     		Connection connection = null;
     		PreparedStatement ps = null;
	     	try {
	              	connection = DBUtility.getConnection();
	               	String query = "update productDetails set productPrice=?, productQuantity=? where productId =?";
	              	ps = connection.prepareStatement(query);
	               	ps.setDouble(1, productPrice);
	              	ps.setInt(2, productQuantity);
	              	ps.setInt(3, productId);
	
	              	int i = ps.executeUpdate();
	              	System.out.println("Product " + i + " is updated successfully..");
	              	// System.out.println();
	     	} catch (SQLException e) {
	              	// TODO Auto-generated catch block
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

// method for get input from admin for no of product details which admin want to
// update in product details table
		public static void getProductDetailsForUpdateProduct() 
		{
	    	Scanner scanner = new Scanner(System.in);
	     	System.out.println("\t"+"Update Product details"+"\n");
	     	System.out.println("\n"+"Please enter the Number for, How many product you want to update? : ");
	    	int no = scanner.nextInt();
	     	for (int i = 1; i <= no; i++) {
	     			System.out.print("Enter Product Id : ");
	     			int productId = scanner.nextInt();
	               	System.out.print("Enter Product Price : ");
	              	double productPrice = scanner.nextDouble();
	              	System.out.print("Enter Product Quantity : ");
	              	int productQuantity = scanner.nextInt();
	              	// updateProductDetails method invoke
	              	ProductDetails.updateProductDetails(productId, productPrice, productQuantity);
	    	}

		}
		
		public static void deleteProductDetails() {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Please enter product id which you want to delete : ");
			int productId = scanner.nextInt();
			Connection connection = null;
			PreparedStatement ps = null;		
			try {
				connection = DBUtility.getConnection();
				String query = "delete from productDetails where productId=?";
				ps = connection.prepareStatement(query);
				ps.setInt(1, productId);
				int i = ps.executeUpdate();
				System.out.println(productId + " Products deleted successfully..");
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

		
		//method for display menu list to admin
		public static void menu() 
		{
	    	System.out.println("Select choice :");
	    	System.out.println("1. INSERT PRODUCT");
	    	System.out.println("2. SELECT(DISPLAY) ALL PRODUCT DETAILS");
	    	System.out.println("3. UPDATE PRODUCT DETAILS");
	    	System.out.println("4. DELETE PRODUCT");
	    	System.out.println("5. EXIT"); 	
	    }
		
	public static void productList()  
	{
     	Scanner scanner = new Scanner(System.in);
           int choice;
    	String str = " ";
    	do {
              	menu();
              	choice =scanner.nextInt();
              	switch(choice) {
              	// case 1 - insert product details
              	case 1:
              	      	ProductDetails.getProductDetailsForInsertProduct();
                        	break;
              	// case 2 - select product details
              	case 2:
                        	ProductDetails.selectAllProductDetails();
                        	break;
              	// case 3 - Update product details
              	case 3:
              	      	ProductDetails.getProductDetailsForUpdateProduct();
                        	break;
              	// case 4 - delete product details
              	case 4:
              		deleteProductDetails();
                        	break;
              	// case 5 - exit
              	case 5:
                        	break;
              	// default - invalid input
              	default:
                        	System.out.println("Invalid Input");
               	}
              	System.out.print("Do you want to continue (Yes/No) :");
              	str = scanner.next();
              	System.out.println();
    	}while(str.equalsIgnoreCase("Yes"));
	}
}
