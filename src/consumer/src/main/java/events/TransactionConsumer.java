package events;

import com.google.gson.*;
import com.rabbitmq.client.*;
import config.RabbitMqConfig;
import dtos.TransactionEventDTO;
import services.NotificationService;

import java.lang.reflect.Type;
import java.time.Instant;

public class TransactionConsumer {

    private final RabbitMqConfig config;
    private final RabbitMQConnection rabbitConnection;
    private final NotificationService notificationService;
    private final Gson gson;

    public TransactionConsumer(RabbitMqConfig config) {
        this.config = config;
        this.rabbitConnection = new RabbitMQConnection(config);
        this.notificationService = new NotificationService();

        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new JsonDeserializer<Instant>() {
                    @Override
                    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return Instant.parse(json.getAsString());
                    }
                })
                .create();
    }

    public void consume() throws Exception {
        Connection connection = rabbitConnection.connect();
        Channel channel = connection.createChannel();

        channel.queueDeclare(config.queueName(), true, false, false, null);

        System.out.println("Waiting for messages...");

        DeliverCallback callback = (consumerTag, delivery) -> {
            try {
                String json = new String(delivery.getBody(), "UTF-8");
                System.out.println(json);
                TransactionEventDTO event = gson.fromJson(json, TransactionEventDTO.class);
                notificationService.notify(event);
            } catch (Exception e) {
                System.err.println("[!] ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        };

        channel.basicConsume(config.queueName(), true, callback, consumerTag -> {});
    }
}