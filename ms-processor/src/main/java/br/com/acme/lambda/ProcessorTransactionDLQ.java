package br.com.acme.lambda;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ProcessorTransactionDLQ {

    private final SqsTemplate sqsTemplate;

    public ProcessorTransactionDLQ(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Bean
    public java.util.function.Consumer<Message<String>> dlqHandler() {
        return message -> {
            String body = message.getPayload();
            System.out.println("Reprocessing the message  DLQ: " + body);

            sqsTemplate.send(to -> to.queue("transactions-accounts").payload(body));
        };
    }
}
