package act.base.test.budi.hermawan.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResponsePayload {
    @Transient
    @Column(name="status")
    private String status;

    @Column(name="message")
    private String message;

    @Column(name="code")
    private int code;

}
