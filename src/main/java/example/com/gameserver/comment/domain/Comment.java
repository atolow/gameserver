package example.com.gameserver.comment.domain;

import example.com.gameserver.board.domain.Board;
import example.com.gameserver.user.domain.User;
import example.com.gameserver.common.entity.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 작성자 (User와의 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;

    // 댓글이 속한 게시글 (Board와의 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String content; // 댓글 내용

    // 댓글 생성 시 필요한 생성자
    public Comment(User user, Board board, String content) {
        this.user = user;
        this.board = board;
        this.content = content;
    }

    // 댓글 내용 업데이트
    public void updateContent(String content) {
        this.content = content;
    }
}