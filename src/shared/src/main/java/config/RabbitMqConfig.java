package config;

public record RabbitMqConfig(
        String host,
        int port,
        String username,
        String password,
        String queueName
) {
}
