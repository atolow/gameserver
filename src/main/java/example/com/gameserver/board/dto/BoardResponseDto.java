package example.com.gameserver.board.dto;

import example.com.gameserver.board.domain.Board;
import example.com.gameserver.comment.dto.CommentResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String username; // 작성자
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentResponseDto> comments; // 댓글 목록

    // 생성자
    public BoardResponseDto(Long id, String title, String content, String username,
                            LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentResponseDto> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }

    // Board 엔티티를 DTO로 변환하는 메서드
    public static BoardResponseDto from(Board board) {
        List<CommentResponseDto> commentDtos = board.getComments().stream()
                .map(CommentResponseDto::from) // 각 댓글을 CommentResponseDto로 변환
                .collect(Collectors.toList());

        return new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getUsername(), // 작성자
                board.getCreatedAt(),
                board.getModifiedAt(),
                commentDtos // 댓글 목록
        );
    }
}