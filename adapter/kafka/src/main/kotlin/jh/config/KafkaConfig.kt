package jh.config

import com.fasterxml.jackson.core.JsonProcessingException
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.CommonContainerStoppingErrorHandler
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.ConsumerRecordRecoverer
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.listener.MessageListenerContainer
import org.springframework.util.backoff.BackOff
import org.springframework.util.backoff.ExponentialBackOff
import java.util.concurrent.atomic.AtomicReference

@Configuration
@EnableKafka
class KafkaConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.kafka")
    fun kafkaProperties(): KafkaProperties {
        return KafkaProperties()
    }

    @Bean
    @Primary
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, Any> {
        val props = HashMap<String, Any>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties().bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "-1"
        props[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = "true"

        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    @Primary
    fun kafkaTemplate(kafkaProperties: KafkaProperties): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory(kafkaProperties))
    }

    @Bean
    @Primary
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<String, Any> {
        val props = HashMap<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = "auto-inspection-consumer-group"
        props[ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG] = "false"
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"

        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    @Primary
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, Any>,
        errorHandler: CommonErrorHandler?,
    ): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = consumerFactory
        factory.setCommonErrorHandler(errorHandler!!)
        factory.containerProperties.ackMode = ContainerProperties.AckMode.RECORD

        return factory
    }

    @Bean
    @Primary
    fun errorHandler(): CommonErrorHandler {
        val cseh = CommonContainerStoppingErrorHandler()
        val consumer2 = AtomicReference<Consumer<*, *>>()
        val container2 = AtomicReference<MessageListenerContainer>()

        val errorHandler: DefaultErrorHandler = object : DefaultErrorHandler(
            ConsumerRecordRecoverer { rec: ConsumerRecord<*, *>, ex: Exception? ->
                cseh.handleRemaining(ex!!, listOf(rec), consumer2.get(), container2.get())
            }, generateBackOff()
        ) {
            override fun handleRemaining(
                thrownException: java.lang.Exception,
                records: MutableList<ConsumerRecord<*, *>>,
                consumer: Consumer<*, *>,
                container: MessageListenerContainer,
            ) {
                consumer2.set(consumer)
                container2.set(container)
                super.handleRemaining(thrownException, records, consumer, container)
            }
        }
        errorHandler.addNotRetryableExceptions(JsonProcessingException::class.java)

        return errorHandler
    }

    private fun generateBackOff(): BackOff {
        val backOff = ExponentialBackOff(1000, 2.0)
        backOff.maxAttempts = 3

        return backOff
    }
}
