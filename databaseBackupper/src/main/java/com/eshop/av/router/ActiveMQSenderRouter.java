package com.eshop.av.router;

import lombok.AllArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActiveMQSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        errorHandler(deadLetterChannel("activemq:dead-queue")
                .maximumRedeliveries(3)
                .redeliveryDelay(1000)
                .retryAttemptedLogLevel(LoggingLevel.WARN)
                .logStackTrace(false));

        from("timer:active-mq-timer?period=10000")      //
                .bean("com.eshop.av.bean.DatabaseBackupEncrypted", "DatabaseBackup")
                .to("activemq:user-queue");
    }
}
