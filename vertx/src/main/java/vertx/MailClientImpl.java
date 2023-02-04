package vertx;

import com.fasterxml.jackson.core.type.TypeReference;
import common.mail.MailClient;
import common.mail.MailMsg;
import common.mail.MailMsgDetails;
import io.vertx.core.Vertx;
import io.vertx.core.json.jackson.JacksonCodec;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.ProxyType;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.uritemplate.UriTemplate;

import java.util.Arrays;
import java.util.List;

public class MailClientImpl implements MailClient {

    private final WebClient webClient;

    public MailClientImpl(Vertx vertx) {
        var proxyOptions = new ProxyOptions()
            .setHost(proxy_addr.getHostName())
            .setPort(proxy_addr.getPort())
            .setType(ProxyType.SOCKS5);

        var options = new WebClientOptions()
            .setDefaultHost(addr.getHost())
//            .setProxyOptions(proxyOptions)
            ;
        this.webClient = WebClient.create(vertx, options);
    }


    @Override
    public String get_auth_token(String mail) {
        var uri = UriTemplate.of("/zh/mail/{mail}");
        var res = webClient.get(uri)
            .setTemplateParam("mail", mail)
            .as(BodyCodec.none())
            .expect(ResponsePredicate.status(200, 300))
            .send()
            .toCompletionStage()
            .toCompletableFuture()
            .join();

        return res.cookies().stream()
            .filter(v -> v.contains("auth_token="))
            .flatMap(v -> Arrays.stream(v.split(";")))
            .filter(v -> v.startsWith("auth_token="))
            .map(v -> v.substring("auth_token=".length()).trim())
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<MailMsg> get_msg_list(String mail,
                                      String auth_token) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;
        var uri = UriTemplate.of("/api/api/v1/mailbox/{mail}");
        var res = webClient.get(uri)
            .setTemplateParam("mail", mail)
            .putHeader("authorization", token)
            .putHeader("referer", referer)
            .expect(ResponsePredicate.status(200, 300))
            .send()
            .toCompletionStage()
            .toCompletableFuture()
            .join();
        return JacksonCodec.decodeValue(res.body(), new TypeReference<List<MailMsg>>() {
        });
    }

    @Override
    public MailMsgDetails get_msg_details(String mail,
                                          String auth_token,
                                          String id) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;
        var uri = UriTemplate.of("/api/api/v1/mailbox/{mail}/{id}");
        return webClient.get(uri)
            .setTemplateParam("mail", mail)
            .setTemplateParam("id", id)
            .putHeader("authorization", token)
            .putHeader("referer", referer)
            .as(BodyCodec.json(MailMsgDetails.class))
            .expect(ResponsePredicate.status(200, 300))
            .send()
            .toCompletionStage()
            .toCompletableFuture()
            .join()
            .body();
    }
}
