package act.base.test.budi.hermawan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="act_user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ssn"}, name="USER_UNIQUE_ID"),
        indexes={
                @Index(columnList="id",name="Index_id"),
                @Index(columnList="ssn",name="Index_ssn")
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ssn",length=16)
    @JsonProperty("ssn")
    @NotNull
    private String ssn;

    @Column(name="firstName",length=100)
    @JsonProperty("first_name")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9.\\-\\/+=@_ ]*$" , message = "Cannot contain special character :{user.firstName.pattern}")
    @Size(min=3, max=100, message = "firstName min char:3,max char:100, current:{user.firstName.size}")
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="middleName",length=100)
    @JsonProperty("middle_name")
    private String middleName;

    @Column(name="last_name",length=100)
    @JsonProperty("lastName")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9.\\-\\/+=@_ ]*$" , message = "Cannot contain special character :{user.lastName.pattern}")
    @Size(min=3, max=100, message = "firstName min char:3,max char:100, current:{user.lastName.size}")
    private String lastName;

    @Column(name="birthDate")
    @JsonProperty("birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private Date birthDate;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @Column(name="createdTime")
    @JsonProperty("created_time")
    private Date  createdTime;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @Column(name="updatedTime")
    @JsonProperty("updated_time")
    private Date  updatedTime;

    @Column(name="createdBy")
    @JsonProperty("created_by")
    @NotNull
    @Size(max=100, message = "createdBy max char:100, current:{user.createdBy.size}")
    private String createdBy="SYSTEM";

    @Column(name="updatedBy")
    @JsonProperty("updated_by")
    @NotNull
    @Size(max=100, message = "updatedBy max char:100, current:{user.updatedBy.size}")
    private String updatedBy="SYSTEM";

    @Column(name="isActive")
    @JsonProperty("is_active")
    @NotNull
    private Boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @Column(name="deletedTime")
    @JsonProperty("deleted_time")
    private Date  deletedTime;

    @JsonIgnoreProperties({ "userId" })
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSetting> userSettings;
}
