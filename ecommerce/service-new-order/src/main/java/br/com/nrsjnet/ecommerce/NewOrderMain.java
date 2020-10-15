package br.com.nrsjnet.ecommerce;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var dispatcherOrder = new KafkaDispatcher<Order>()) {
            try (var dispatcherEmail = new KafkaDispatcher<Email>()) {
                for (int i = 0; i < 10; i++) {
                    var userID = UUID.randomUUID().toString();
                    var orderID = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1).setScale(2, RoundingMode.HALF_DOWN);
                    var value = new Order(userID, orderID, amount);
                    dispatcherOrder.send("ECOMMERCE_NEW_ORDER", userID, value);

                    var email = new Email(userID, "Thank you for your order! We are processing your order!");
                    dispatcherEmail.send("ECOMMERCE_SEND_EMAIL", userID, email);
                }
            }
        }

    }

}
