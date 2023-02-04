package helidon.nima;

import common.mail.MailClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MailClientImplTest {

    private static MailClient mailClient;
    private static String mail;

    @BeforeAll
    static void setup() {
        mailClient = new MailClientImpl();
        mail = "nima@end.tw";
    }

    @Test
    void get_auth_token() {
        var authToken = mailClient.get_auth_token(mail);
        System.out.println("authToken = " + authToken);
        assertThat(authToken).isNotBlank();
    }


    @Test
    void get_msg_list() {
        var authToken = mailClient.get_auth_token(mail);
        assertNotNull(authToken);

        var msgList = mailClient.get_msg_list(mail, authToken);
        System.out.println("msgList = " + msgList);
    }

    @Test
    void get_msg_details() {
        var authToken = mailClient.get_auth_token(mail);
        assertNotNull(authToken);

        var msgList = mailClient.get_msg_list(mail, authToken);

        msgList.forEach(msg -> {
            var msgDetails = mailClient.get_msg_details(mail, authToken, msg.id());
            System.out.println("msgDetails = " + msgDetails);
            System.out.println("msgDetails.text = " + msgDetails.text());
        });
    }

}
