package com.cop4331.shopping_cart_app.graphics.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.cop4331.shopping_cart_app.account.Customer;
import com.cop4331.shopping_cart_app.account.Seller;
import com.cop4331.shopping_cart_app.databases.AccountDB;
import com.cop4331.shopping_cart_app.databases.ItemDB;
import com.cop4331.shopping_cart_app.graphics.Page;
import com.cop4331.shopping_cart_app.graphics.pagemanager.PageManager;
import com.cop4331.shopping_cart_app.item.Item;

public class CheckOutPage extends Page {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel itemContainerPanel;
	JLabel priceDisplay;
	/* (non-Javadoc)
	 * @see com.cop4331.shopping_cart_app.graphics.Page#buildPage()
	 */
	@Override
	protected void buildPage() {
		// TODO Auto-generated method stub
		
		setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		
		BuildHeadPanel();
		BuildContentPanel();
	}
	/**
	 * builds header
	 */
	private void BuildHeadPanel() {
		JPanel headPanel = new JPanel(new FlowLayout());
		headPanel.setBackground(new Color(43f/255f,105f/255f,128f/255f));
		add(headPanel,BorderLayout.PAGE_START);
		
		headPanel.add(Box.createHorizontalStrut(30));// creates gap
		
		JButton backToCheckOutBtn = new JButton();
		backToCheckOutBtn.setPreferredSize(new Dimension(125,75));
		backToCheckOutBtn.setText("Back To Check Out");
		headPanel.add(backToCheckOutBtn);
		
		Page pageinQuestion = this;
		
		backToCheckOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getWindow().SetPage(PageManager.getInstance().getPageIndex(pageinQuestion)-1);
			}
		});
		
		
		JButton payBtn = new JButton();
		payBtn.setPreferredSize(new Dimension(125,75));
		payBtn.setText("Pay");
		headPanel.add(payBtn);
		
		payBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				HashMap<Integer,Integer> oldcart  = ((Customer)AccountDB.getInstance().getCurrentAccount()).cart;
				Object[] keyset = oldcart.keySet().toArray();
				AccountDB.getInstance().clearCart();
				for (int i = 0; i < keyset.length; i++) {
					int itemID = (int) keyset[i];
					int qual = oldcart.get(keyset[i]);
					
					int new_quantity = ItemDB.getInstance().getItem(itemID).getQuantity() - qual;
					System.out.println("New Item quantity: "+ new_quantity);
					if(new_quantity < 0) {
						new_quantity = 0;
						System.err.println("the new quantity is negative... how?");
					}
					Seller currSeller=(Seller) AccountDB.getInstance().getAccount(ItemDB.getInstance().getItem(itemID).getSellerID());
					double cost=qual*(Double.parseDouble(ItemDB.getInstance().getItem(itemID).getInvPrice()));
					double revenue=qual*(Double.parseDouble(ItemDB.getInstance().getItem(itemID).getPrice()));
					
					AccountDB.getInstance().addRev(revenue, itemID);
					AccountDB.getInstance().addCost(cost, itemID);
					
					currSeller.addRev(revenue);
					currSeller.addCost(cost);
					
					ItemDB.getInstance().setQuantity(itemID, new_quantity);
					
				}
				
				
				((Customer)AccountDB.getInstance().getCurrentAccount()).cart = new HashMap<Integer,Integer>();
				
				getWindow().SetPage(1);
				
			}
		});
	}
	/**
	 * builds body
	 */
	private void BuildContentPanel() {
		JPanel contentPanel = new JPanel(new FlowLayout());
		contentPanel.setBackground(Color.gray);
		contentPanel.setOpaque(false);
		add(contentPanel,BorderLayout.CENTER);

		JPanel cartview = new JPanel(new FlowLayout());
		cartview.setOpaque(false);
		cartview.setPreferredSize(new Dimension(400,600));
		cartview.add(createItemContainerHeader());
		
		FlowLayout itemContainer_Layout = new FlowLayout();
		itemContainerPanel =  new JPanel(itemContainer_Layout);
		itemContainerPanel.setBackground(Color.black);
		
		JScrollPane scrollableItemContainer = new JScrollPane(itemContainerPanel);
		scrollableItemContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableItemContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollableItemContainer.setPreferredSize(new Dimension(400,520));
		cartview.add(scrollableItemContainer);
		
		JPanel getPrice=new JPanel();
		getPrice.add(new JLabel("Total Price: "));
		priceDisplay = new JLabel();
		getPrice.add(priceDisplay);
		cartview.add(getPrice);
		
		
		contentPanel.add(cartview);
		
		JPanel formPanel = new JPanel(new FlowLayout());
		formPanel.setBackground(Color.LIGHT_GRAY);
		formPanel.setOpaque(false);
		formPanel.setPreferredSize(new Dimension(600,550));
		contentPanel.add(formPanel);
		
		JLabel shipping = new JLabel("Shipping Address", SwingConstants.CENTER);
		shipping.setBackground(Color.WHITE);
		shipping.setOpaque(true);
		shipping.setPreferredSize(new Dimension(600, 50));
		
		formPanel.add(shipping);
		
		formPanel.add(createFormInput("Full name",600, 50,0.3f));
		formPanel.add(createFormInput("Address",300, 50,0.3f));
		formPanel.add(createFormInput("City",150, 50,0.3f));
		formPanel.add(createFormInput("State / Province / Region",390, 50,0.5f));
		formPanel.add(createFormInput("Postal code",190, 50,0.5f));
		formPanel.add(createFormInput("Country/Region",450, 50,0.3f));
		formPanel.add(createFormInput("Phone number",300, 50,0.3f));
		
		JLabel card = new JLabel("Payment Method", SwingConstants.CENTER);
		card.setBackground(Color.WHITE);
		card.setOpaque(true);
		card.setPreferredSize(new Dimension(600, 50));
		
		formPanel.add(card);
		
		formPanel.add(createFormInput("Card number",600, 50,0.3f));
		formPanel.add(createFormInput("Name on card",600, 50,0.3f));
		formPanel.add(createFormInput("Expiration date",300, 50,0.3f));
		
	}
	/**
	 * @param text
	 * @param width
	 * @param height
	 * @param labelWidthPercentage
	 * @returns FormInput
	 */
	private JPanel createFormInput(String text, int width, int height, float labelWidthPercentage) {
		JPanel input = new JPanel(new FlowLayout());
		input.setPreferredSize(new Dimension(width, height));
		
		JLabel lab = new JLabel(text, SwingConstants.CENTER);
		lab.setPreferredSize(new Dimension((int) (width*labelWidthPercentage - 5),(int) (height* 0.9 -2.5)));
		lab.setBackground(Color.BLUE);
		input.add(lab);
		
		JTextField field = new JTextField();
		field.setPreferredSize(new Dimension((int) (width*(1-labelWidthPercentage)- 5),(int) (height* 0.9 -2.5)));
		input.add(field);
		
		return input;
	}
	/**
	 * @returns Item Container Header
	 */
	private JPanel createItemContainerHeader() {
		JPanel ItemContainerHeader = new JPanel(new GridLayout(1,2));
		
		JLabel itemName = new JLabel("Name", SwingConstants.CENTER);
		itemName.setBackground(Color.white);
		itemName.setOpaque(true);
		ItemContainerHeader.add(itemName);
		
		JLabel seller = new JLabel("Sold by", SwingConstants.CENTER);
		seller.setBackground(Color.white);
		seller.setOpaque(true);
		ItemContainerHeader.add(seller);
		
		JLabel quantity = new JLabel("Quantity", SwingConstants.CENTER);
		quantity.setBackground(Color.white);
		quantity.setOpaque(true);
		ItemContainerHeader.add(quantity);

		
		ItemContainerHeader.setOpaque(false);
		ItemContainerHeader.setPreferredSize(new Dimension(300, 25));
		
		return ItemContainerHeader;
	}
	/**
	 * Builds Item Container
	 */
	private void BuildItemContainer() {
		
		//contentPanel.add(itemContainerPanel);
		
		itemContainerPanel.removeAll();
		
		HashMap<Integer,Integer> cart = ((Customer)AccountDB.getInstance().getCurrentAccount()).cart;
		Object[] keyset = cart.keySet().toArray();
		for (int i = 0; i < keyset.length; i++) {
			
			Item item = ItemDB.getInstance().getItem((int) keyset[i]);
			JPanel itemPanel = createItem(item,cart.get(keyset[i]));
			itemPanel.setPreferredSize(new Dimension(300, 100));
			itemContainerPanel.add(itemPanel);
			}
		
		
		FlowLayout itemContainer_Layout = (FlowLayout) itemContainerPanel.getLayout();
		int totalHeight = itemContainer_Layout .getHgap() +(100 + itemContainer_Layout.getHgap()) * keyset.length;
		itemContainerPanel.setPreferredSize(new Dimension(400,totalHeight));
	}
	/**
	 * @param i
	 * @param qual
	 * @returns item display
	 */
	private JPanel createItem(Item i,int qual) {
		JPanel item = new JPanel(new GridLayout(1,2));
		
		JLabel itemName = new JLabel(i.getName(), SwingConstants.CENTER);
		item.add(itemName);
		
		JLabel sellerName = new JLabel(AccountDB.getInstance().getAccount(i.getSellerID()-1).getUsername(), SwingConstants.CENTER);
		item.add(sellerName);
		
		JLabel quantity = new JLabel(Integer.toString(qual), SwingConstants.CENTER);
		item.add(quantity);
		
		return item;
	}
	/* (non-Javadoc)
	 * @see com.cop4331.shopping_cart_app.graphics.Page#load()
	 */
	@Override
	protected void load() {
		// TODO Auto-generated method stub
		super.load();
		BuildItemContainer();
		updatePriceDisplay();
	}
	/**
	 * updates PriceDisplay
	 */
	private void updatePriceDisplay() {
		priceDisplay.setText("$"+((Customer)AccountDB.getInstance().getCurrentAccount()).getTotalPrice());
	}
}
