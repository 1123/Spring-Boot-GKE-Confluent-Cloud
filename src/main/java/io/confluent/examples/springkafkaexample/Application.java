package io.confluent.examples.springkafkaexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * This code is taken from Gary Russel's blog post here: https://www.confluen.io/blog/spring-for-apache-kafka-deep-dive-part-1-error-handling-message-conversion-transaction-support
 */

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * This configuration showcases Spring Kafka's DeadLetterPublishingRecoverer.
     */

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory,
            KafkaTemplate<Object, Object> template) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(template), 3)); // dead-letter after 3 tries
        return factory;
    }

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @KafkaListener(id = "fooGroup", topics = "topic1")
    public void listen(Foo2 foo) {
        log.info("Received: " + foo);
        if (foo.getFoo().startsWith("fail")) {
            throw new RuntimeException("failed");
        }
    }

    /**
     * This method listens on the dead letter topic and logs received messages for demonstration purposes.
     * It uses a separate consumer group from the listener on topic1.
     *
     * @param in: The received message value.
     */

    @KafkaListener(id = "dltGroup", topics = "topic1.DLT")
    public void dltListen(String in) {
        log.info("Received from DLT: " + in);
    }

}

