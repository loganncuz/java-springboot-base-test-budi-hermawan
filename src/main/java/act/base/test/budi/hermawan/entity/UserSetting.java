package act.base.test.budi.hermawan.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="act_user_setting",
        indexes={
                @Index(columnList="id",name="Index_user_setting_id")
        }
)
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="keyName",length=100)
    @JsonProperty("key")
    @NotNull
    @Size(min=3, max=100, message = "key min char:3,max char:100, current:{userSetting.keyName.size}")
    private String keyName;

    @Column(name="valueName",length=100)
    @JsonProperty("value")
    @NotNull
    @Size(min=3, max=100, message = "value min char:3,max char:100, current:{userSetting.valueName.size}")
    private String valueName;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_SETTING_USER_ID"))
    private User userId;

    public UserSetting() {
    }

    public UserSetting(String key, String value) {
        keyName=key;
        valueName= (String) value;
    }
}
