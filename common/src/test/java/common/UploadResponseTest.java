package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.file.UploadResponse;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UploadResponseTest {

    private static EasyRandom random;

    private static ObjectMapper mapper;

    @BeforeAll
    static void setup() {
        var params = new EasyRandomParameters();
        random = new EasyRandom(params);
        mapper = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Test
    void test_serialize() throws Exception {
        var json = generate_json();
        System.out.println("uploadResponse json = " + json);
    }

    String generate_json() throws Exception {
        var uploadResponse = EasyRandomUtil.generate(random, UploadResponse.class);
        System.out.println("uploadResponse = " + uploadResponse);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploadResponse);
    }

    @Test
    void test_deserialize() throws Exception {
        var json = generate_json();
        System.out.println("uploadResponse json = " + json);

        var uploadResponse = mapper.readValue(json, UploadResponse.class);
        System.out.println("uploadResponse = " + uploadResponse);
    }

}
