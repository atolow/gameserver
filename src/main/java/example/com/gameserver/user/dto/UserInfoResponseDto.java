package example.com.gameserver.user.dto;

import example.com.gameserver.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private String username;
    private String email;
    private Integer cash;

    public static UserInfoResponseDto from(User user) {
        return UserInfoResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .cash(user.getCash())
                .build();
    }
}