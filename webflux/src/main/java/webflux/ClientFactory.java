package webflux;

import common.file.FileClient;
import common.mail.MailClient;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Factory
public class ClientFactory {

    @Bean
    FileClient fileClient() {
        var webClient = FileClientExt.inner_client.get();
        var proxyFactory = HttpServiceProxyFactory.builder()
            .clientAdapter(WebClientAdapter.forClient(webClient))
            .build();
        return proxyFactory.createClient(FileClientExt.class);
    }

    @Bean
    MailClient mailClient() {
        var webClient = MailClientExt.inner_client.get();
        var proxyFactory = HttpServiceProxyFactory.builder()
            .clientAdapter(WebClientAdapter.forClient(webClient))
            .build();
        return proxyFactory.createClient(MailClientExt.class);
    }
}
