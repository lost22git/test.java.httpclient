package common.file;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.util.Optional;

public interface FileClient {
    URI api_addr = URI.create("https://api.anonfiles.com");
    URI addr = URI.create("https://anonfiles.com");

    Proxy proxy = new Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved("localhost", 55556));

    static URI resolve(String id) {
        return addr.resolve("/" + id);
    }

    static Optional<URI> parse_download_uri(String html) {
//        System.out.println("html = " + html);
        var a = Jsoup.parse(html)
            .select("#download-url")
            .first();
        if (a == null) return Optional.empty();
        return Optional.of(URI.create(a.attr("href")));
    }

    UploadResponse upload(InputStream inputStream) throws IOException;

    InfoResponse info(String id);

    Optional<URI> get_download_uri(String id);

    InputStream download(URI uri);
}
