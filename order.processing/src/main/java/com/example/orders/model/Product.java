package com.example.orders.model;
public abstract class Product {
    private final String name;
    private final double price;
    protected Product(String name, double price) {
        if (name == null || name.isBlank() || price <= 0) throw new IllegalArgumentException("Invalid product");
        this.name = name; this.price = price;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
