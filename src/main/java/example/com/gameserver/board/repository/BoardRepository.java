package example.com.gameserver.board.repository;

import example.com.gameserver.board.domain.Board;
import example.com.gameserver.command.domain.Command;
import example.com.gameserver.notice.domain.Notice;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    default Board findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 board가 존재하지 않습니다."));
    }

    Page<Board> findAllByOrderByIdDesc(Pageable pageable);
}