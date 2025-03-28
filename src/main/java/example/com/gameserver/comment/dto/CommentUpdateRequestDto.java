package example.com.gameserver.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private Long id;         // 수정할 댓글의 ID
    private String content;  // 새로운 댓글 내용

    public CommentUpdateRequestDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}