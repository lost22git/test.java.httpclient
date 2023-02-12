package helidon.nima;

import common.file.FileClient;
import common.file.FileClientTestBase;
import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@InjectTest
class FileClientImplTest extends FileClientTestBase {

    @Inject
    static FileClient fileClient;

    @Override
    public FileClient fileClient() {
        return fileClient;
    }

    @Test
    public void upload() throws IOException {
        super.upload();
    }


    @Test
    public void info() {
        super.info();
    }

    @Test
    public void get_download_uri() {
        super.get_download_uri();
    }

    @Test
    public void download() throws IOException {
        super.download();
    }

}
