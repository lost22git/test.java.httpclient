package unirest;

import common.mail.MailClient;
import common.mail.MailMsg;
import common.mail.MailMsgDetails;
import jakarta.inject.Singleton;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

import java.util.List;

@Singleton
public class MailClientImpl implements MailClient {
    private final UnirestInstance rest;

    public MailClientImpl() {
        var rest = Unirest.spawnInstance();
        rest.config().defaultBaseUrl(api_addr.toString());
        UnirestConfig.configureProxy(rest, proxy);
        UnirestConfig.configureObjectMapper(rest);
        this.rest = rest;
    }


    @Override
    public String get_auth_token(String mail) {
        var cookieValues = rest.get("/zh/mail/{mail}")
            .routeParam("mail", mail)
            .asEmpty()
            .getHeaders()
            .get("set-cookie");
        return parse_auth_token_from_cookie(cookieValues.stream());
    }

    @Override
    public List<MailMsg> get_msg_list(String mail,
                                      String auth_token) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;

        return rest.get("/api/api/v1/mailbox/{mail}")
            .routeParam("mail", mail)
            .header("authorization", token)
            .header("referer", referer)
            .asObject(new GenericType<List<MailMsg>>() {
            })
            .getBody();
    }

    @Override
    public MailMsgDetails get_msg_details(String mail,
                                          String auth_token,
                                          String id) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;

        return rest.get("/api/api/v1/mailbox/{mail}/{id}")
            .routeParam("mail", mail)
            .routeParam("id", id)
            .header("authorization", token)
            .header("referer", referer)
            .asObject(MailMsgDetails.class)
            .getBody();
    }
}
