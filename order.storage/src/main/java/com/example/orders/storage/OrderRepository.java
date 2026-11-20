package com.example.orders.storage;
import com.example.orders.model.Order;
import com.example.orders.model.Product;
import java.util.List;
public interface OrderRepository {
    void save(Order<? extends Product> order);
    List<Order<? extends Product>> findAll();
}
