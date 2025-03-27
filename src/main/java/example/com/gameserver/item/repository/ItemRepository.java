package example.com.gameserver.item.repository;

import example.com.gameserver.item.domain.Item;
import example.com.gameserver.trade.domain.BuyPost;
import example.com.gameserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 특정 유저가 등록한 아이템들 (운영자 확인용)
    List<Item> findByUser(User user);

    // 판매되지 않은 아이템만 조회    // 특정 유저가 등록한 아이템들 (운영자 확인용)
    //    List<Item> findByUser(User user);
    //
    //    // 판매되지 않은 아이템만 조회
    //    List<Item> findByIsSoldFalse();
    //
    //    // 이름으로 검색 (선택)
    //    List<Item> findByNameContaining(String keyword);
    //    Optional<Item> findByName(String name);
    //
    //    Optional<Item> findByItem(String item);
    //
    //    default Item findByItemOrElseThrow(String item) {
    //        return findByItem(item).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
    //    }
    //
    //    default Item findByIdOrElseThrow(Long id) {
    //        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
    //    }
    List<Item> findByIsSoldFalse();

    // 이름으로 검색 (선택)
    List<Item> findByNameContaining(String keyword);

    Optional<Item> findByName(String name);

    default Item findByItemOrElseThrow(String name) {
        return findByName(name).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
    }

    default Item findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
    }

}