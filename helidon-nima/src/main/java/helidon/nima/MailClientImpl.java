package helidon.nima;

import common.mail.MailClient;
import common.mail.MailMsg;
import common.mail.MailMsgDetails;
import io.helidon.common.GenericType;
import io.helidon.common.http.Http;
import io.helidon.nima.webclient.http1.Http1Client;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class MailClientImpl implements MailClient {

    private final Http1Client httpClient;

    public MailClientImpl() {
        httpClient = Http1Client.builder()
            .baseUri(api_addr)
            .build();
    }

    @Override
    public String get_auth_token(String mail) {
        var res = httpClient.get("/zh/mail/{mail}")
            .pathParam("mail", mail)
            .request();

        var cookieValues = res.headers().values(Http.Header.create("set-cookie"));
        return parse_auth_token_from_cookie(cookieValues.stream());
    }

    @Override
    public List<MailMsg> get_msg_list(String mail,
                                      String auth_token) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;
        var res = httpClient.get("/api/api/v1/mailbox/{mail}")
            .pathParam("mail", mail)
            .header(Http.Header.create("authorization"), token)
            .header(Http.Header.create("referer"), referer)
            .request();
        return res.entity().as(new GenericType<>() {
        });
    }

    @Override
    public MailMsgDetails get_msg_details(String mail,
                                          String auth_token,
                                          String id) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;

        return httpClient.get("/api/api/v1/mailbox/{mail}/{id}")
            .pathParam("mail", mail)
            .pathParam("id", id)
            .header(Http.Header.create("authorization"), token)
            .header(Http.Header.create("referer"), referer)
            .request(MailMsgDetails.class);
    }
}
