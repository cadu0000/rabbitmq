package events;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import config.RabbitMqConfig;
import dtos.TransactionEventDTO;

public class WithdrawalPublisher {
    private final RabbitMQConnection rabbitConnection;
    private final RabbitMqConfig config;

    public WithdrawalPublisher(
            RabbitMqConfig config
    ) {
        this.config = config;
        this.rabbitConnection =
                new RabbitMQConnection(config);
    }

    public void publish(
            TransactionEventDTO event
    ) throws Exception {

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

        Gson gson = new Gson();

        String json = gson.toJson(event);

        channel.basicPublish(
                "",
                config.queueName(),
                null,
                json.getBytes()
        );

        System.out.println(
                "Withdrawal event published!"
        );

        channel.close();
        connection.close();
    }
}
