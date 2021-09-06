package devops.kindergarten.server.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
