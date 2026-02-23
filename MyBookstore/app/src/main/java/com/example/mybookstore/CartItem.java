package com.example.mybookstore;

public class CartItem {
    private String title;
    private String author;
    private double price;
    private int quantity;
    private int imageResId;

    // Constructor
    public CartItem(String title, String author, double price, int quantity, int imageResId) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    // Getters
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

    public int getImageResId() {
        return imageResId;
    }

    // Setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to get the total price for the item (price * quantity)
    public double getTotalPrice() {
        return price * quantity;
    }
}
