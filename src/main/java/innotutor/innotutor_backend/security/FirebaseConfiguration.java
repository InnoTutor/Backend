package innotutor.innotutor_backend.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {

    @Value("${firebase.path}")
    private String path;

    @Value("${firebase.database-url}")
    private String databaseUrl;

    @Value("${firebase.storage-url}")
    private String storageUrl;

    @Value("${GOOGLE_CREDENTIALS}")
    private String gservicesConfig;

    @Bean
    public FirebaseApp provideFirebaseOptions() throws IOException {
        JSONObject jsonObject = new JSONObject(gservicesConfig);
        InputStream is = new ByteArrayInputStream(jsonObject.toString().getBytes());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream((is)))
                .setDatabaseUrl(databaseUrl)
                .setStorageBucket(storageUrl)
                .build();

        return FirebaseApp.initializeApp(options);
    }
}