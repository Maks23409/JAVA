package com.example.orders.processing;
import com.example.orders.model.Order;
import com.example.orders.model.Product;
public final class OrderProcessor<T extends Product> {
    public void process(Order<T> order) {
        if (order == null) throw new OrderProcessingException("Order is null");
        if (order.getProduct().getPrice() <= 0) throw new OrderProcessingException("Invalid price");
        System.out.printf("[%s] Order #%d: %s%n", Thread.currentThread().getName(),
                order.getId(), order.getProduct().getName());
    }
}
