package common.mail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public record MailMsgDetails(
    String id,
    String from,
    @JsonProperty("mailbox")
    String to,
    String subject,
    @JsonProperty("posix-millis")
    long ts,
    Map<String, Object> header,
    Map<String, Object> body,
    List<Object> attachments
) {

    public String text() {
        if (body == null) return "";
        return (String) body.getOrDefault("text", "");
    }
}
