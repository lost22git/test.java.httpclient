package helidon.nima;

import common.mail.MailClient;
import common.mail.MailClientTestBase;
import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@InjectTest
class MailClientImplTest extends MailClientTestBase {

    @Inject
    static MailClient mailClient;

    @Override
    protected MailClient mailClient() {
        return mailClient;
    }

    @Test
    public void get_auth_token() {
        super.get_auth_token();
    }

    @Test
    public void get_msg_list() {
        super.get_msg_list();
    }

    @Test
    public void get_msg_details() {
        super.get_msg_details();
    }

}
