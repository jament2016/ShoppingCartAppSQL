package com.cop4331.shopping_cart_app.graphics.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.cop4331.shopping_cart_app.account.Customer;
import com.cop4331.shopping_cart_app.databases.AccountDB;
import com.cop4331.shopping_cart_app.databases.ItemDB;
import com.cop4331.shopping_cart_app.graphics.Page;
import com.cop4331.shopping_cart_app.graphics.pagemanager.PageManager;
import com.cop4331.shopping_cart_app.graphics.windowmanager.WindowManager;
import com.cop4331.shopping_cart_app.item.Item;

public class CartPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel itemContainerPanel;
	JLabel priceDisplay;
	/* (non-Javadoc)
	 * @see com.shopping_cart_app.graphics.Page#buildPage(com.shopping_cart_app.graphics.Window)
	 */
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
		//JPanel itemContainerPanel
	}
	/**
	 * builds header
	 */
	private void BuildHeadPanel() {
		JPanel headPanel = new JPanel(new FlowLayout());
		headPanel.setBackground(new Color(43f/255f,105f/255f,128f/255f));
		add(headPanel,BorderLayout.PAGE_START);
		
		headPanel.add(Box.createHorizontalStrut(30));// creates gap
		
		JButton KeepShoppingBtn = new JButton();
		KeepShoppingBtn.setPreferredSize(new Dimension(125,75));
		KeepShoppingBtn.setText("Keep Shopping");
		headPanel.add(KeepShoppingBtn);
		
		Page pageinQuestion = this;
		
		KeepShoppingBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getWindow().SetPage(PageManager.getInstance().getPageIndex(pageinQuestion)-1);
			}
		});
		
		
		JButton checkoutBtn = new JButton();
		checkoutBtn.setPreferredSize(new Dimension(125,75));
		checkoutBtn.setText("Checkout");
		headPanel.add(checkoutBtn);
		
		checkoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getWindow().SetPage(PageManager.getInstance().getPageIndex(pageinQuestion)+1);
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
		
		contentPanel.add(createItemContainerHeader());
		
		FlowLayout itemContainer_Layout = new FlowLayout();
		itemContainerPanel =  new JPanel(itemContainer_Layout);
		itemContainerPanel.setBackground(Color.black);
		
		JScrollPane scrollableItemContainer = new JScrollPane(itemContainerPanel);
		scrollableItemContainer.getVerticalScrollBar().setUnitIncrement(13);
		scrollableItemContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableItemContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//change to 450
		scrollableItemContainer.setPreferredSize(new Dimension(1200,550));
		contentPanel.add(scrollableItemContainer);
		
		//add the profit here
	}
	/**
	 * @returns ItemContainerHeader
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
		
		//Add the price to one of these headers
		ItemContainerHeader.add(Box.createHorizontalStrut(30));
		
		
		JPanel getPrice=new JPanel();
		getPrice.add(new JLabel("Total Price: "));
		priceDisplay = new JLabel();
		getPrice.add(priceDisplay);
		ItemContainerHeader.add(getPrice);
		//ItemContainerHeader.add(Box.createHorizontalStrut(30));
		
		ItemContainerHeader.setOpaque(false);
		ItemContainerHeader.setPreferredSize(new Dimension(1100, 25));
		
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
			int itemID = (int) keyset[i];
			JPanel itemPanel = createItem(itemID,cart.get(keyset[i]));
			itemPanel.setPreferredSize(new Dimension(1100, 100));
			itemContainerPanel.add(itemPanel);
		}
		
		
		FlowLayout itemContainer_Layout = (FlowLayout) itemContainerPanel.getLayout();
		int totalHeight = itemContainer_Layout .getHgap() +(100 + itemContainer_Layout.getHgap()) * keyset.length;
		itemContainerPanel.setPreferredSize(new Dimension(1200,totalHeight));
	}
	/**
	 * @param itemID
	 * @param qual
	 * @returns item display
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createItem(int itemID,int qual) {
		Item i = ItemDB.getInstance().getItem(itemID);
		JPanel itemDisplay = new JPanel(new GridLayout(1,2));
		
		JLabel itemName = new JLabel(i.getName(), SwingConstants.CENTER);
		Font itemTitle =  itemName.getFont();
		Map attributes = itemTitle.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		itemName.setForeground (Color.blue);
		itemName.setFont(itemTitle.deriveFont(attributes));
		itemDisplay.add(itemName);
		
		itemName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int popUpPageID = 4;
				DescriptionPage d = (DescriptionPage) PageManager.getInstance().getPage(popUpPageID);
				d.setItemToDisplay(i);
				
				WindowManager.getInstance().createNewWindow(popUpPageID, 500, 700);
			}
		});
		
		JLabel sellerName = new JLabel(AccountDB.getInstance().getAccount(i.getSellerID()).getUsername(), SwingConstants.CENTER);
		itemDisplay.add(sellerName);
		
		JLabel quantity = new JLabel(Integer.toString(qual), SwingConstants.CENTER);
		itemDisplay.add(quantity);
		
		JButton addBtn = new JButton("Add ( $" + i.getPrice()+ " )");
		itemDisplay.add(addBtn);
		
		CartPage page = this;
		
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				HashMap<Integer,Integer> cart = ((Customer)AccountDB.getInstance().getCurrentAccount()).cart;
				
				if(cart.containsKey(itemID)) {
					if(cart.get(itemID)<i.getQuantity())
						cart.put(itemID, cart.get(itemID) + 1 );
					
				}
				else {
					cart.put(itemID, 1)	;
					System.out.println("dwadw");
				}

				quantity.setText(Integer.toString(cart.get(itemID)));
				AccountDB.getInstance().addToCart(itemID, 1);
				
				page.updatePriceDisplay();
			}
		});
		
		JButton deleteBtn = new JButton("Delete");
		itemDisplay.add(deleteBtn);
		
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				HashMap<Integer,Integer> cart = ((Customer)AccountDB.getInstance().getCurrentAccount()).cart;
				
				if(cart.containsKey(itemID)) {
					
					if(cart.get(itemID) > 0) {
						cart.put(itemID, cart.get(itemID) - 1 );
						quantity.setText(Integer.toString(cart.get(itemID)));
						AccountDB.getInstance().addToCart(itemID, -1);
					}
					
					if(cart.get(itemID) < 1){
						cart.remove(itemID);
						//BuildItemContainer();
						//getWindow().repaint();
					}
						
				}
				
				page.updatePriceDisplay();
			}
		});
		
		return itemDisplay;
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
	 * updates Price Display 
	 */
	private void updatePriceDisplay() {
		priceDisplay.setText("$"+((Customer)AccountDB.getInstance().getCurrentAccount()).getTotalPrice());
	}
}
