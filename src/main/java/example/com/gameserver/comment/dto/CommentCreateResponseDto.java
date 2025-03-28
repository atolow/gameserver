package example.com.gameserver.comment.dto;

import java.time.LocalDateTime;

import example.com.gameserver.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateResponseDto {

    private Long id; // 댓글 ID
    private String username; // 작성자의 username
    private String content; // 댓글 내용
    private LocalDateTime createdAt; // 댓글 작성 시간
    private LocalDateTime modifiedAt; // 댓글 수정 시간

    // 생성자
    public CommentCreateResponseDto(Long id, String username, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentCreateResponseDto from(Comment comment) {
        return new CommentCreateResponseDto(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}