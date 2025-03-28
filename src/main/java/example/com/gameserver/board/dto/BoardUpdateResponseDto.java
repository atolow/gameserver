package example.com.gameserver.board.dto;

import example.com.gameserver.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateResponseDto {

    private Long id; // 게시글 ID
    private String title; // 게시글 제목
    private String content; // 게시글 내용

    // 생성자
    public BoardUpdateResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // 엔티티 -> DTO 변환 메서드
    public static BoardUpdateResponseDto from(Board board) {
        return new BoardUpdateResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent()
        );
    }
}