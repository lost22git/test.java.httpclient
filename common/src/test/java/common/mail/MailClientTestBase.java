package common.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class MailClientTestBase {
    //    static final String mail = "test.java.httpclient@end.tw";
    static final String mail = "ai@end.tw";

    abstract protected MailClient mailClient();

    protected void get_auth_token() {
        var authToken = mailClient().get_auth_token(mail);
        System.out.println("authToken = " + authToken);
        assertThat(authToken).isNotBlank();
    }


    protected void get_msg_list() {
        var authToken = mailClient().get_auth_token(mail);
        assertNotNull(authToken);

        var msgList = mailClient().get_msg_list(mail, authToken);
        System.out.println("msgList = " + msgList);
    }

    protected void get_msg_details() {
        var authToken = mailClient().get_auth_token(mail);
        assertNotNull(authToken);

        var msgList = mailClient().get_msg_list(mail, authToken);

        msgList.forEach(msg -> {
            var msgDetails = mailClient().get_msg_details(mail, authToken, msg.id());
            System.out.println("msgDetails = " + msgDetails);
            System.out.println("msgDetails.text = " + msgDetails.text());
        });
    }

}
