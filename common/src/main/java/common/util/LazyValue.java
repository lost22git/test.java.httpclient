package common.util;

import java.util.function.Supplier;

// copy from helidon

/**
 * A typed supplier that wraps another supplier and only retrieves the value on the first request to
 * {@link #get()}, caching the value for all subsequent invocations.
 *
 * <p><b>Helidon implementations obtained through {@link #create(java.util.function.Supplier)} and
 * {@link #create(Object)} are guaranteed to be thread safe.</b>
 *
 * @param <T> type of the provided object
 */
public interface LazyValue<T> extends Supplier<T> {
    /**
     * Create a lazy value from a supplier.
     *
     * @param supplier supplier to get the value from
     * @param <T>      type of the value
     * @return a lazy value that will obtain the value from supplier on first call to {@link #get()}
     */
    static <T> LazyValue<T> create(Supplier<T> supplier) {
        return new LazyValueImpl<>(supplier);
    }

    /**
     * Create a lazy value from a value.
     *
     * @param value actual value to return
     * @param <T>   type of the value
     * @return a lazy value that will always return the value provided
     */
    static <T> LazyValue<T> create(T value) {
        return new LazyValueImpl<>(value);
    }

    /**
     * Return true if the value is loaded, false if the supplier was not invoked.
     *
     * @return {@code true} if the value is loaded
     */
    boolean isLoaded();
}
