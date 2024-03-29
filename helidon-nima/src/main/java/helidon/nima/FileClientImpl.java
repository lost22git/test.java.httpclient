package helidon.nima;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import io.helidon.common.http.Http;
import io.helidon.common.http.HttpMediaType;
import io.helidon.nima.http.media.multipart.WriteableMultiPart;
import io.helidon.nima.http.media.multipart.WriteablePart;
import io.helidon.nima.webclient.http1.Http1Client;
import jakarta.inject.Singleton;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

@Singleton
public class FileClientImpl implements FileClient {

    private final Http1Client httpClient;

    public FileClientImpl() {
        httpClient = Http1Client.builder()
            .baseUri(api_addr)
            .build();
    }

    @Override
    public UploadResponse upload(InputStream inputStream) {
        var multipart = WriteableMultiPart.builder()
            .addPart(WriteablePart.builder("file")
                .fileName("test.png")
                .contentType(HttpMediaType.create("image/png"))
                .content(() -> inputStream)
                .build()
            )
            .build();

        return httpClient.method(Http.Method.POST)
            .path("/upload")
            .submit(multipart)
            .as(UploadResponse.class);
    }

    @Override
    public InfoResponse info(String id) {
        return httpClient.get("/v2/file/{id}/info")
            .pathParam("id", id)
            .request(InfoResponse.class);
    }

    @Override
    public Optional<URI> get_download_uri(String id) {
        var uri = FileClient.resolve(id);
        var html = httpClient
            .get(uri.toString())
//            .get(uri.getScheme() + "://" + uri.getHost() + ":443" + "/" + uri.getPath())
            .request()
            .as(String.class);
        return FileClient.parse_download_uri(html);
    }

    @Override
    public InputStream download(URI uri) {
        return httpClient
//            .get(uri.getScheme() + "://" + uri.getHost() + ":443" + "/" + uri.getPath())
            .get(uri.toString())
            .request()
            .inputStream();
    }
}
