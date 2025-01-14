package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class Product {
    private String name;
    private BigDecimal price;
    private Category category;

    public Product(String name, BigDecimal price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal calculateTax(int quantity) {
        final BigDecimal unitaryTax = unitaryTax();
        return unitaryTax.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal unitaryTax() {
        return this.getPrice().divide(valueOf(100)).multiply(this.getCategory().getTaxPercentage()).setScale(2, HALF_UP);
    }

    public BigDecimal calculateTotalWithTax(int quantity) {
        return unitaryTaxedAmount().multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
    }

    public BigDecimal unitaryTaxedAmount() {
        return this.getPrice().add(unitaryTax()).setScale(2, HALF_UP);
    }
}
