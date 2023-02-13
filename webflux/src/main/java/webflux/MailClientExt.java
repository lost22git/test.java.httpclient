package webflux;

import common.mail.MailClient;
import common.mail.MailMsg;
import common.mail.MailMsgDetails;
import common.util.LazyValue;
import io.netty.handler.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.annotation.GetExchange;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface MailClientExt extends MailClient {
    LazyValue<WebClient> inner_client = LazyValue.create(() -> {
        var httpClient = HttpClient.create()
            .followRedirect(true)
            .proxy(HttpUtil.configure_proxy(proxy))
            .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        return WebClient.builder()
            .baseUrl(api_addr.toString())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    });

    default String get_auth_token(String mail) {
        var res = get_auth_token_0(mail);
        var c = Optional.ofNullable(res.getHeaders().getFirst("set-cookie"));
        if (c.isEmpty()) return null;

        var auth_token_entry = Arrays.stream(c.get().split(";")).filter(v -> v.startsWith("auth_token="))
            .findFirst();
        return auth_token_entry.map(s -> s.split("=")[1]).orElse(null);
    }

    default List<MailMsg> get_msg_list(String mail,
                                       String auth_token) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;
        return get_msg_list_0(mail, token, referer);
    }

    default MailMsgDetails get_msg_details(String mail,
                                           String auth_token,
                                           String id) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;
        return get_msg_details_0(mail, id, token, referer);
    }

    @GetExchange("/zh/mail/{mail}")
    ResponseEntity<Void> get_auth_token_0(@PathVariable("mail") String mail);

    @GetExchange("/api/api/v1/mailbox/{mail}")
    List<MailMsg> get_msg_list_0(@PathVariable("mail") String mail,
                                 @RequestHeader("authorization") String auth_token,
                                 @RequestHeader("referer") String referer);


    @GetExchange("/api/api/v1/mailbox/{mail}/{id}")
    MailMsgDetails get_msg_details_0(@PathVariable("mail") String mail,
                                     @PathVariable("id") String id,
                                     @RequestHeader("authorization") String auth_token,
                                     @RequestHeader("referer") String referer);

}
