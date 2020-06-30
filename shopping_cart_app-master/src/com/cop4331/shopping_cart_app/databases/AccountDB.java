package com.cop4331.shopping_cart_app.databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.cop4331.shopping_cart_app.account.Account;
import com.cop4331.shopping_cart_app.account.Customer;
import com.cop4331.shopping_cart_app.account.Seller;
import com.cop4331.shopping_cart_app.graphics.windowmanager.WindowManager;

/**
 *
 * @author Justin Ament, Michael Mena
 */
public class AccountDB {
    public int currentAccount_ID;
    private ArrayList<Account> accounts;
    private final String fileName="Accounts.json";
    private static AccountDB INSTANCE;
	private Connection con;
	private Statement st;
	private Statement a;
	private Statement b;
	private Statement c;
	private Statement d;
	private ResultSet rs;
	
    /**
     * 
     */
    public AccountDB() {
    	this.currentAccount_ID = -1;
    	connect();
    	loadAccounts();
		
    }
    
    public void connect() {
    	boolean fail=false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String conn="";
			String user="";
			String pass="";
			File f=new File("lib/Conn.txt");
			FileReader fr=new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			String line;
			int i=0;
			
			while((line=br.readLine())!=null) {
				if(i==0) 
					conn=line;
				else if(i==1)
					user=line;
				else if(i==2)
					pass=line;
				i++;
			}
			
			br.close();
			fr.close();

		
			
			con=DriverManager.getConnection(conn,user, pass);
			System.out.println("Connection");
			st=con.createStatement();
			a=con.createStatement();
			b=con.createStatement();
			c=con.createStatement();
			d=con.createStatement();
			

		}
		catch (Exception e) {
			fail=true;
			e.printStackTrace();
		}
	}
    
    public void close() {
    	try {
    		System.out.println("Closing Account Connections...");
			rs.close();
			st.close();
			con.close();
			a.close();
			b.close();
			c.close();
			d.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void addRev(double price, int itemID) {
    	String sellerID=Integer.toString(ItemDB.getInstance().getItem(itemID).getSellerID());
    	String sql="update seller set revenue=revenue+"+Double.toString(price)+" where id="+sellerID;
    	System.out.println(sql);
    	try {
    		c.execute(sql);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void addCost(double price, int itemID) {
    	String sellerID=Integer.toString(ItemDB.getInstance().getItem(itemID).getSellerID());
    	String sql="update seller set cost=cost+"+Double.toString(price)+" where id="+sellerID;
    	System.out.println(sql);
    	try {
    		d.execute(sql);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * @returns INSTANCE
     */
    public static AccountDB getInstance() {
		if(INSTANCE == null) {
			synchronized(WindowManager.class) {
				if(INSTANCE == null)
					INSTANCE = new AccountDB();
			}
		}
		return INSTANCE;
	}
    
    /**
     * forces creates the INSTANCE
     */
    public static void init() {
    	
    	getInstance();
    }
    
    //Returns true if the account is in the database, returns false if not
    //use getAccount method next
    /**
     * @param username
     * @param password
     * @returns true if the account is in the database, returns false if not use getAccount method next
     */
    public boolean verify(String username, String password) {
    	for(int i=0; i<accounts.size(); i++) {
    		if(accounts.get(i).getUsername()==username && accounts.get(i).getPassword()==password) {
    			currentAccount_ID=getAccountID(username, password);
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * @param username
     * @returns account
     */
    protected Account getAccByUsername(String username) {
    	
    	for(int i=0; i<accounts.size(); i++) {
    		if(accounts.get(i).getUsername().equals(username)) {
    			return accounts.get(i);
    		}
    	}
    	return new Account();
    }
    
    
    //Returns an empty account if the account is not found, else returns the count if it is found
    //Method to be called after verify methodS
    /**
     * @param username
     * @param password
     * @returns an empty account if the account is not found, else returns the count if it is found 
     * Method to be called after verify methodS
     */
    public Account getAccount(String username, String password) {
    	for(int i=0; i<accounts.size(); i++) {
    		if(accounts.get(i).getUsername()==username && accounts.get(i).getPassword()==password) {
    			return accounts.get(i);
    		}
    	}
    	return new Account();
    }
    
    /**
     * @param username
     * @param password
     * @return accountID
     */
    public int getAccountID(String username, String password) {
    	for(int i=0; i<accounts.size(); i++) {
    		Account a = accounts.get(i);
    		if(a.getUsername().equals(username) && a.getPassword().equals(password)) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    /**
     * @param id
     * @returns account
     */
    public Account getAccount(int id) {
    	return accounts.get(id);
    }
    
    /**
     * builds a prefab DB
     */
    private void loadAccounts() {
    	accounts=new ArrayList<Account>();
    	String query="select * from account";
    	System.out.println(query);
    	try {
    		rs=st.executeQuery("select * from account");

    		String username;
    		String password;
    		String acc_type;
    		int id;
    		
    		while(rs.next()) {
    			System.out.println("result" + rs.getString("username"));
    			int i=0;
    			username=rs.getString("username");
    			password=rs.getString("Password");
    			id=Integer.parseInt(rs.getString("id"));
    			System.out.println(username + password + id);
    			if(rs.getString("acc_type").equals("seller")) {
    				
    				//need to add revenue and cost
    				
    				String sql="select * from seller where id="+Integer.toString(id);
    				System.out.println("sql: " + sql);
    				
    				ResultSet q=a.executeQuery(sql);
    				double cost=0;
    				double revenue=0;
    				while(q.next()) {
    					cost=Double.parseDouble(q.getString("cost"));
    					revenue=Double.parseDouble(q.getString("revenue"));
    				}   				
    				accounts.add(new Seller(username, password, revenue, cost));
    			}
    			
    			//needs to load the cart
    			else {
    				
    				String itemID="";
    				String quantity="";
    				String cart="";
    				String sql="select * from cart where customerID="+Integer.toString(id);
    				System.out.println("sql: "+sql);
    				ResultSet r=b.executeQuery(sql);
	    			while(r.next()) {
	    				
	    				itemID=r.getString("itemID");
	    				quantity=r.getString("quantity");
	    				if(cart.equals(""))
	    					cart+=itemID+":"+quantity;
	    				else cart +=","+itemID+":"+quantity;
	    			}
	    			System.out.println("loading cart: "+cart);
	    			accounts.add(new Customer(username, password, cart));
	    			
    			}			
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		
    	}
    	
    	if(accounts.isEmpty()) {
    		createInitialAccounts();
    	}
    	
    	System.out.println("Total accounts: "+ accounts.size());
    	
    }
    
    public void createInitialAccounts() {
		// TODO Auto-generated method stub
		String sql="create table account(username VARCHAR2(15), password VARCHAR2(15),"
				+ " acc_type VARCHAR2(15), id NUMBER generated by default on null as identity, PRIMARY KEY(id))";
		
		try {
			st.execute(sql);
			sql="create table cart(customerID number, itemID number, quantity number)";
			st.execute(sql);
			sql="create table seller(id number, revenue binary_float, cost binary_float)";
			st.execute(sql);
			
			sql="insert into account values ('justin','1234','seller',0)";
			st.execute(sql);
			sql="insert into account(username, password, acc_type) values('justinbuyer', '1234', 'buyer')";
			st.execute(sql);
			//sellers id = 2
			sql="insert into account(username, password, acc_type) values('justinseller', '1234', 'seller')";
			st.execute(sql);
			sql="insert into seller values (2,0,0)";
			st.execute(sql);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		loadAccounts();
		
	}


    
    /**
     * @return
     */
    private boolean ifFileExists() {
		return new File(fileName).exists();
	}
    
    /**
     * @returns currentAccount
     */
    public Account getCurrentAccount() {
    	return accounts.get(currentAccount_ID);
    }

	public void addToCart(int itemID, int add) {
		// TODO Auto-generated method stub
		int id=currentAccount_ID;
		//new item to cart
		
		
		String query;
		String sql="select * from cart";
		boolean exists=false;
		System.out.println("Current customerID = "+id);
		System.out.println("Item Id Adding: "+itemID+" quantity: "+add);
		try {
			rs=st.executeQuery(sql);
			while(rs.next()) {
				if(itemID==(Integer.parseInt(rs.getString("itemID"))) && id==(Integer.parseInt(rs.getString("customerID")) )) {
					exists=true;
				}
			}
			if(exists) {
				query="update cart set quantity=quantity+("+Integer.toString(add) + ") where itemID="+Integer.toString(itemID)+" and customerID="+Integer.toString(id);
			}
			else {
				query="insert into cart(customerID, itemID, quantity) values("+Integer.toString(id)+", "+Integer.toString(itemID)+", 1)";
			}
					
			st.execute(query);
			query="delete from cart where quantity=0";
			st.execute(query);
			System.out.println("adding item to cart");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void createAccount(String username, String password, String acc_type) {
		String sql;
		String query;
		
		//acc type has to be buyer or seller
		try {
			sql="Insert into account values('"+username+"', '"+password+"', '"+acc_type+"',null)";
			st.execute(sql);
			if("acc_type".equals("seller")) {
				query="select id from account where username='"+username+"'";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					int id=Integer.parseInt(rs.getString("id"));
					sql="insert into seller values("+id+",0,0)";
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void clearCart() {
		// TODO Auto-generated method stub
		String sql="Delete from cart where customerID="+Integer.toString(currentAccount_ID);
		try {
			st.execute(sql);
		}
		catch(Exception e) {
			
		}
	}

	public boolean checkExists(String username) {
		// TODO Auto-generated method stub
		for(int i=0; i<accounts.size(); i++) {
			if(accounts.get(i).getUsername().equals(username))
				return true;
		}
		return false;
	}
}
