/**
 * 
 */
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
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.cop4331.shopping_cart_app.account.Seller;
import com.cop4331.shopping_cart_app.databases.AccountDB;
import com.cop4331.shopping_cart_app.databases.ItemDB;
import com.cop4331.shopping_cart_app.graphics.Page;
import com.cop4331.shopping_cart_app.graphics.pagemanager.PageManager;
import com.cop4331.shopping_cart_app.graphics.windowmanager.WindowManager;
import com.cop4331.shopping_cart_app.item.Item;

/**
 * @author mmena2017
 *
 */
public class InventoryPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel itemContainerPanel;
	JLabel getProf,getRev,getCosts;
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
		
		JButton LogOutBtn = new JButton();
		LogOutBtn.setPreferredSize(new Dimension(125,75));
		LogOutBtn.setText("Logout");
		headPanel.add(LogOutBtn);
		
		LogOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getWindow().SetPage(0);
			}
		});
		
		
		JButton addNewItemBtn = new JButton();
		addNewItemBtn.setPreferredSize(new Dimension(125,75));
		addNewItemBtn.setText("Add Item");
		headPanel.add(addNewItemBtn);
		
		addNewItemBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(WindowManager.getInstance().count() > 1) {
					System.err.println("you are already adding an item!");
					return;
				}
				
				int popUpPageID = 6;
				
				WindowManager.getInstance().createNewWindow(popUpPageID, 500, 700);
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
		
		itemContainerPanel =  new JPanel(new FlowLayout());
		itemContainerPanel.setBackground(Color.black);
		
		BuildItemContainer();
		
		JScrollPane scrollableItemContainer = new JScrollPane(itemContainerPanel);
		scrollableItemContainer.getVerticalScrollBar().setUnitIncrement(13);
		scrollableItemContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableItemContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollableItemContainer.setPreferredSize(new Dimension(1200,520));
		contentPanel.add(scrollableItemContainer);
		
		JPanel info=new JPanel();
		getProf=new JLabel();
		getRev=new JLabel();
		getCosts=new JLabel();
		info.add(getCosts);
		info.add(getRev);
		info.add(getProf);
		
		contentPanel.add(info);
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
		
		JLabel Price = new JLabel("Price", SwingConstants.CENTER);
		Price.setBackground(Color.white);
		Price.setOpaque(true);
		ItemContainerHeader.add(Price);
		
		JLabel invPrice = new JLabel("Invoice Price", SwingConstants.CENTER);
		invPrice.setBackground(Color.white);
		invPrice.setOpaque(true);
		ItemContainerHeader.add(invPrice);
		
		JLabel quantity = new JLabel("Quantity", SwingConstants.CENTER);
		quantity.setBackground(Color.white);
		quantity.setOpaque(true);
		ItemContainerHeader.add(quantity);

		
		ItemContainerHeader.setOpaque(false);
		ItemContainerHeader.setPreferredSize(new Dimension(1100, 25));
		
		return ItemContainerHeader;
	}
	/**
	 * Build Item Container
	 */
	private void BuildItemContainer() {
		
		itemContainerPanel.removeAll();
		
		List<Item> itemsSearched = ItemDB.getInstance().getFullInventory();
		double total_rev=0;
		for (int i = 0; i < itemsSearched.size(); i++) {
			if(itemsSearched.get(i).getSellerID() != AccountDB.getInstance().currentAccount_ID)
				continue;
			JPanel item = createItem(itemsSearched.get(i));
			item.setPreferredSize(new Dimension(1100, 100));
			itemContainerPanel.add(item);			
		}
		
		int totalHeight = ((FlowLayout) itemContainerPanel.getLayout()).getHgap() +(100 + ((FlowLayout) itemContainerPanel.getLayout()).getHgap()) * itemsSearched.size();
		itemContainerPanel.setPreferredSize(new Dimension(1200,totalHeight));
	}
	/**
	 * @param i
	 * @return item display
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createItem(Item i) {
		JPanel item = new JPanel(new GridLayout(1,2));
		
		JLabel itemName = new JLabel(i.getName(), SwingConstants.CENTER);
		Font itemTitle =  itemName.getFont();
		Map attributes = itemTitle.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		itemName.setForeground (Color.blue);
		itemName.setFont(itemTitle.deriveFont(attributes));
		item.add(itemName);
		
		itemName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int popUpPageID = 4;
				DescriptionPage d = (DescriptionPage) PageManager.getInstance().getPage(popUpPageID);
				d.setItemToDisplay(i);
				
				WindowManager.getInstance().createNewWindow(popUpPageID, 500, 700);
			}
		});
		
		JLabel price = new JLabel("$"+(i.getPrice()), SwingConstants.CENTER);
		item.add(price);
		
		JLabel invPrice=new JLabel("$"+(i.getInvPrice()), SwingConstants.CENTER);
		item.add(invPrice);
		
		JTextField quantity = new JTextField(Integer.toString(i.getQuantity()), SwingConstants.CENTER);
		item.add(quantity);
		
		quantity.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				update();
			}
			
			void update() {
				int newQuantity = - 1;
				
				try {
					newQuantity = Integer.parseInt(quantity.getText());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					System.err.println("not a number");
					return;
				}
				
				if(newQuantity < 0) {
					System.err.println("can't have negatives");
					return;
				}
				
				int itemID = ItemDB.getInstance().getItemID(i);
				ItemDB.getInstance().setQuantity(itemID, newQuantity);
			}
		});
		
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
		
		if(AccountDB.getInstance().currentAccount_ID !=-1) {
			Seller currAcc=((Seller) AccountDB.getInstance().getCurrentAccount());
			getProf.setText("Total profit: $"+(currAcc.getProfit()));
			getRev.setText("Total Revenue: $"+(currAcc.getRevenue()));
			getCosts.setText("Total Costs: $"+(currAcc.getCost()));
		}
	}
	

}
