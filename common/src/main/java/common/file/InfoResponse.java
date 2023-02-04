package common.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see <a href="https://anonfiles.com/docs/api">API Model</a>
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public record InfoResponse(boolean status, Data data, Error error) {
    public static record Data(UploadResponse.File file) {

    }

    public static record File(UploadResponse.Url url,
                              UploadResponse.MetaData metadata) {

    }

    public static record Url(String full,
                             @JsonProperty("short") String _short) {

    }

    public static record MetaData(String id, String name,
                                  UploadResponse.Size size) {

    }

    public static record Size(long bytes, String readable) {

    }

    public static record Error(String message, String type, Integer code) {
    }

}
