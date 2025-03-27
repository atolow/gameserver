package example.com.gameserver.trade.domain;

import example.com.gameserver.common.entity.TimeBaseEntity;
import example.com.gameserver.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BuyPost extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private boolean isCompleted = false;

    public BuyPost(String title, String content, int price, User user) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.user = user;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void update(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
