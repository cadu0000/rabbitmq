import config.EnvLoader;
import config.RabbitMqConfig;
import events.TransactionConsumer;

public class ConsumerMain {
    public static void main(String[] args)
            throws Exception {

        RabbitMqConfig config =
                EnvLoader.loadRabbitConfig();

        TransactionConsumer consumer =
                new TransactionConsumer(config);

        consumer.consume();
    }
}