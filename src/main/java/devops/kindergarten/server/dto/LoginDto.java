package devops.kindergarten.server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class LoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
