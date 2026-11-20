package com.example.orders.app;
import com.example.orders.model.*;
import com.example.orders.processing.*;
import com.example.orders.storage.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        FakeProductFactory factory = new FakeProductFactory();
        Order<Electronics> first = new Order<>(1, factory.electronics());
        Order<Clothing> second = new Order<>(2, factory.clothing());
        Order<Electronics> third = new Order<>(3, factory.electronics());
        List<Order<? extends Product>> orders = List.of(first, second, third);
        OrderRepository repository = RepositoryFactory.create();
        OrderProcessor<Product> processor = new OrderProcessor<>();
        Consumer<Order<? extends Product>> task = order -> { // lambda expression
            try {
                processor.process(new Order<Product>(order.getId(), order.getProduct()));
                repository.save(order);
            } catch (OrderProcessingException | IllegalArgumentException ex) {
                System.err.println("Processing failed: " + ex.getMessage());
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            List<? extends Future<?>> futures = orders.stream()
                    .filter(order -> order.getProduct().getPrice() > 0) // lambda filter
                    .map(order -> pool.submit(() -> task.accept(order))).toList();
            for (Future<?> result : futures) {
                try { result.get(); }
                catch (ExecutionException ex) { System.err.println("Worker error: " + ex.getCause()); }
            }
        } finally { pool.shutdown(); }
        System.out.println("Saved orders: " + repository.findAll().size());
        repository.findAll().stream().map(Order::getProduct)
                .map(Product::getName).forEach(System.out::println); // method reference
    }
}
