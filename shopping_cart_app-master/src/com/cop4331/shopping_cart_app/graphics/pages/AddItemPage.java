/**
 * 
 */
package com.cop4331.shopping_cart_app.graphics.pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.cop4331.shopping_cart_app.databases.AccountDB;
import com.cop4331.shopping_cart_app.databases.ItemDB;
import com.cop4331.shopping_cart_app.graphics.IPopUp;
import com.cop4331.shopping_cart_app.graphics.Page;
import com.cop4331.shopping_cart_app.graphics.windowmanager.WindowManager;
import com.cop4331.shopping_cart_app.item.Item;

/**
 * @author mmena2017
 *
 */
public class AddItemPage extends Page implements IPopUp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name = "NULL";
    String item_description = "NULL";
    int quantity;
    float price;
    float invPrice;
    
    boolean error = false;
	
	/* (non-Javadoc)
	 * @see com.cop4331.shopping_cart_app.graphics.Page#buildPage()
	 */
	@Override
	protected void buildPage() {
		// TODO Auto-generated method stub
		super.buildPage();
		
		setBackground(Color.BLACK);
		this.setLayout(new FlowLayout());
		
		add(createNameInput());
		add(createDescriptionInput());
		add(createQualtityInput());
		add(createPriceInput());
		add(createInvPriceInput());
		
		JButton addNewItemBtn = new JButton();
		addNewItemBtn.setPreferredSize(new Dimension(125,75));
		addNewItemBtn.setText("Add Item");
		
		addNewItemBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (error) {
					System.err.println("There is a error somewhere");
					return;
				}
				
				ItemDB.getInstance().addItem(new Item(name,item_description,AccountDB.getInstance().currentAccount_ID,quantity,price, invPrice));
				
				WindowManager.getInstance().getMainWindow().SetPage(5);
				
				getWindow().close();
			}
		});
		add(addNewItemBtn);
	}

	/**
	 * @returns nameInput
	 */
	private JPanel createNameInput() {
		return createFormInput("Name", 400, 100, 0.25f, new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				void update(DocumentEvent e) {
					int l = e.getDocument().getLength();
					try {
						String str = e.getDocument().getText(0,l);
						
						name = str;
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		);
	}
	
	/**
	 * @returns DescriptionInput
	 */
	private JPanel createDescriptionInput() {
		return createFormInput("Description", 400, 100, 0.25f, new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				void update(DocumentEvent e) {
					int l = e.getDocument().getLength();
					try {
						String str = e.getDocument().getText(0,l);
						
						item_description = str;
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		);
	}
	/**
	 * @return QualtityInput
	 */
	private JPanel createQualtityInput() {
		return createFormInput("Qualtity", 200, 100, 0.25f, new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				void update(DocumentEvent e) {
					int l = e.getDocument().getLength();
					try {
						String str = e.getDocument().getText(0,l);
						
						try {
							quantity = Integer.parseInt(str);
							error = false;
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							error = true;
						}
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		);
	}
	/**
	 * @return PriceInput
	 */
	private JPanel createPriceInput() {
		return createFormInput("Price", 200, 100, 0.25f, new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				void update(DocumentEvent e) {
					int l = e.getDocument().getLength();
					try {
						String str = e.getDocument().getText(0,l);
						
						try {
							price = Float.parseFloat(str);
							error = false;
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							error = true;
						}
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		);
	}
	
	/**
	 * @returns InvPriceInput
	 */
	private JPanel createInvPriceInput() {
		return createFormInput("Invoice Price", 400, 100, 0.25f, new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
					update(e);
				}
				
				void update(DocumentEvent e) {
					int l = e.getDocument().getLength();
					try {
						String str = e.getDocument().getText(0,l);
						
						try {
							invPrice = Float.parseFloat(str);
							error = false;
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							error = true;
						}
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		);
	}
	/* (non-Javadoc)
	 * @see com.cop4331.shopping_cart_app.graphics.Page#load()
	 */
	@Override
	protected void load() {
		// TODO Auto-generated method stub
		super.load();
	}
	/**
	 * @param text
	 * @param width
	 * @param height
	 * @param labelWidthPercentage
	 * @param documentListener
	 * @returns FormInput
	 */
	private JPanel createFormInput(String text, int width, int height, float labelWidthPercentage, DocumentListener documentListener) {
		JPanel input = new JPanel(new FlowLayout());
		input.setPreferredSize(new Dimension(width, height));
		
		JLabel lab = new JLabel(text, SwingConstants.CENTER);
		lab.setPreferredSize(new Dimension((int) (width*labelWidthPercentage - 5),(int) (height* 0.9 -2.5)));
		lab.setBackground(Color.BLUE);
		input.add(lab);
		
		JTextField field = new JTextField();
		field.setPreferredSize(new Dimension((int) (width*(1-labelWidthPercentage)- 5),(int) (height* 0.9 -2.5)));
		field.getDocument().addDocumentListener(documentListener);
		input.add(field);
		
		return input;
	}
}
