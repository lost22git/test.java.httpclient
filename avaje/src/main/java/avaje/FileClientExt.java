package avaje;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import io.avaje.http.api.Client;
import io.avaje.http.api.Get;
import io.avaje.http.api.Post;
import io.avaje.http.client.HttpClient;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Optional;

@Client
public interface FileClientExt extends FileClient {

    // TODO avaje does not support multipart now
    default UploadResponse upload(InputStream inputStream) {
        return upload(HttpRequest.BodyPublishers.ofInputStream(() -> inputStream));
    }

    @Override
    default Optional<URI> get_download_uri(String id) {
        var proxySelector = ProxySelector.of((InetSocketAddress) FileClient.proxy.address());
        var client = HttpClient.builder()
            .proxy(proxySelector)
            .baseUrl(FileClient.api_addr.toString())
            .build();
        var uri = FileClient.resolve(id);
        var html = client.request()
            .url(uri.toString())
            .GET()
            .asString()
            .body();
        return FileClient.parse_download_uri(html);
    }

    @Override
    default InputStream download(URI uri) {
        var proxySelector = ProxySelector.of((InetSocketAddress) FileClient.proxy.address());
        var client = HttpClient.builder()
            .proxy(proxySelector)
            .baseUrl(FileClient.api_addr.toString())
            .build();
        return client.request()
            .url(uri.toString())
            .GET()
            .asInputStream()
            .body();
    }

    // TODO avaje does not support multipart now
    @Post("/upload")
    UploadResponse upload(HttpRequest.BodyPublisher bodyPublisher);

    @Get("/v2/file/{id}/info")
    InfoResponse info(String id);

}
