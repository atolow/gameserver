package example.com.gameserver.user.dto;

import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateResponseDto {

    private final Long id;
    private final String username;
    private final String realName;
    private final String email;
    private final String phoneNumber;
    private final Integer cash;
    private final UserRole role;
    private final boolean active;

    @Builder
    public UserCreateResponseDto(Long id, String username, String realName, String email,
                                 String phoneNumber, Integer cash, UserRole role, boolean active) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cash = cash;
        this.role = role;
        this.active = active;
    }

    public static UserCreateResponseDto from(User user) {
        return UserCreateResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .cash(user.getCash())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}