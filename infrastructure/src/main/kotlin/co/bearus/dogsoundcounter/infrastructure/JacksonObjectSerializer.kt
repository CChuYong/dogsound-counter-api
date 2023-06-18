package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.usecases.ObjectSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class JacksonObjectSerializer(
    private val objectMapper: ObjectMapper,
) : ObjectSerializer {
    override fun serialize(data: Any): String {
        return objectMapper.writeValueAsString(data)
    }
}
