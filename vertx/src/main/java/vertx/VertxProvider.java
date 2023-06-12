package vertx;

import io.vertx.core.Vertx;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

@Singleton
public class VertxProvider implements Provider<Vertx> {

    private static final Object LOCK = new Object();
    volatile Vertx vertx;

    @Override
    public Vertx get() {
        if (vertx == null) {
            synchronized (LOCK) {
                if (vertx == null) {
                    vertx = Vertx.vertx();
                }
            }
        }
        return vertx;
    }
}
