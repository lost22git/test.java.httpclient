package okhttp;

import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * okhttp log -> slf4j
 */
public enum HttpLog implements HttpLoggingInterceptor.Logger {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(HttpLog.class);

    @Override
    public void log(@NotNull String s) {
        LOG.debug(s);
    }

    public HttpLoggingInterceptor createInterceptor() {
        return new HttpLoggingInterceptor(INSTANCE);
    }
}
