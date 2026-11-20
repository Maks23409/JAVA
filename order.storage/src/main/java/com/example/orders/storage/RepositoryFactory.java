package com.example.orders.storage;
import com.example.orders.storage.internal.InMemoryOrderRepository;
public final class RepositoryFactory {
    private RepositoryFactory() {}
    public static OrderRepository create() { return new InMemoryOrderRepository(); }
}
