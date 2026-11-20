package com.example.orders.model;
public final class Order<T extends Product> {
    private final int id;
    private final T product;
    public Order(int id, T product) {
        if (id <= 0 || product == null) throw new IllegalArgumentException("Invalid order");
        this.id = id; this.product = product;
    }
    public int getId() { return id; }
    public T getProduct() { return product; }
}
