package webflux;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import common.util.LazyValue;
import io.netty.handler.logging.LogLevel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.util.Optional;

public interface FileClientExt extends FileClient {
    LazyValue<WebClient> inner_client = LazyValue.create(() -> {
        var httpClient = HttpClient.create()
            .followRedirect(true)
            .proxy(HttpUtil.configure_proxy(proxy))
            .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        return WebClient.builder()
            .baseUrl(api_addr.toString())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    });

    @Override
    default UploadResponse upload(InputStream inputStream) throws IOException {
        var resource = new InputStreamResource(inputStream);

//        var multipartBodyBuilder = new MultipartBodyBuilder();
//        multipartBodyBuilder
//            .part("file", resource, MediaType.IMAGE_PNG).filename("test.png");
//        var multipartBody = multipartBodyBuilder.build();
//
//        return inner_client.get().post()
//            .uri("/upload")
//            .contentType(MediaType.MULTIPART_FORM_DATA)
//            .body(BodyInserters.fromMultipartData(multipartBody))
//            .retrieve()
//            .bodyToMono(UploadResponse.class)
//            .block();

        return upload(resource);
    }

    @Override
    default Optional<URI> get_download_uri(String id) {
        var uri = FileClient.resolve(id);
        var html = inner_client.get().get()
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return FileClient.parse_download_uri(html);
    }

    @Override
    default InputStream download(URI uri) throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(1024 * 10);
        inputStream.connect(outputStream);
        var bufferFlux = inner_client.get().get()
            .uri(uri)
            .retrieve()
            .bodyToFlux(DataBuffer.class)
            .publishOn(Schedulers.boundedElastic())
            .doFinally(s -> {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        DataBufferUtils.write(bufferFlux, outputStream)
            .log("Writing to output buffer ...")
            .subscribe();
        return inputStream;
    }

    @GetExchange("/v2/file/{id}/info")
    InfoResponse info(@PathVariable("id") String id);

    @PostExchange(value = "/upload", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadResponse upload(@RequestPart("file")
                          Resource file);
}
