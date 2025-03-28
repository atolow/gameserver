package example.com.gameserver.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title; // 게시글 제목
    @NotBlank(message = "내용은 필수입니다.")
    private String content; // 게시글 내용

    public BoardCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}