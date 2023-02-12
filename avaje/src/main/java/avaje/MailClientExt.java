package avaje;

import common.mail.MailClient;
import common.mail.MailMsg;
import common.mail.MailMsgDetails;
import common.util.LazyValue;
import io.avaje.http.api.Client;
import io.avaje.http.api.Get;
import io.avaje.http.api.Header;
import io.avaje.http.client.HttpClient;
import io.avaje.http.client.JacksonBodyAdapter;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Client
public interface MailClientExt extends MailClient {
    LazyValue<HttpClient> inner_client = LazyValue.create(() -> {
        var proxySelector = ProxySelector.of((InetSocketAddress) MailClient.proxy.address());
        return HttpClient.builder()
            .baseUrl(MailClient.addr.toString())
//            .proxy(proxySelector)
            .bodyAdapter(new JacksonBodyAdapter())
            .build();
    });

    default String get_auth_token(String mail) {
        var res = get_auth_token_0(mail);
        var c = res.headers().firstValue("set-cookie");
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

    @Get("/zh/mail/{mail}")
    HttpResponse<Void> get_auth_token_0(String mail);

    @Get("/api/api/v1/mailbox/{mail}")
    List<MailMsg> get_msg_list_0(String mail,
                                 @Header("authorization") String auth_token,
                                 @Header("referer") String referer);


    @Get("/api/api/v1/mailbox/{mail}/{id}")
    MailMsgDetails get_msg_details_0(String mail,
                                     String id,
                                     @Header("authorization") String auth_token,
                                     @Header("referer") String referer);
}
