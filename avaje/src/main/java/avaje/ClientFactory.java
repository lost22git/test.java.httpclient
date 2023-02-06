package avaje;

import common.file.FileClient;
import common.mail.MailClient;
import io.avaje.http.client.HttpClient;
import io.avaje.http.client.JacksonBodyAdapter;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.net.InetSocketAddress;
import java.net.ProxySelector;

@Factory
public class ClientFactory {

    @Bean
    FileClient fileClient() {
        var proxySelector = ProxySelector.of((InetSocketAddress) FileClient.proxy.address());

        var httpClient = HttpClient.builder()
            .proxy(proxySelector)
            .baseUrl(FileClient.addr.toString())
            .bodyAdapter(new JacksonBodyAdapter())
            .build();

        return httpClient.create(FileClientExt.class);
    }

    @Bean
    MailClient mailClient() {
        var proxySelector = ProxySelector.of((InetSocketAddress) MailClient.proxy.address());
        var httpclient = HttpClient.builder()
            .baseUrl(MailClient.addr.toString())
//            .proxy(proxySelector)
            .bodyAdapter(new JacksonBodyAdapter())
            .build();

        return httpclient.create(MailClientExt.class);
    }
}
