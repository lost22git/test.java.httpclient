package webflux;

import common.file.FileClient;
import common.file.FileClientTestBase;
import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@InjectTest
class FileClientExtTest extends FileClientTestBase {

    @Inject
    static FileClient fileClient;

    @Override
    protected FileClient fileClient() {
        return fileClient;
    }

    @Test
    @Override
    protected void upload() throws IOException {
        super.upload();
    }

    @Test
    @Override
    protected void info() {
        super.info();
    }

    @Test
    @Override
    protected void get_download_uri() {
        super.get_download_uri();
    }

    @Test
    @Override
    protected void download() throws IOException {
        super.download();
    }
}
