package br.com.nrsjnet.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var service = new KafkaService(LogService.class.getSimpleName(), Pattern.compile("ECOMMERCE.*"),
                LogService::parse, String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record) {
        System.out.println("------------------------------------------");
        System.out.println("LOG");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
    }
}
