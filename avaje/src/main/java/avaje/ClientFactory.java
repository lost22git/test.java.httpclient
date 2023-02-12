package avaje;

import common.file.FileClient;
import common.mail.MailClient;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

@Factory
public class ClientFactory {

    @Bean
    FileClient fileClient() {
        var httpClient = FileClientExt.inner_client.get();
        return httpClient.create(FileClientExt.class);
    }

    @Bean
    MailClient mailClient() {
        var httpClient = MailClientExt.inner_client.get();
        return httpClient.create(MailClientExt.class);
    }
}
