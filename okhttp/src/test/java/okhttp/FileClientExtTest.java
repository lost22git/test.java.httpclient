package okhttp;

import common.file.FileClient;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileClientExtTest {

    private static FileClient fileClient;

    @BeforeAll
    static void setup() {
        var httpClient = new OkHttpClient.Builder()
            .proxy(FileClient.proxy)
            .build();
        var retrofit = new Retrofit.Builder()
            .baseUrl(FileClient.addr.toString())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build();

        fileClient = retrofit.create(FileClientExt.class);
    }

    @Test
    void upload() throws IOException {
        var to_upload_url = FileClientExtTest.class.getClassLoader().getResource("test.png");

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
