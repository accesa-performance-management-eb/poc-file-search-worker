package com.biroas.poc.file.search.worker.configuration;

import com.biroas.poc.file.search.worker.jms.FileDownloadRequestReceiver;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


@Configuration
public class ActiveMQReceiverConfiguration {

    @Value("${activemq.broker.url}")
    private String brokerUrl;
    @Value("${activemq.listener.concurrency}")
    private String listenerConcurrency;
    @Value("${activemq.listener.session.transacted}")
    private Boolean sessionTransacted;
    @Value("${activemq.queue.download.name}")
    private String downloadRequestQueueName;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        activeMQConnectionFactory.setTrustAllPackages(true);
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setConcurrency(listenerConcurrency);
        factory.setSessionTransacted(sessionTransacted);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public FileDownloadRequestReceiver getFileDownloadRequestReceiver(){
        return new FileDownloadRequestReceiver();
    }

    @Bean(name = "DynamicDestination")
    public DynamicDestination getDynamicDestination() {
        return new DynamicDestination();
    }

    public class DynamicDestination {

        public String getDestinationName() {
            return downloadRequestQueueName+"_"+System.getProperty("user.name") + "-" + System.getProperty("os.name");
        }
    }
}
