package com.example.mybookstore;

import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;
    private ArrayList<CartItem> cartItems;
    private double totalPrice;

    private CartManager() {
        cartItems = new ArrayList<>();
        totalPrice = 0.0;
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(String title, String author, double price, int quantity, int imageResId) {
        CartItem item = findItemByTitle(title);
        if (item != null) {
            // Update quantity and recalculate total price for this item
            item.setQuantity(item.getQuantity() + quantity);
            totalPrice += price * quantity; // Add new total price
        } else {
            // Add new item to cart
            cartItems.add(new CartItem(title, author, price, quantity, imageResId));
            totalPrice += price * quantity; // Add the total price of this item
        }

    }


    // Helper method to find a cart item by its title
    private CartItem findItemByTitle(String title) {
        for (CartItem item : cartItems) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        return null; // Return null if the item was not found
    }

    // Update the quantity of an existing cart item and recalculate the total price
    public void updateItemQuantity(String title, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getTitle().equals(title)) {
                totalPrice -= item.getPrice() * item.getQuantity(); // Remove old price
                item.setQuantity(quantity); // Update quantity
                totalPrice += item.getPrice() * item.getQuantity(); // Add new price
                break;
            }
        }
    }

    // Get the total price of the cart
    public double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
        totalPrice = 0.0;
    }

    // Inner class to represent a cart item
    public static class CartItem {
        private String title, author;
        private double price;
        private int quantity, imageResId;

        public CartItem(String title, String author, double price, int quantity, int imageResId) {
            this.title = title;
            this.author = author;
            this.price = price;
            this.quantity = quantity;
            this.imageResId = imageResId;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getImageResId() {
            return imageResId;
        }
    }
}

