package br.com.nrsjnet.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

class KafkaService<T> implements Closeable {
    private final KafkaConsumer<String, T> consumer;
    private final Class<T> type;
    private final Map<String, String> customProperties;
    private ConsumerFunction parse;

    private KafkaService(String groupID, ConsumerFunction parse, Class<T> type, Map<String,String> customProperties) {
        this.consumer = new KafkaConsumer<>(properties(groupID, type, customProperties));
        this.parse = parse;
        this.type = type;
        this.customProperties = customProperties;
    }

    KafkaService(String groupID, String topic, ConsumerFunction parse, Class<T> type, Map<String,String> customProperties) {
        this(groupID, parse, type, customProperties);
        this.consumer.subscribe(Collections.singletonList(topic));
    }


    KafkaService(String groupID, Pattern compile, ConsumerFunction parse, Class<T> type, Map<String,String> customProperties) {
        this(groupID, parse, type, customProperties);
        this.consumer.subscribe(compile);
    }

    void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    this.parse.consume(record);
                }
            }
        }
    }

    private Properties properties(String groupID, Class<T> type, Map<String, String> customProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        properties.putAll(customProperties);
        return properties;
    }

    @Override
    public void close() {
        this.consumer.close();
    }
}
