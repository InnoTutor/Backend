package innotutor.innotutor_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor
@SpringBootApplication
public class InnotutorBackendApplication {
    @Value("${GOOGLE_CREDENTIALS}")
    private String gservicesConfig;

    public static void main(final String[] args) {
        SpringApplication.run(InnotutorBackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FirebaseApp provideFirebaseOptions() throws IOException {
        final JSONObject jsonObject = new JSONObject(gservicesConfig);
        final InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
        final FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(final Jackson2ObjectMapperBuilder builder) {
        return builder.build();
    }
}
