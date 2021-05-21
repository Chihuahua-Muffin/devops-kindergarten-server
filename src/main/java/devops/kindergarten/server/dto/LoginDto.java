package devops.kindergarten.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
