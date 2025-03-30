package example.com.gameserver.board.dto;

import example.com.gameserver.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateResponseDto {

    private Long id; // 게시글 ID
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String author; // 작성자의 username

    // 생성자
    public BoardCreateResponseDto(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // 엔티티 -> DTO 변환 메서드
    public static BoardCreateResponseDto from(Board board) {
        return new BoardCreateResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getUsername() // 작성자 username
        );
    }
}