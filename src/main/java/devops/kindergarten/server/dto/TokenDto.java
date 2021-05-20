package devops.kindergarten.server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
