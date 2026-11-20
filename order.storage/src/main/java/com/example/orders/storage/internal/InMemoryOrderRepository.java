package com.example.orders.storage.internal;
import com.example.orders.model.Order;
import com.example.orders.model.Product;
import com.example.orders.storage.OrderRepository;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
public final class InMemoryOrderRepository implements OrderRepository {
    private final List<Order<? extends Product>> orders = new CopyOnWriteArrayList<>();
    @Override public void save(Order<? extends Product> order) {
        if (order == null) throw new IllegalArgumentException("Cannot save null order");
        orders.add(order);
    }
    @Override public List<Order<? extends Product>> findAll() { return List.copyOf(orders); }
}
