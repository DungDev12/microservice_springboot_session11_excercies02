package com.exam.productservice.config;

import com.exam.productservice.messaging.PharmacyAlertPublisher;
import com.exam.productservice.messaging.PharmacyAlertSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public ChannelTopic pharmacyAlertsTopic() {
        return new ChannelTopic(
                PharmacyAlertPublisher.CHANNEL
        );
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            PharmacyAlertSubscriber subscriber,
            ChannelTopic pharmacyAlertsTopic
    ) {
        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(
                subscriber,
                pharmacyAlertsTopic
        );

        return container;
    }
}
