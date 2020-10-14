package br.com.nrsjnet.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException{

        try(var dispatcher = new KafkaDispatcher()){
            for (int i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();
                String value = key + "767654,432423,432432";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "Thank you for your order! We are processing your order!";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }

    }

}
