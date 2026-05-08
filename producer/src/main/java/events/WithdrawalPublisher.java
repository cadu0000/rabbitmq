package events;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.Instant;
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

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new JsonSerializer<Instant>() {
                    @Override
                    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.toString());
                    }
                })
                .registerTypeAdapter(Instant.class, new JsonDeserializer<Instant>() {
                    @Override
                    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return Instant.parse(json.getAsString());
                    }
                })
                .create();

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
