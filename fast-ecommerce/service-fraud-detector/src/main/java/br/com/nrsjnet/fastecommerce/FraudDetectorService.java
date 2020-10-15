package br.com.nrsjnet.fastecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {

    private static KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    public static void main(String[] args) {
        try (var service = new KafkaService<Order>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER", FraudDetectorService::parse, Order.class, Map.of())) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
        System.out.println("------------------------------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        checkExternalFraud();
        Order order = record.value();
        if (isFraud(order)) {
            System.out.println("Order is Fraud!!!!");
            orderDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getOrderID(), order);
        } else {
            System.out.println("Order is approved: " + order);
            orderDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getOrderID(), order);
        }
    }

    private static boolean isFraud(Order order) {
        return order.getAmount().compareTo(new BigDecimal("4500")) >= 0;
    }

    private static void checkExternalFraud() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
