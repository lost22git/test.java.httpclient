package avaje;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import io.avaje.http.api.Client;
import io.avaje.http.api.Get;
import io.avaje.http.api.Post;

import java.io.InputStream;
import java.net.http.HttpRequest;

@Client
public interface FileClientExt extends FileClient {

    // TODO avaje does not support multipart now
    default UploadResponse upload(InputStream inputStream) {
        return upload(HttpRequest.BodyPublishers.ofInputStream(() -> inputStream));
    }


    // TODO avaje does not support multipart now
    @Post("/upload")
    UploadResponse upload(HttpRequest.BodyPublisher bodyPublisher);

    @Get("/v2/file/{id}/info")
    InfoResponse info(String id);
}
