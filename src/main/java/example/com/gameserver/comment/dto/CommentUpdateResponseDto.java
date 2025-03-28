package example.com.gameserver.comment.dto;

import example.com.gameserver.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentUpdateResponseDto {

    private Long id;         // 수정된 댓글의 ID
    private String username; // 작성자의 이름
    private String content;  // 수정된 댓글 내용
    private LocalDateTime createdAt;  // 생성일
    private LocalDateTime modifiedAt; // 수정일

    public CommentUpdateResponseDto(Long id, String username, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Comment 엔티티를 받아 DTO로 변환하는 메서드
    public static CommentUpdateResponseDto from(Comment comment) {
        return new CommentUpdateResponseDto(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}