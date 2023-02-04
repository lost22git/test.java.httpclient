package helidon.nima;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import io.helidon.common.http.Http;
import io.helidon.common.http.HttpMediaType;
import io.helidon.nima.http.media.multipart.WriteableMultiPart;
import io.helidon.nima.http.media.multipart.WriteablePart;
import io.helidon.nima.webclient.http1.Http1Client;

import java.io.IOException;
import java.io.InputStream;

public class FileClientImpl implements FileClient {

    private final Http1Client httpClient;

    public FileClientImpl() {
        httpClient = Http1Client.builder()
            .baseUri(addr.toString() + ":443")
            .build();
    }

    @Override
    public UploadResponse upload(InputStream inputStream) throws IOException {
        var multipart = WriteableMultiPart.builder()
            .addPart(WriteablePart.builder("file")
                .fileName("test.png")
                .contentType(HttpMediaType.create("image/png"))
                .inputStream(() -> inputStream)
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
}
