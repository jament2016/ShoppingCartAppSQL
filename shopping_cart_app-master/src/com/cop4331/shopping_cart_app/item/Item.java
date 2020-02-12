
package com.cop4331.shopping_cart_app.item;

import java.text.DecimalFormat;

/**
 *
 * @author Justin Ament
 */

public class Item {
    private  String name="";
    private  String item_description="";
    private int quantity=0;
    private  int sellerID=-1; 
    private  double price=0;
	private double invPrice=0;
	
    /**
     * @param name
     * @param item_description
     * @param sellerID
     * @param quantity
     * @param price
     * @param invPrice
     */
    public Item(String name, String item_description, int sellerID, int quantity, double price, double invPrice) {
        this.name=name;
        this.item_description=item_description;
        this.sellerID=sellerID;
        this.quantity=quantity;
        this.price=price;
        this.invPrice=invPrice;
    }
    
    //Item doesn't exist
    /**
     * 
     */
    public Item() {

    }
    
    /**
     * @returns Name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return Description
     */
    public String getDescription() {
        return item_description;
    }
    
    /**
     * @return SellerID
     */
    public int getSellerID() {
        return sellerID;
    }
    
    /**
     * @return Quantity
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * @return Price
     */
    public String getPrice() { 
    	DecimalFormat form=new DecimalFormat("0.00");
    	return form.format(price);
    }
    
    /**
     * @return InvPrice
     */
    public String getInvPrice() {
    	DecimalFormat form=new DecimalFormat("0.00");
    	return form.format(this.invPrice);
    }
    
    /**
     * @param q
     * sets Quantity to q
     */
    public void setQuantity(int q) {
    	this.quantity = q;
    }
    /**
     * @returns item toString
     */
    public String print() {
    	return name+" "+ item_description + " " + Integer.toString(quantity) + " " + Double.toString(price);
    }
}
