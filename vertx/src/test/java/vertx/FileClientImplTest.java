package vertx;

import common.file.FileClient;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileClientImplTest {

    private static FileClient fileClient;

    @BeforeAll
    static void setup() {
        var vertx = Vertx.vertx();
        fileClient = new FileClientImpl(vertx);
    }


    // TODO test failed?
    @Test
    void upload() throws IOException {
        var inputStream = FileClientImplTest.class.getClassLoader().getResourceAsStream("test.png");
        assertNotNull(inputStream);
        System.out.println("upload buffer size=" + inputStream.available());
        var uploadResponse = fileClient.upload(inputStream);
        System.out.println("uploadResponse = " + uploadResponse);
        assertTrue(uploadResponse.status());
        assertNotNull(uploadResponse.data());
        assertNotNull(uploadResponse.data().file().url());
    }

    @Test
    void info() {
        var id = "WeZdKcVeyb";
        var infoResponse = fileClient.info(id);
        assertTrue(infoResponse.status());
        assertNotNull(infoResponse.data());
        assertNotNull(infoResponse.data().file().url());
        System.out.println("infoResponse = " + infoResponse);
    }
}
