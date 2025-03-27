package example.com.gameserver.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

//    @NotBlank(message = "이름은 필수입니다.")
    private String realName;

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

//    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    @NotBlank(message = "비밀번호는 필수입니다.") // ← 이거 꼭!
    private String password;

    @Builder
    public UserCreateRequestDto(String username, String realName, String email,
                                String phoneNumber, String password) {
        this.username = username;
        this.realName = realName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}