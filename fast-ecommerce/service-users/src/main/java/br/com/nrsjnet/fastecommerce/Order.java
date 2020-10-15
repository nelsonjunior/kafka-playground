package br.com.nrsjnet.fastecommerce;

import java.math.BigDecimal;

public class Order {

    private final String orderID;
    private final BigDecimal amount;

    public Order(String orderID, BigDecimal amount) {
        this.orderID = orderID;
        this.amount = amount;
    }


    public String getOrderID() {
        return orderID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", amount=" + amount +
                '}';
    }
}
