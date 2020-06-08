package net.msonic.customers.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRabbit
public class MqConfig {

    private static final String topicExchangeName = "wallet";
    private static final String queueName = "wallet.new";
    private static final String queueName_webhook = "wallet.hook";

    @Bean
    Queue walletNew() {
        return new Queue(queueName, false);
    }

    @Bean
    Queue walletNotifications() {

        return new Queue(queueName_webhook, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }


    @Bean
    Binding walletNewBinding(Queue walletNew, TopicExchange exchange) {
        return BindingBuilder.bind(walletNew).to(exchange).with("mybank.wallet.new.v1");
    }

    @Bean
    Binding walletBinding(Queue walletNotifications, TopicExchange exchange) {
        return BindingBuilder.bind(walletNotifications).to(exchange).with("mybank.wallet.created.v1.*");
    }


    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
