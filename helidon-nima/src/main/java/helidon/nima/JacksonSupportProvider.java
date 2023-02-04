package helidon.nima;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.helidon.common.GenericType;
import io.helidon.common.http.Headers;
import io.helidon.common.http.Http;
import io.helidon.common.http.HttpMediaType;
import io.helidon.common.http.WritableHeaders;
import io.helidon.common.media.type.MediaTypes;
import io.helidon.nima.http.media.EntityReader;
import io.helidon.nima.http.media.EntityWriter;
import io.helidon.nima.http.media.spi.MediaSupportProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static io.helidon.common.http.Http.HeaderValues.CONTENT_TYPE_JSON;
import static java.lang.System.Logger.Level.TRACE;

public class JacksonSupportProvider implements MediaSupportProvider {
    public final static ObjectMapper JSON;

    static {
        JSON = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        ;
    }

    private static final System.Logger LOGGER = System.getLogger(JacksonSupportProvider.class.getName());

    private <T> EntityWriter<T> writer() {
        return (EntityWriter<T>) JacksonEntityWriter.INSTANCE;
    }

    private <T> EntityReader<T> reader() {
        return (EntityReader<T>) JacksonEntityReader.INSTANCE;
    }

    @Override
    public <T> WriterResponse<T> writer(GenericType<T> type,
                                        WritableHeaders<?> requestHeaders) {
        if (requestHeaders.contains(Http.Header.CONTENT_TYPE)) {
            if (requestHeaders.contains(CONTENT_TYPE_JSON)) {
                return new WriterResponse<>(SupportLevel.COMPATIBLE, this::writer);
            }
        } else {
            return new WriterResponse<>(SupportLevel.SUPPORTED, this::writer);
        }
        return WriterResponse.unsupported();
    }

    @Override
    public <T> WriterResponse<T> writer(GenericType<T> type,
                                        Headers requestHeaders,
                                        WritableHeaders<?> responseHeaders) {
        LOGGER.log(TRACE, "jackson writer for type=" + type.getTypeName());

        // check if accepted
        for (HttpMediaType acceptedType : requestHeaders.acceptedTypes()) {
            if (acceptedType.test(MediaTypes.APPLICATION_JSON)) {
                return new WriterResponse<>(SupportLevel.COMPATIBLE, this::writer);
            }
        }

        if (requestHeaders.acceptedTypes().isEmpty()) {
            return new WriterResponse<>(SupportLevel.COMPATIBLE, this::writer);
        }

        return WriterResponse.unsupported();
    }

    static class JacksonEntityWriter<T> implements EntityWriter<T> {

        private static final JacksonEntityWriter<?> INSTANCE = new JacksonEntityWriter<>();

        @Override
        public void write(GenericType<T> type,
                          T object,
                          OutputStream outputStream,
                          Headers requestHeaders,
                          WritableHeaders<?> responseHeaders) {
            write(object, outputStream);
        }

        @Override
        public void write(GenericType<T> type,
                          T object,
                          OutputStream outputStream,
                          WritableHeaders<?> headers) {
            write(object, outputStream);
        }

        private static <T> void write(T object,
                                      OutputStream outputStream) {
            try {
                JSON.writeValue(outputStream, object);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> ReaderResponse<T> reader(GenericType<T> type,
                                        Headers requestHeaders) {
        LOGGER.log(TRACE, "jackson reader for type=" + type.getTypeName());

        if (requestHeaders.contains(CONTENT_TYPE_JSON)) {
            return new ReaderResponse<>(SupportLevel.COMPATIBLE, this::reader);
        } else {
            return ReaderResponse.unsupported();
        }
    }

    @Override
    public <T> ReaderResponse<T> reader(GenericType<T> type,
                                        Headers requestHeaders,
                                        Headers responseHeaders) {
        LOGGER.log(TRACE, "jackson reader for type=" + type.getTypeName());

        for (HttpMediaType acceptedType : requestHeaders.acceptedTypes()) {
            if (acceptedType.test(MediaTypes.APPLICATION_JSON) || acceptedType.mediaType().isWildcardType()) {
                return new ReaderResponse<>(SupportLevel.COMPATIBLE, this::reader);
            }
        }

        if (requestHeaders.acceptedTypes().isEmpty()) {
            return new ReaderResponse<>(SupportLevel.COMPATIBLE, this::reader);
        }

        return ReaderResponse.unsupported();
    }

    static class JacksonEntityReader<T> implements EntityReader<T> {
        private static final JacksonEntityReader<?> INSTANCE = new JacksonEntityReader<>();

        @Override
        public T read(GenericType<T> type,
                      InputStream stream,
                      Headers headers) {
            return read(type, stream);
        }

        @Override
        public T read(GenericType<T> type,
                      InputStream stream,
                      Headers requestHeaders,
                      Headers responseHeaders) {
            return read(type, stream);
        }

        private static <T> T read(GenericType<T> type,
                                  InputStream stream) {
            try {
                return (T) JSON.readValue(stream, type.rawType());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

