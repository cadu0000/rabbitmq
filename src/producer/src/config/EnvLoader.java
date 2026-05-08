package config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    private static final Dotenv dotenv = Dotenv.load();

    public static RabbitMqConfig loadRabbitConfig() {
        return new RabbitMqConfig(
                dotenv.get("RABBITMQ_HOST"),
                Integer.parseInt(
                        dotenv.get("RABBITMQ_PORT")
                ),
                dotenv.get("RABBITMQ_USERNAME"),
                dotenv.get("RABBITMQ_PASSWORD"),
                dotenv.get("RABBITMQ_QUEUE")
        );
    }
}