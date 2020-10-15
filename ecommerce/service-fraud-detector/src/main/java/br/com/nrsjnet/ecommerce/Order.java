package br.com.nrsjnet.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String userID, orderID;

    public Order(String userID, String orderID) {
        this.userID = userID;
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public String getOrderID() {
        return orderID;
    }


    @Override
    public String toString() {
        return "Order{" +
                "userID='" + userID + '\'' +
                ", orderID='" + orderID + '\'' +
                '}';
    }
}
