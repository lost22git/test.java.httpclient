package okhttp;

import common.file.FileClient;
import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@InjectTest
class FileClientExtTest {

    @Inject
    private static FileClient fileClient;

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
        var id = "Cfl4xbXcyb";

        var infoResponse = fileClient.info(id);

        System.out.println("infoResponse = " + infoResponse);
        assertNotNull(infoResponse);
        assertTrue(infoResponse.status());
        assertNotNull(infoResponse.data().file().url().full());
        assertNotNull(infoResponse.data().file().metadata().id());
    }

    @Test
    void get_download_uri() {
        var id = "Cfl4xbXcyb";
        var downloadUri = fileClient.get_download_uri(id);

        assertTrue(downloadUri.isPresent());
    }

    @Test
    void download() throws IOException {
        var id = "Cfl4xbXcyb";
        var downloadUri = fileClient.get_download_uri(id);

        assertTrue(downloadUri.isPresent());

        var inputStream = fileClient.download(downloadUri.get());
        var bytes = inputStream.readAllBytes();
        inputStream.close();

        assertTrue(bytes.length > 0);

    }
}
