package example.com.gameserver.trade.repository;

import example.com.gameserver.item.domain.Item;
import example.com.gameserver.trade.domain.BuyPost;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuyPostRepository extends JpaRepository<BuyPost, Long> {

    Page<BuyPost> findAllByOrderByIdDesc(Pageable pageable);
    default BuyPost findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("구매거래를 찾을 수 없습니다."));
    }
}