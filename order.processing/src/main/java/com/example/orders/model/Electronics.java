package com.example.orders.model;
import lombok.Builder;
public final class Electronics extends Product {
    private final int warrantyMonths;
    @Builder
    public Electronics(String name, double price, int warrantyMonths) {
        super(name, price);
        if (warrantyMonths < 0) throw new IllegalArgumentException("Warranty cannot be negative");
        this.warrantyMonths = warrantyMonths;
    }
    public int getWarrantyMonths() { return warrantyMonths; }
    @Override public String toString() { return getName() + " (electronics, " + getPrice() + " UAH)"; }
}
