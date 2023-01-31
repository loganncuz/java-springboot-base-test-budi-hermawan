package act.base.test.budi.hermawan.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ErrorResponsePayload {
    @Transient
    @Column(name="status")
    @JsonProperty("status")
    private int status;

    @Column(name="message")
    @JsonProperty("message")
    private List<String> message;

    @Column(name="code")
    @JsonProperty("code")
    private int code;

}
