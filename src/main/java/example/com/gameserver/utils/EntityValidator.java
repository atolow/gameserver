package example.com.gameserver.utils;

import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.domain.UserRole;
import org.springframework.security.access.AccessDeniedException;

import java.util.regex.Pattern;

public class EntityValidator {

    public static void validateIsAdmin(User user) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new IllegalStateException("어드민 권한이 없습니다.");
        }
    }

    public static void validateIsNewbie(User user) {
        if (user.getRole() != UserRole.NEWBIE && user.getRole() != UserRole.ADMIN) {
            throw new IllegalStateException("회원만 글을 작성할 수 있습니다.");
        }
    }




    public static void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식이 맞지 않습니다.");
        }
    }
    public static void validatePassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,20}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);

        if (!passwordPattern.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 8~20자이며, 영문, 숫자, 특수문자를 모두 포함해야 합니다.");
        }
    }
    public static void validateUsername(String username) {
        String usernameRegex = "^[a-zA-Z]{6,15}$";
        Pattern usernamePattern = Pattern.compile(usernameRegex);

        if (!usernamePattern.matcher(username).matches()) {
            throw new IllegalArgumentException("아이디는 영어 대소문자로만 구성된 6자 이상 15자 이하의 문자열이어야 합니다.");
        }
    }
}
