package example.com.gameserver.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateRequestDto {

    private String content; // 댓글 내용

    // 생성자
    public CommentCreateRequestDto(String content) {
        this.content = content;
    }
}