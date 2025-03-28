package example.com.gameserver.comment.service;

import example.com.gameserver.comment.dto.CommentUpdateRequestDto;
import example.com.gameserver.comment.dto.CommentUpdateResponseDto;
import example.com.gameserver.comment.dto.CommentCreateResponseDto;
import example.com.gameserver.comment.dto.CommentCreateRequestDto;
import example.com.gameserver.comment.dto.CommentResponseDto;
import example.com.gameserver.user.domain.User;

import java.util.List;

public interface CommentService {

    // 댓글 생성
    CommentCreateResponseDto createComment(Long boardId, CommentCreateRequestDto commentCreateRequestDto, User currentUser);

    // 댓글 수정
    CommentUpdateResponseDto updateComment(CommentUpdateRequestDto commentUpdateRequestDto, User currentUser);
    // 댓글 삭제
    void deleteComment(Long commentId,User user);

    // 특정 게시글의 댓글 조회
    List<CommentResponseDto> getCommentsByBoardId(Long boardId);


    // 개별 댓글 조회
    CommentCreateResponseDto getCommentById(Long commentId);

}