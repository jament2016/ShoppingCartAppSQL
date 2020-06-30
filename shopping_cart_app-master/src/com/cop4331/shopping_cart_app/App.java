package com.cop4331.shopping_cart_app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.cop4331.shopping_cart_app.databases.AccountDB;
import com.cop4331.shopping_cart_app.databases.ItemDB;
import com.cop4331.shopping_cart_app.graphics.pagemanager.PageManager;
import com.cop4331.shopping_cart_app.graphics.windowmanager.WindowManager;

public class App {
	
	public static final int WINDOW_WIDTH = 1280;/// CHANGE RES HERE ONLY!
	public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9; // 16 by 9 aspect ratio
	
	/**
	 * 
	 */
	public App() {	
		
		ItemDB.init();
		AccountDB.init();
		
		
		
		PageManager.init();
		WindowManager.init();
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		new App();
		
	}

}
