package com.cop4331.shopping_cart_app.account;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.cop4331.shopping_cart_app.databases.ItemDB;
import com.cop4331.shopping_cart_app.item.Item;


public class Customer extends Account {
	
	public HashMap<Integer,Integer> cart;
	/**
	 * @param username
	 * @param password
	 */
	public Customer(String username, String password) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.password = password;
	}
	/**
	 * @param username
	 * @param password
	 * @param cartStr
	 */
	public Customer(String username, String password, String cartStr) {
		super(username,password);
		this.cart = new HashMap<Integer,Integer>();
		
		if(!cartStr.isEmpty()) {
			String[] entry = cartStr.split(",");
			
			for (int i = 0; i < entry.length; i++) {
				String[] l = entry[i].split(":");
				
				//First int=id, second int=quantity
				int itemID = Integer.parseInt(l[0]);
				int qual = Integer.parseInt(l[1]);
				
				cart.put(itemID, qual);
			}
		}
	}
	
	/**
	 * @returns cart as a string format
	 */
	public String cartToString() {
		String str = "";
		Object[] keyset = cart.keySet().toArray();
		for (int i = 0; i < keyset.length; i++) {
			int itemID = (int) keyset[i];
			int qual = cart.get(keyset[i]);
			
			str += itemID + ":" + qual;
			
			if(i < keyset.length - 1)
				str += ",";
			
		}
		
		return str;
	}

	/**
	 * @returns total price
	 */
	public String getTotalPrice() {
		double total_price=0;
		Object[] keyset=cart.keySet().toArray();
		for(int i=0; i<keyset.length; i++) {
			Item item=ItemDB.getInstance().getItem((int) keyset[i]);
			total_price+=(Double.parseDouble(item.getPrice())*cart.get(keyset[i]));
		}
		DecimalFormat form=new DecimalFormat("0.00");
		return form.format(total_price);
	}
	

}
