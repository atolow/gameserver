package example.com.gameserver.comment.service;

import example.com.gameserver.comment.domain.Comment;
import example.com.gameserver.comment.dto.CommentCreateRequestDto;
import example.com.gameserver.comment.dto.CommentCreateResponseDto;
import example.com.gameserver.comment.dto.CommentUpdateRequestDto;
import example.com.gameserver.comment.dto.CommentUpdateResponseDto;
import example.com.gameserver.comment.dto.CommentResponseDto;
import example.com.gameserver.comment.repository.CommentRepository;
import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.domain.UserRole;
import example.com.gameserver.board.domain.Board;
import example.com.gameserver.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static example.com.gameserver.utils.EntityValidator.validateIsNewbie;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 생성
    @Override
    public CommentCreateResponseDto createComment(Long boardId, CommentCreateRequestDto commentCreateRequestDto, User currentUser) {
        validateIsNewbie(currentUser);
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        Comment comment = new Comment(currentUser, board, commentCreateRequestDto.getContent());
        commentRepository.save(comment);

        return CommentCreateResponseDto.from(comment);
    }

    // 댓글 수정
    @Override
    @Transactional
    public CommentUpdateResponseDto updateComment(CommentUpdateRequestDto commentUpdateRequestDto, User user) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentUpdateRequestDto.getId());

        // 댓글 작성자가 현재 사용자와 동일한지 확인 (본인만 수정 가능)
        if (!comment.getUser().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 댓글 내용 업데이트
        comment.updateContent(commentUpdateRequestDto.getContent());
        commentRepository.save(comment);

        return CommentUpdateResponseDto.from(comment);
    }

    // 댓글 삭제
    @Override
    @Transactional
    public void deleteComment(Long commentId,User user) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        if (!comment.getUser().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    // 특정 게시글의 댓글 조회
    @Override
    public List<CommentResponseDto> getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        return comments.stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    // 개별 댓글 조회
    @Override
    public CommentCreateResponseDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return CommentCreateResponseDto.from(comment);  // CommentCreateResponseDto로 변경
    }
}