package common.mail;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public interface MailClient {

    URI api_addr = URI.create("https://mail.td");
    Proxy proxy = new Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved("localhost", 55556));

    default String parse_auth_token_from_cookie(Stream<String> cookieValues) {
        return cookieValues
            .filter(v -> v.contains("auth_token="))
            .flatMap(v -> Arrays.stream(v.split(";")))
            .filter(v -> v.startsWith("auth_token="))
            .map(v -> v.substring("auth_token=".length()).trim())
            .findFirst()
            .orElse(null);
    }

    String get_auth_token(String mail);

    List<MailMsg> get_msg_list(String mail,
                               String auth_token);

    MailMsgDetails get_msg_details(String mail,
                                   String auth_token,
                                   String id
    );
}
