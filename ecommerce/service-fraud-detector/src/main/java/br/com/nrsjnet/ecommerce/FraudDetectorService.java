package br.com.nrsjnet.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {

    public static void main(String[] args) {
        try (var service = new KafkaService<Order>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER", FraudDetectorService::parse, Order.class, Map.of())) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, Order> record) {
        System.out.println("------------------------------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
