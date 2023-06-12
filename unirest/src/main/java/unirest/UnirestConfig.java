package unirest;

import com.fasterxml.jackson.databind.type.TypeFactory;
import kong.unirest.*;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class UnirestConfig {
    private UnirestConfig() {
    }

    public static void configureProxy(UnirestInstance rest,
                                      Proxy proxy) {
        var proxyAddr = (InetSocketAddress) proxy.address();
        var proxyHost = proxyAddr.getHostString();
        var proxyPort = proxyAddr.getPort();
        rest.config()
            .proxy(proxyHost, proxyPort);
    }

    public static void configureObjectMapper(UnirestInstance rest) {
        rest.config().setObjectMapper(new ObjectMapper() {
            final com.fasterxml.jackson.databind.ObjectMapper mapper;

            {
                mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            }

            @Override
            public <T> T readValue(String value,
                                   Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (Exception e) {
                    throw new UnirestParsingException(value, e);
                }
            }

            @Override
            public <T> T readValue(String value,
                                   GenericType<T> genericType) {
                try {
                    var type = genericType.getType();
                    var javaType = TypeFactory.defaultInstance()
                        .constructType(type);
                    return mapper.readValue(value, javaType);
                } catch (Exception e) {
                    throw new UnirestParsingException(value, e);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (Exception e) {
                    throw new UnirestException(e);
                }
            }
        });
    }
}
