package webflux;

import reactor.netty.transport.ProxyProvider;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.function.Consumer;

public class HttpUtil {
    public static Consumer<? super ProxyProvider.TypeSpec> configure_proxy(Proxy proxy) {
        return
            typeSpec -> {
                var addSpec = switch (proxy.type()) {
                    case SOCKS -> typeSpec.type(ProxyProvider.Proxy.SOCKS5);
                    case DIRECT -> null;
                    case HTTP -> typeSpec.type(ProxyProvider.Proxy.HTTP);
                };
                if (addSpec != null) {
                    addSpec.address((InetSocketAddress) proxy.address());
                }
            };
    }
}
