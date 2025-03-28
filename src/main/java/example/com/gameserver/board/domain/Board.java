package example.com.gameserver.board.domain;

import example.com.gameserver.common.entity.TimeBaseEntity;
import example.com.gameserver.user.domain.User;
import example.com.gameserver.comment.domain.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(nullable = false, length = 30)
    private String title; // 게시글 제목
    @Column(nullable = false, length = 200)
    private String content; // 게시글 내용

    // 게시글에 대한 댓글들 (1:N 관계, CascadeType.ALL을 통해 댓글도 같이 삭제)
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // 게시글 작성자를 받아 Board 객체를 생성하는 생성자
    public Board(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    // 게시글 제목과 내용을 수정하는 메서드
    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }
}