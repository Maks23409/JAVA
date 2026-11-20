module order.storage {
    requires order.processing;
    exports com.example.orders.storage;
    // com.example.orders.storage.internal is intentionally NOT exported.
}
