package example.com.gameserver.user.repository;

import example.com.gameserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ 단순 존재 여부 확인용
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    // ✅ 실제 유저 객체가 필요한 경우
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    // ✅ 확장 메서드 - 예외 발생 포함 (서비스 단에서 호출용)
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    default User findByUsernameOrElseThrow(String username) {
        return findByUsername(username).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    default User findByPhoneNumberOrElseThrow(String phoneNumber) {
        return findByPhoneNumber(phoneNumber).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Query(value = "SELECT o.balance FROM CMI_users o WHERE o.username = :username", nativeQuery = true)
    Double findBalanceByUsername(@Param("username") String username);


    @Query(value = "SELECT c.username, c.balance " +
            "FROM CMI_users c " +
            "JOIN users u ON c.username = u.username " +
            "ORDER BY c.balance DESC", nativeQuery = true)
    List<Object[]> findBalanceFromCMIUsers();

}

