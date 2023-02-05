package helidon.nima;

import common.file.FileClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileClientImplTest {

    private static FileClient fileClient;

    @BeforeAll
    static void setup() {
        fileClient = new FileClientImpl();
    }

    @Test
    void upload() throws IOException {
        var inputStream = FileClientImplTest.class.getClassLoader().getResourceAsStream("test.png");
        assertThat(inputStream).isNotNull();
        var uploadResponse = fileClient.upload(inputStream);

        System.out.println("uploadResponse = " + uploadResponse);
        var uploadResponseAssert = assertThat(uploadResponse);
        uploadResponseAssert.isNotNull();
        uploadResponseAssert.extracting("status").isEqualTo(true);
    }


    @Test
    void info() {
        var id = "WeZdKcVeyb";

        var infoResponse = fileClient.info(id);

        assertNotNull(infoResponse);
        assertTrue(infoResponse.status());
        assertNotNull(infoResponse.data().file().url().full());
        assertNotNull(infoResponse.data().file().metadata().id());
        System.out.println("infoResponse = " + infoResponse);
    }

}
