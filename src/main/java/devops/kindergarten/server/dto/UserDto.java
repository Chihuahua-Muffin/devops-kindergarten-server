package devops.kindergarten.server.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import devops.kindergarten.server.domain.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class UserDto {
    private String username;

    private String uid;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Status status;
}
