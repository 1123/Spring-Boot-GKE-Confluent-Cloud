package io.confluent.examples.springkafkaexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This code is taken from Gary Russel's blog post here: https://www.confluen.io/blog/spring-for-apache-kafka-deep-dive-part-1-error-handling-message-conversion-transaction-support
 */

@RestController
public class Controller {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    /**
     * Use this method to try out the application.
     * e.g. curl -X POST localhost:8080/send/this_is_the_message
     * @param what the message to be sent to Kafka
     */

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        // Conversion to bytes is done by the template.
        this.template.send("topic1", new Foo1(what));
    }

}