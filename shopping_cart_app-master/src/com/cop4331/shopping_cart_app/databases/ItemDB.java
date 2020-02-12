
package com.cop4331.shopping_cart_app.databases;



import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cop4331.shopping_cart_app.graphics.windowmanager.WindowManager;
import com.cop4331.shopping_cart_app.item.Item;




/**
 *
 * @author Justin Ament , Michael Mena
 */

public class ItemDB{

	private ArrayList<Item> items;
	private final String fileName = "Items.json";
	private static ItemDB INSTANCE;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	/**
	 * 
	 */
	public ItemDB() {
		items=new ArrayList<Item>();
		connect();
		buildDb();
		
	///	if(ifFileExists()) {
		//	load();
		//}else {
		//	buildInitialDB();
		//	save();
		//}
	
		}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Successful connection");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppingcartapp","root","");
			st=con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildDb() {
		try {
			//execute a query
			String query="select * from item";
			rs=st.executeQuery(query);
			String name;
			String description;
			int sellerID;
			int quantity;
			double price;
			double inv_price;

			while(rs.next()) {
				
				name=rs.getString("name");
				description=rs.getString("description");
				sellerID=Integer.parseInt(rs.getString("sellerID"));
				quantity=Integer.parseInt(rs.getString("quantity"));
				price=Double.parseDouble(rs.getString("price"));
				inv_price=Double.parseDouble(rs.getString("inv_price"));
				System.out.println(name+" "+description+" "+sellerID+" "+quantity+" "+price+" "+inv_price);
				Item a=new Item(name, description, sellerID, quantity, price, inv_price);
				items.add(a);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(items.isEmpty())
			buildInitialDB();
	}
	
	
	/**
	 * @returns INSTANCE
	 */
	public static ItemDB getInstance() {
		if(INSTANCE == null) {
			synchronized(WindowManager.class) {
				if(INSTANCE == null)
					INSTANCE = new ItemDB();
			}
		}
		return INSTANCE;
	}
	
	/**
	 * Forces creates INSTANCE
	 */
	public static void init() {
		getInstance();
	}

	/**
	 * build prefab DB
	 */
	private void buildInitialDB() {
		String sql="create table item(name varchar30, id int NOT NULL AUTO_INCREMENT, description varchar30, sellerID int, quantity int, price float, inv_price float)";
		try {
			st.execute(sql);
			sql="insert into item(name, description, sellerID, quantity, price, inv_price) values('Item1', 'Test Item', 2, 10, 10.0, 8.0)";
			st.execute(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		buildDb();
	}
	
    /**
     * @param itemID
     * @param new_quantity
     * updates the quantity of a certain item
     */
    public void setQuantity(int itemID, int new_quantity) {
    	items.get(itemID).setQuantity(new_quantity);
    	String query="update item set quantity="+Integer.toString(new_quantity)+" where id="+Integer.toString(itemID+1);
    	try {
    		st.execute(query);
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
 
    	}
    }
    
    /**
     * @param id
     * @returns items from a seller
     */
    public List<Item> getItemBySeller(int id) {
    	List<Item> seller_items=new ArrayList<Item>();
    	for(int i=0; i<items.size(); i++) {
    		if(items.get(i).getSellerID()==id) seller_items.add(items.get(i));
    	}
    	return seller_items;
    }
    /**
     * @returns item list
     */
    public List<Item> getFullInventory() {
    	return items;
    }
    
    /**
     * @param itemID
     * @returns item
     */
    public Item getItem(int itemID) {
    	return items.get(itemID);
    }
    
    /**
     * @param a
     * @return itemID
     */
    public int getItemID(Item a) {
    	int value=-1;
    	for(int i=0; i<items.size(); i++) {
    		if(a==items.get(i)) value=i;
    	}
    	return value; 
    }
    
    /**
     * @param a
     * adds item to DB
     */
    public void addItem(Item a) {
    	
    	items.add(a);
    	String sql="insert into item(name, description, sellerID, quantity, price, inv_price) values('"
    		+a.getName()+"', '"+a.getDescription()+"', "+a.getSellerID()+", "+a.getQuantity()+", "+a.getPrice()+", "
    		+a.getInvPrice()+")";   	
    	System.out.println(sql);
    	try {
			st.execute(sql);
			System.out.println("added item");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//save(); <--- too slow
    }
	
	
	/**
	 * @returns true if file exists
	 */
	private boolean ifFileExists() {
		return new File(fileName).exists();
	}
	 
	


}

