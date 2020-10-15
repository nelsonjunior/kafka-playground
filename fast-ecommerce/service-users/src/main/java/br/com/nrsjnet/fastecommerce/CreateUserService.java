package br.com.nrsjnet.fastecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class CreateUserService {
    public static void main(String[] args) {
        try (var service = new KafkaService<Order>(CreateUserService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER", CreateUserService::parse, Order.class, Map.of())) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, Order> record) {
        System.out.println("------------------------------------------");
        System.out.println("Create User. Check for new user!!!");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
