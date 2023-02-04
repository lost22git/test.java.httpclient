package okhttp;

import common.mail.MailClient;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MailClientExtTest {

    private static MailClient mailClient;

    private static String mail;

    @BeforeAll
    static void setup() {
        var httpClient = new OkHttpClient.Builder()
//            .proxy(FileClient.proxy)
            .build();
        var retrofit = new Retrofit.Builder()
            .baseUrl(MailClient.addr.toString())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build();

        mailClient = retrofit.create(MailClientExt.class);
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
