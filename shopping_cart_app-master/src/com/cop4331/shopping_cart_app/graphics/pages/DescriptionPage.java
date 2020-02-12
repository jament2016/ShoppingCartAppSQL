/**
 * 
 */
package com.cop4331.shopping_cart_app.graphics.pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.cop4331.shopping_cart_app.databases.AccountDB;
import com.cop4331.shopping_cart_app.graphics.IPopUp;
import com.cop4331.shopping_cart_app.graphics.Page;
import com.cop4331.shopping_cart_app.item.Item;

/**
 * @author mmena2017
 *
 */
public class DescriptionPage extends Page implements IPopUp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Item item;
	JLabel itemName,sellerName,itemDescription;
	/* (non-Javadoc)
	 * @see com.cop4331.shopping_cart_app.graphics.Page#buildPage()
	 */
	@Override
	protected void buildPage() {
		// TODO Auto-generated method stub
		super.buildPage();
		
		this.setLayout(new FlowLayout());
		
		itemName = new JLabel("NULL", SwingConstants.CENTER);	
		itemName.setPreferredSize(new Dimension(400,100));
		itemName.setOpaque(true);
		itemName.setBackground(Color.white);
		
		Font itemTitle = new Font("serif", Font.BOLD, 28);
		itemName.setFont(itemTitle);
		
		add(itemName);
		
		sellerName = new JLabel("NULL", SwingConstants.CENTER);
		sellerName.setPreferredSize(new Dimension(400,100));
		sellerName.setBackground(Color.white);
		sellerName.setOpaque(true);
		add(sellerName);
		
		itemDescription = new JLabel("NULL", SwingConstants.CENTER);
		itemDescription.setPreferredSize(new Dimension(400,100));
		itemDescription.setBackground(Color.white);
		itemDescription.setOpaque(true);
		add(itemDescription);
		
		
		
	}
	/* (non-Javadoc)
	 * @see com.cop4331.shopping_cart_app.graphics.Page#load()
	 */
	@Override
	protected void load() {
		// TODO Auto-generated method stub
		super.load();
		
		itemName.setText(item.getName()); 
		sellerName.setText("Sold by "+ AccountDB.getInstance().getAccount(item.getSellerID()).getUsername()); 
		itemDescription.setText(item.getDescription()); 
	}
	
	/**
	 * @param i
	 * sets Item To Display 
	 */
	public void setItemToDisplay(Item i) {
		this.item = i;
	}
}
