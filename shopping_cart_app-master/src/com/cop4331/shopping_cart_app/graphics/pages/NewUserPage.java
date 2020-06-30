package com.cop4331.shopping_cart_app.graphics.pages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
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

public class NewUserPage extends Page implements IPopUp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String username="";
	String password="";
	String acc_type="seller";
	JRadioButton bb;
	JRadioButton sb;
	boolean emptyf=false;
	boolean userexi=false;
    
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
		add(createPassInput());
		ButtonGroup group=new ButtonGroup();
		group.add(sellerButton());
		group.add(buyerButton());
		JPanel buttons=new JPanel();
		buttons.add(sellerButton());
		buttons.add(buyerButton());
		buttons.setPreferredSize(new Dimension(150,75));
		add(buttons);
		

		
		JButton addNewAcc = new JButton();
		addNewAcc.setPreferredSize(new Dimension(125,75));
		addNewAcc.setText("Add Account");		
		addNewAcc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JLabel err=new JLabel();
				err.setText("");
				add(err);
				if (error) {
					System.err.println("There is a error somewhere");
					return;
				}
				if(AccountDB.getInstance().checkExists(username)) {
					if(!userexi) {
						userexi=true;
						err.setText("Account Name is Taken");
						revalidate();
					}
				}
				if(username.equals("") || password.equals("")) {
					if(!emptyf) {
						emptyf=true;
						err.setText("One or more fields are Empty");
						revalidate();
					}
				}
				else {
					addAccount();
					WindowManager.getInstance().getMainWindow().SetPage(0);
					getWindow().close();
				}
			}
		});
		
		add(addNewAcc);
		
		JButton back=new JButton();
		back.setPreferredSize(new Dimension(125,75));
		back.setText("Return");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getWindow().close();
			}
		});
		add(back);
		
	}
	
	private void addAccount() {
		AccountDB.getInstance().createAccount(username, password, acc_type);
	}

	/**
	 * @returns nameInput
	 */
	
	private JRadioButton buyerButton() {
		bb=new JRadioButton();
		bb.setText("Buyer");
		bb.setActionCommand("buyer");
		bb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					acc_type=e.getActionCommand();
				}
			});
		
		bb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				acc_type="buyer";
				sb.setSelected(false);
			}
		});
		return bb;
		}

		
	private JRadioButton sellerButton() {
		sb=new JRadioButton();
		sb.setText("Seller");
		sb.setActionCommand("seller");
		sb.setSelected(true);
		
		sb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				acc_type="seller";
				bb.setSelected(false);
			}
		});
		return sb;
	}
	
	private JPanel createNameInput() {
		return createFormInput("Username: ", 400, 100, 0.25f, new DocumentListener() {

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
						username=str;
						
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
	private JPanel createPassInput() {
		return createFormInput("Password: ", 400, 100, 0.25f, new DocumentListener() {

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
						password=str;
						try {
							
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
