package avaje;

import common.file.FileClient;
import io.avaje.http.client.HttpClient;
import io.avaje.http.client.JacksonBodyAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileClientExtTest {

    private static FileClient fileClient;

    @BeforeAll
    static void setup() {

        var proxySelector = ProxySelector.of((InetSocketAddress) FileClient.proxy.address());

        var httpClient = HttpClient.builder()
            .proxy(proxySelector)
            .baseUrl(FileClient.addr.toString())
            .bodyAdapter(new JacksonBodyAdapter())
            .build();

        fileClient = httpClient.create(FileClientExt.class);
    }


    // TODO avaje does not support multipart now
    @Test
    void upload() throws IOException {
        var to_upload_url = FileClientExtTest.class.getClassLoader().getResource("to_upload.png");

        assertNotNull(to_upload_url);

        var inputStream = to_upload_url.openStream();
        var uploadResponse = fileClient.upload(inputStream);

        System.out.println("uploadResponse = " + uploadResponse);
        assertNotNull(uploadResponse);
        assertTrue(uploadResponse.status());
        assertNotNull(uploadResponse.data().file().url().full());
        assertNotNull(uploadResponse.data().file().metadata().id());
    }

    @Test
    void info() {
        var id = "WeZdKcVeyb";

        var infoResponse = fileClient.info(id);

        System.out.println("infoResponse = " + infoResponse);
        assertNotNull(infoResponse);
        assertTrue(infoResponse.status());
        assertNotNull(infoResponse.data().file().url().full());
        assertNotNull(infoResponse.data().file().metadata().id());
    }

}
