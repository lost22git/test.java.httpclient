package common.file;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class FileClientTestBase {

    protected abstract FileClient fileClient();

    protected void upload() throws IOException {
        var inputStream = FileClientTestBase.class.getClassLoader().getResourceAsStream("test.png");
        assertThat(inputStream).isNotNull();
        var uploadResponse = fileClient().upload(inputStream);

        System.out.println("uploadResponse = " + uploadResponse);
        var uploadResponseAssert = assertThat(uploadResponse);
        uploadResponseAssert.isNotNull();
        uploadResponseAssert.extracting("status").isEqualTo(true);
    }


    protected void info() {
        var id = "Cfl4xbXcyb";

        var infoResponse = fileClient().info(id);

        assertNotNull(infoResponse);
        assertTrue(infoResponse.status());
        assertNotNull(infoResponse.data().file().url().full());
        assertNotNull(infoResponse.data().file().metadata().id());
        System.out.println("infoResponse = " + infoResponse);
    }

    protected void get_download_uri() {
        var id = "Cfl4xbXcyb";
        var downloadUri = fileClient().get_download_uri(id);

        assertTrue(downloadUri.isPresent());
    }

    protected void download() throws IOException {
        var id = "Cfl4xbXcyb";
        var downloadUri = fileClient().get_download_uri(id);

        assertTrue(downloadUri.isPresent());

        var inputStream = fileClient().download(downloadUri.get());
        var bytes = inputStream.readAllBytes();
        inputStream.close();

        assertTrue(bytes.length > 0);

    }

}
