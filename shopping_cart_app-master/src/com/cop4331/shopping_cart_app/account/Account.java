/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cop4331.shopping_cart_app.account;

/**
 *
 * @author Justin Ament
 */
public class Account {
    protected  String username="";
    protected  String password="";
    
    /**
     * @param username
     * @param password
     */
    public Account(String username, String password) {
    	this.username=username;
    	this.password=password;
    }
    
    /**
     * 
     */
    public Account() {
    	//account doesn't exist, no credentials
    }
    
    /**
     * @returns username
     */
    public String getUsername() {
    	return this.username;
    }
    
    /**
     * @returns password
     */
    public String getPassword() {
    	return this.password;
    }
}
//could use inheritance pattern
//could seperate seller and buyer by files