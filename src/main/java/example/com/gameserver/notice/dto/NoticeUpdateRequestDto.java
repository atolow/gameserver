package example.com.gameserver.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NoticeUpdateRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public NoticeUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}