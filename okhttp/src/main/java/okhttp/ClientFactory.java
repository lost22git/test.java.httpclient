package okhttp;

import common.file.FileClient;
import common.mail.MailClient;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Factory
public class ClientFactory {

    @Bean
    FileClient fileClient() {
        var httpClient = FileClientExt.inner_client.get();
        var retrofit = new Retrofit.Builder()
            .baseUrl(FileClient.api_addr.toString())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build();

        return retrofit.create(FileClientExt.class);
    }

    @Bean
    MailClient mailClient() {
        var httpClient = MailClientExt.inner_client.get();
        var retrofit = new Retrofit.Builder()
            .baseUrl(MailClient.addr.toString())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build();

        return retrofit.create(MailClientExt.class);
    }
}
