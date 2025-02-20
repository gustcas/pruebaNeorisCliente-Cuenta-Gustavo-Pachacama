package com.servicio.cuenta.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.Map;
import java.util.Set;

@TestConfiguration
public class TestConfig {

    @Bean
    public EmbeddedKafkaBroker embeddedKafkaBroker() {
        return new EmbeddedKafkaBroker() {
            @Override
            public EmbeddedKafkaBroker kafkaPorts(int... ports) {
                return null;
            }

            @Override
            public Set<String> getTopics() {
                return Set.of();
            }

            @Override
            public EmbeddedKafkaBroker brokerProperties(Map<String, String> properties) {
                return null;
            }

            @Override
            public EmbeddedKafkaBroker brokerListProperty(String brokerListProperty) {
                return null;
            }

            @Override
            public EmbeddedKafkaBroker adminTimeout(int adminTimeout) {
                return null;
            }

            @Override
            public String getBrokersAsString() {
                return "";
            }

            @Override
            public void addTopics(String... topicsToAdd) {

            }

            @Override
            public void addTopics(NewTopic... topicsToAdd) {

            }

            @Override
            public Map<String, Exception> addTopicsWithResults(NewTopic... topicsToAdd) {
                return Map.of();
            }

            @Override
            public Map<String, Exception> addTopicsWithResults(String... topicsToAdd) {
                return Map.of();
            }

            @Override
            public void consumeFromEmbeddedTopics(Consumer<?, ?> consumer, boolean seekToEnd, String... topicsToConsume) {

            }

            @Override
            public void consumeFromEmbeddedTopics(Consumer<?, ?> consumer, String... topicsToConsume) {

            }

            @Override
            public void consumeFromAnEmbeddedTopic(Consumer<?, ?> consumer, boolean seekToEnd, String topic) {

            }

            @Override
            public void consumeFromAnEmbeddedTopic(Consumer<?, ?> consumer, String topic) {

            }

            @Override
            public void consumeFromAllEmbeddedTopics(Consumer<?, ?> consumer, boolean seekToEnd) {

            }

            @Override
            public void consumeFromAllEmbeddedTopics(Consumer<?, ?> consumer) {

            }

            @Override
            public int getPartitionsPerTopic() {
                return 0;
            }
        }
                .kafkaPorts(9092);
    }
}
