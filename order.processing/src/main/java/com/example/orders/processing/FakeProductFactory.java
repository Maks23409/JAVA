package com.example.orders.processing;
import com.example.orders.model.Clothing;
import com.example.orders.model.Electronics;
import com.github.javafaker.Faker;
import java.util.Locale;
public final class FakeProductFactory {
    private final Faker faker = new Faker(Locale.ENGLISH);
    public Electronics electronics() {
        return Electronics.builder().name(faker.commerce().productName())
                .price(1000 + faker.number().numberBetween(1, 8000))
                .warrantyMonths(12).build();
    }
    public Clothing clothing() {
        return Clothing.builder().name(faker.commerce().productName())
                .price(200 + faker.number().numberBetween(1, 1500))
                .size("M").build();
    }
}
