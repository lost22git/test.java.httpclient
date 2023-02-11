package okhttp;

import common.file.FileClient;
import common.mail.MailClient;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Factory
public class ClientFactory {

    @Bean
    FileClient fileClient() {
        var httpClient = new OkHttpClient.Builder()
            .proxy(FileClient.proxy)
            .addInterceptor(HttpLog.INSTANCE.createInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
        var retrofit = new Retrofit.Builder()
            .baseUrl(FileClient.api_addr.toString())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build();

        return retrofit.create(FileClientExt.class);
    }

    @Bean
    MailClient mailClient() {
        var httpClient = new OkHttpClient.Builder()
//            .proxy(FileClient.proxy)
            .addInterceptor(HttpLog.INSTANCE.createInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
        var retrofit = new Retrofit.Builder()
            .baseUrl(MailClient.addr.toString())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build();

        return retrofit.create(MailClientExt.class);
    }
}
