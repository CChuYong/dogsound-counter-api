package co.bearus.dogsoundcounter.infrastructure.messages

import co.bearus.dogsoundcounter.usecases.TopicManager
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KafkaTopicManager(
    @Value("\${app.kafka.servers}") private val bootstrapUrls: String,
) : TopicManager {
    override fun createTopic(topicName: String) {
        val client = AdminClient.create(getProps())
        val topic = NewTopic(topicName, 1, 1)
        client.createTopics(listOf(topic)).all().get()
        client.close()
    }

    private fun getProps(): Map<String, Any> = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to listOf(bootstrapUrls),
        ConsumerConfig.GROUP_ID_CONFIG to "",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to IntegerDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
    )
}
