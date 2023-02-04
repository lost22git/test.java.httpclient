package common;

import org.jeasy.random.EasyRandom;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;

public class EasyRandomUtil {
    public static <T> T generateRecord(EasyRandom random,
                                       Class<T> clazz) {

        try {
            var params = Arrays.stream(clazz.getRecordComponents())
                .map(RecordComponent::getType)
                .toArray(Class<?>[]::new);

            var args = Arrays.stream(params).map(it -> generate(random, it)).toArray();

            return clazz.getDeclaredConstructor(params).newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T generate(EasyRandom random,
                                 Class<T> clazz) {
        if (Record.class.isAssignableFrom(clazz))
            return generateRecord(random, clazz);
        return random.nextObject(clazz);
    }
}
