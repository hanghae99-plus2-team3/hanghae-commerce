package hanghae99.plus2.team3.hanghaeorder.infrastructure.payment.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import hanghae99.plus2.team3.hanghaeorder.common.task.PaymentTask
import hanghae99.plus2.team3.hanghaeorder.domain.payment.infrastructure.PaymentRequestTaskProducer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

/**
 * TaskProducer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/24/23
 */

@Component
class TaskProducer(
    @Value("\${kafka.cluster.bootstrapservers}") bootstrapServers: String,
    private val objectMapper: ObjectMapper,
) : PaymentRequestTaskProducer {

    private final val producer: KafkaProducer<String, String>

    init {
        val props = Properties()
        props["bootstrap.servers"] = bootstrapServers
        props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        props["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        this.producer = KafkaProducer(props)
    }

    override fun produce(task: PaymentTask) {
        val record: ProducerRecord<String, String> =
            ProducerRecord(task.taskName, task.taskId, objectMapper.writeValueAsString(task))
        producer.send(record)
    }

}
