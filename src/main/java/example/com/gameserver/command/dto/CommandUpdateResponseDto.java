package example.com.gameserver.command.dto;

import example.com.gameserver.command.domain.Command;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommandUpdateResponseDto {

    private final Long id;
    private final String title;
    private final String content;

    @Builder
    public CommandUpdateResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static CommandUpdateResponseDto from(Command notice) {
        return CommandUpdateResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}