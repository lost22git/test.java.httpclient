package unirest;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import jakarta.inject.Singleton;
import kong.unirest.ContentType;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

@Singleton
public class FileClientImpl implements FileClient {

    private final UnirestInstance rest;

    public FileClientImpl() {
        var rest = Unirest.spawnInstance();
        rest.config().defaultBaseUrl(api_addr.toString());
        UnirestConfig.configureProxy(rest, proxy);
        UnirestConfig.configureObjectMapper(rest);
        this.rest = rest;
    }

    @Override
    public UploadResponse upload(InputStream inputStream) throws IOException {
        return rest.post("/upload")
            .field("file", inputStream, ContentType.IMAGE_PNG, "test.png")
            .asObject(UploadResponse.class)
            .getBody();
    }

    @Override
    public InfoResponse info(String id) {
        return rest.get("/v2/file/{id}/info")
            .routeParam("id", id)
            .asObject(InfoResponse.class)
            .getBody();
    }

    @Override
    public Optional<URI> get_download_uri(String id) {
        var uri = FileClient.resolve(id);
        var html = rest
            .get(uri.toString())
            .asString()
            .getBody();
        return FileClient.parse_download_uri(html);
    }

    @Override
    public InputStream download(URI uri) throws IOException {
        var bytes = rest
            .get(uri.toString())
            .asBytes()
            .getBody();
        return new ByteArrayInputStream(bytes);
    }
}
