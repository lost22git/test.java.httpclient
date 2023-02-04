package common.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see <a href="https://anonfiles.com/docs/api">API Model</a>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record UploadResponse(boolean status,
                             Data data,
                             Error error
) {
    public static record Data(File file) {

    }

    public static record File(Url url, MetaData metadata) {

    }

    public static record Url(String full,
                             @JsonProperty("short") String _short) {

    }

    public static record MetaData(String id, String name, Size size) {

    }

    public static record Size(long bytes, String readable) {

    }

    public static record Error(String message, String type, Integer code) {
    }

}
