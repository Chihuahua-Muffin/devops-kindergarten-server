package devops.kindergarten.server.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import devops.kindergarten.server.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@Data
public class UserDto {
    @Size(min = 6)
    private String username;

    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String status;
}
