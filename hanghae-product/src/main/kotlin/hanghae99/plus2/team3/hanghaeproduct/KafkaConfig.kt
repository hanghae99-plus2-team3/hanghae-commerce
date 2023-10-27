package hanghae99.plus2.team3.hanghaeproduct

import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEvent
import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEventDeserializer
import hanghae99.plus2.team3.hanghaecommon.event.ChangeStockEventSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*

/**
 * KafkaConsumerConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */

@Configuration
class KafkaConfig {

    @Value("\${kafka.cluster.bootstrapservers}")
    lateinit var bootstrapServers: String

    @Value("\${kafka.group.name}")
    lateinit var groupName: String

    @Bean
    fun consumerFactory(): ConsumerFactory<String, ChangeStockEvent> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ConsumerConfig.GROUP_ID_CONFIG] = groupName
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ChangeStockEventDeserializer::class.java
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ChangeStockEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ChangeStockEvent>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, ChangeStockEvent> {
        val config: MutableMap<String, Any> = java.util.HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = ChangeStockEventSerializer::class.java
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, ChangeStockEvent> {
        return KafkaTemplate(producerFactory())
    }
}
