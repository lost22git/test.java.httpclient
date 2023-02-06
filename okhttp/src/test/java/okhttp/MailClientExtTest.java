package okhttp;

import common.mail.MailClient;
import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@InjectTest
class MailClientExtTest {

    @Inject
    private static MailClient mailClient;

    private static String mail;

    @BeforeAll
    static void setup() {
        mail = "ok@end.tw";
    }

    @Test
    void test_auth_token() {
        var authToken = mailClient.get_auth_token(mail);
        System.out.println("authToken = " + authToken);
    }

    @Test
    void test_get_last_msg() {
        var authToken = mailClient.get_auth_token(mail);
        assertNotNull(authToken);

        var msgList = mailClient.get_msg_list(mail, authToken);

        System.out.println("msgList = " + msgList);
    }

    @Test
    void test_get_msg_details() {
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
