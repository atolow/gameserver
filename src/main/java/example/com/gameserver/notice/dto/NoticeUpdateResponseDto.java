package example.com.gameserver.notice.dto;

import example.com.gameserver.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeUpdateResponseDto {

    private final Long id;
    private final String title;
    private final String content;

    @Builder
    public NoticeUpdateResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static NoticeUpdateResponseDto from(Notice notice) {
        return NoticeUpdateResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}