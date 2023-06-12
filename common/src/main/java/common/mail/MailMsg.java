package common.mail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MailMsg(
    String id,
    String from,

    @JsonProperty("mailbox")
    String to,

    String subject,

    @JsonProperty("posix-millis")
    long ts

) {

}
