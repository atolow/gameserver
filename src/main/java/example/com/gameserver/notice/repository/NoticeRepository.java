package example.com.gameserver.notice.repository;

import example.com.gameserver.notice.domain.Notice;
import example.com.gameserver.trade.domain.BuyPost;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    default Notice findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
    }

    Page<Notice> findAllByOrderByIdDesc(Pageable pageable);
}