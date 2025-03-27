package example.com.gameserver.notice.dto;

import example.com.gameserver.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeResponseDto {

    private final Long id;
    private final String title;
    private final String content; // ✅ 추가
    private final String author;
    private final LocalDateTime createdAt;

    @Builder
    public NoticeResponseDto(Long id, String title, String content, String author, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content; // ✅ 추가
        this.author = author;
        this.createdAt = createdAt;
    }

    public static NoticeResponseDto from(Notice notice) {
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent()) // ✅ 추가
                .author(notice.getUser().getUsername())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}