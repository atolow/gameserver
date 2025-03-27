package example.com.gameserver.utils;

import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.domain.UserRole;
import org.springframework.security.access.AccessDeniedException;

public class EntityValidator {

    public static void validateIsAdmin(User user) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("어드민 권한이 없습니다.");
        }
    }
    public static void validateIsNewbie(User user) {
        if (user.getRole() != UserRole.NEWBIE) {
            throw new AccessDeniedException("비회원은 할 수 없습니다.");
        }
    }
}
