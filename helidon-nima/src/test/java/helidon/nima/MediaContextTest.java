package helidon.nima;

import io.helidon.common.HelidonServiceLoader;
import io.helidon.nima.http.media.FormParamsSupportProvider;
import io.helidon.nima.http.media.PathSupportProvider;
import io.helidon.nima.http.media.StringSupportProvider;
import io.helidon.nima.http.media.multipart.MultiPartSupportProvider;
import io.helidon.nima.http.media.spi.MediaSupportProvider;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ServiceLoader;

import static org.assertj.core.api.Assertions.assertThat;

;

class MediaContextTest {

    @Test
    void load_providers() {
        /**
         * Copy from @{@link MediaContextImpl#providers }
         */
        List<String> mcProviders = HelidonServiceLoader.builder(ServiceLoader.load(MediaSupportProvider.class))
            .addService(new StringSupportProvider())
            .addService(new FormParamsSupportProvider())
            .addService(new PathSupportProvider())
            .build()
            .asList()
            .stream()
            .map(Object::getClass)
            .map(Class::getName)
            .toList();

        System.out.println("mcProviders = " + mcProviders);

        assertThat(mcProviders).contains(
            MultiPartSupportProvider.class.getName(),
            JacksonSupportProvider.class.getName()
        );

    }

}
