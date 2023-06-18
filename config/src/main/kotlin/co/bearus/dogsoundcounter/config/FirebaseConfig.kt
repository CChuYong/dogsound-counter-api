package co.bearus.dogsoundcounter.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.util.*


@Configuration
class FirebaseConfig(
    @Value("\${app.firebase-token}") val secretValue: String,
) {
    @PostConstruct
    fun initialize() {
        val credentials = ByteArrayInputStream(Base64.getDecoder().decode(secretValue))
        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(credentials))
            .build()

        FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()
}
