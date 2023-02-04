package vertx;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.ProxyType;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.multipart.MultipartForm;
import io.vertx.uritemplate.UriTemplate;

import java.io.IOException;
import java.io.InputStream;

public class FileClientImpl implements FileClient {
    private final WebClient webClient;

    public FileClientImpl(Vertx vertx) {
        var proxyOptions = new ProxyOptions()
            .setHost(proxy_addr.getHostName())
            .setPort(proxy_addr.getPort())
            .setType(ProxyType.SOCKS5);
        var options = new WebClientOptions()
            .setDefaultHost(addr.getHost())
//            .setSsl(true)
//            .setVerifyHost(false)
            .setLogActivity(true)
            .setProxyOptions(proxyOptions);
        this.webClient = WebClient.create(vertx, options);
    }

    @Override
    public UploadResponse upload(InputStream inputStream) throws IOException {
        var uri = UriTemplate.of("/upload");
        var buffer = Buffer.buffer(inputStream.readAllBytes());
        inputStream.close();
        var form = MultipartForm.create()
            .binaryFileUpload("file", "test.png", buffer, "image/png");

        return webClient.post(uri)
            .as(BodyCodec.json(UploadResponse.class))
            .expect(ResponsePredicate.status(200, 300))
            .sendMultipartForm(form)
            .toCompletionStage()
            .toCompletableFuture()
            .join()
            .body();
    }

    @Override
    public InfoResponse info(String id) {
        var uri = UriTemplate.of("/v2/file/{id}/info");
        return webClient.get(uri)
            .setTemplateParam("id", id)
            .as(BodyCodec.json(InfoResponse.class))
            .expect(ResponsePredicate.status(200, 300))
            .send()
            .toCompletionStage()
            .toCompletableFuture()
            .join()
            .body();
    }
}
