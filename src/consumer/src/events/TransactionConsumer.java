package events;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import config.RabbitMqConfig;
import dtos.TransactionEventDTO;
import services.NotificationService;

public class TransactionConsumer {

    private final RabbitMqConfig config;

    private final RabbitMQConnection rabbitConnection;

    private final NotificationService notificationService;

    private final Gson gson;

    public TransactionConsumer(
            RabbitMqConfig config
    ) {

        this.config = config;

        this.rabbitConnection =
                new RabbitMQConnection(config);

        this.notificationService =
                new NotificationService();

        this.gson = new Gson();
    }

    public void consume()
            throws Exception {

        Connection connection =
                rabbitConnection.connect();

        Channel channel =
                connection.createChannel();

        channel.queueDeclare(
                config.queueName(),
                true,
                false,
                false,
                null
        );

        System.out.println(
                "Waiting for messages..."
        );

        DeliverCallback callback =
                (consumerTag, delivery) -> {

                    String json =
                            new String(
                                    delivery.getBody()
                            );

                    TransactionEventDTO event =
                            gson.fromJson(
                                    json,
                                    TransactionEventDTO.class
                            );

                    notificationService.notify(event);
                };

        channel.basicConsume(
                config.queueName(),
                true,
                callback,
                consumerTag -> {}
        );
    }
}