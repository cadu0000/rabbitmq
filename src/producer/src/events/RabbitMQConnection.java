package events;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {
    private final RabbitMqConfig config;

    public RabbitMQConnection(RabbitMqConfig config) {
        this.config = config;
    }

    public Connection connect() throws Exception {
        ConnectionFactory factory =
                new ConnectionFactory();

        factory.setHost(config.host());
        factory.setPort(config.port());
        factory.setUsername(config.username());
        factory.setPassword(config.password());

        return factory.newConnection();
    }
}