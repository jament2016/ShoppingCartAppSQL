/**
 * 
 */
package com.cop4331.shopping_cart_app.graphics.windowmanager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.WindowConstants;

import com.cop4331.shopping_cart_app.App;
import com.cop4331.shopping_cart_app.databases.AccountDB;
import com.cop4331.shopping_cart_app.databases.ItemDB;
import com.cop4331.shopping_cart_app.graphics.Window;

/**
 * @author mmena2017
 *
 */
public class WindowManager {
	private List<Window> windows;
	private static WindowManager INSTANCE = null;
	
	/**
	 * 
	 */
	private WindowManager() {
		windows = new ArrayList<Window>();
		
		createNewWindow(0);
		
		getMainWindow().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	
	
	/**
	 * @returns INSTANCE
	 */
	public static WindowManager getInstance() {
		if(INSTANCE == null) {
			synchronized(WindowManager.class) {
				if(INSTANCE == null)
					INSTANCE = new WindowManager();
			}
		}
		return INSTANCE;
	}
	
	/**
	 * force creates INSTANCE
	 */
	public static void init() {
		getInstance();
	}


	/**
	 * @param pageIndex
	 * create a new window with page attached
	 */
	public void createNewWindow(int pageIndex) {
		createNewWindow(pageIndex,App.WINDOW_WIDTH,App.WINDOW_HEIGHT);
	}
	
	/**
	 * @param pageIndex
	 * @param width
	 * @param height
	 * create a new window with page attached
	 */
	public void createNewWindow(int pageIndex,int width,int height) {
		// TODO Auto-generated method stub
		System.out.println("Building new window :" + 
				((windows.size() == 0) ? "(MAIN)" : "(POPUP)")
		);
		Window w = new Window();
		
		w.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
            	System.out.println("Page Number: " +pageIndex);
            	if(pageIndex!=6) {
            		if(pageIndex!=7) {
            			if(pageIndex!=4) {
		            		super.windowClosing(e);
							AccountDB.getInstance().close();
							ItemDB.getInstance().close();
            			}
            		}
            	}
                System.out.println("A window had been closed");
                windows.remove(e.getWindow());
                e.getWindow().dispose();
            }
        });

		w.setSize(width,height);
		
		w.SetPage(pageIndex);
		windows.add(w);
	}
	/**
	 * @returns main window
	 */
	public Window getMainWindow() {
		return windows.get(0);
	}


	/**
	 * @returns the number of items
	 */
	public int count() {
		// TODO Auto-generated method stub
		return windows.size();
	}
}
