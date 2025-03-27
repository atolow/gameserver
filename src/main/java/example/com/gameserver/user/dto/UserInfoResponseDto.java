package example.com.gameserver.user.dto;

import example.com.gameserver.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private String username;
    private String email;
    private int balance;

    public static UserInfoResponseDto from(User user) {
        return UserInfoResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .balance(user.getBalance())
                .build();
    }
}