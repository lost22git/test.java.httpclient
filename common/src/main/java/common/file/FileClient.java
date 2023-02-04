package common.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public interface FileClient {
    URI addr = URI.create("https://api.anonfiles.com");

    InetSocketAddress proxy_addr = InetSocketAddress.createUnresolved("localhost", 55556);

    UploadResponse upload(InputStream inputStream) throws IOException;

    InfoResponse info(String id);
}
