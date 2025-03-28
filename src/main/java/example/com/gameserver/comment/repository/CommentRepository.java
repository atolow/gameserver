package example.com.gameserver.comment.repository;

import example.com.gameserver.board.domain.Board;
import example.com.gameserver.comment.domain.Comment;
import example.com.gameserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글에 달린 댓글들을 조회하는 메서드
    List<Comment> findByBoardId(Long boardId);

    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 comment가 존재하지 않습니다."));
    }

}