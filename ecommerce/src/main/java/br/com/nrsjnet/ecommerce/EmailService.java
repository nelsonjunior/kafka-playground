package br.com.nrsjnet.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class EmailService {

    public static void main(String[] args) {

        try (var service = new KafkaService(EmailService.class.getName(), "ECOMMERCE_SEND_EMAIL", EmailService::parse)) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record) {
        System.out.println("------------------------------------------");
        System.out.println("Processing send email");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
    }

}
