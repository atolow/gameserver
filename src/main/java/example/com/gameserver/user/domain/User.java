package example.com.gameserver.user.domain;

import example.com.gameserver.common.entity.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User  extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 15)
    private String username;

//    @Column(nullable = false, length = 50)
    private String realName;

    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean isActive = true;

    @Builder
    public User(String username, String realName, String email, String phoneNumber, String password, double balance, UserRole role, boolean isActive) {
        this.username = username;
        this.realName = realName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.role = role;
        this.isActive = isActive;
    }

    public User(String username, String email, String password, double balance, UserRole role, boolean isActive) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.role = role;
        this.isActive = isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }
    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
    public void deductBalance(int amount) {
        if (this.balance < amount) {
            throw new IllegalArgumentException("잔액 부족");
        }
        this.balance -= amount;
    }
}