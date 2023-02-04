package common.mail;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

public interface MailClient {

    URI addr = URI.create("https://mail.td");
    InetSocketAddress proxy_addr = InetSocketAddress.createUnresolved("localhost", 55556);

    String get_auth_token(String mail);

    List<MailMsg> get_msg_list(String mail,
                               String auth_token);

    MailMsgDetails get_msg_details(String mail,
                                   String auth_token,
                                   String id
    );
}
