package com.example.orders.model;
import lombok.Builder;
public final class Clothing extends Product {
    private final String size;
    @Builder
    public Clothing(String name, double price, String size) {
        super(name, price);
        if (size == null || size.isBlank()) throw new IllegalArgumentException("Size required");
        this.size = size;
    }
    public String getSize() { return size; }
    @Override public String toString() { return getName() + " (clothing, " + getPrice() + " UAH)"; }
}
