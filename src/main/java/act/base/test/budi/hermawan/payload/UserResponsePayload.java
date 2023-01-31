package act.base.test.budi.hermawan.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponsePayload {
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user_data")
    private Object userData;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("max_records")
    private Integer maxRecords;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("offset")
    private Integer offset;
}
