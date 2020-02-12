/**
 * 
 */
package com.cop4331.shopping_cart_app.graphics;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author mmena2017
 *
 */
public abstract class Page extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Page() {
		// TODO Auto-generated constructor stub
		super(null);
		buildPage();
	}
	
	/**
	 * build pages
	 */
	protected void buildPage() {
	}
	
	/**
	 * @returns the window the page lives on
	 */
	protected Window getWindow() {
		return (Window) SwingUtilities.getWindowAncestor(this);
	}

	/**
	 * called everytime a page is loaded
	 */
	protected void load() {
		System.out.println("Loading Page "+this.getClass().getName());
	}
}
