package example.com.gameserver.trade.service;

import example.com.gameserver.item.repository.ItemRepository;
import example.com.gameserver.trade.domain.BuyPost;
import example.com.gameserver.trade.dto.*;
import example.com.gameserver.trade.repository.BuyPostRepository;
import example.com.gameserver.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BuyPostServiceImpl implements BuyPostService {

    private final BuyPostRepository buyPostRepository;
    private final ItemRepository itemRepository;

    @Override
    public Page<BuyPostCreateResponseDto> getAll(Pageable pageable) {
        return buyPostRepository.findAllByOrderByIdDesc(pageable)
                .map(BuyPostCreateResponseDto::toDto);
    }

    @Override
    public BuyPostCreateResponseDto getById(Long id) {
        BuyPost post = buyPostRepository.findByIdOrElseThrow(id);
        return BuyPostCreateResponseDto.toDto(post);
    }
    @Transactional
    @Override
    public BuyPostCreateResponseDto create(BuyPostCreateRequestDto dto, User user) {

        BuyPost post = new BuyPost(dto.getTitle(), dto.getContent(), dto.getPrice(), user);
        BuyPost savedPost = buyPostRepository.save(post); // 저장 후 반환된 엔티티 사용

        return BuyPostCreateResponseDto.toDto(savedPost);
    }
    @Transactional
    @Override
    public BuyPostUpdateResponseDto update(Long postId, BuyPostUpdateRequestDto dto, User user) {
        BuyPost post = buyPostRepository.findByIdOrElseThrow(postId);

        if (!post.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }
        post.update(dto.getTitle(), dto.getContent(), dto.getPrice());
        // 저장된 post를 그대로 DTO 변환
        return BuyPostUpdateResponseDto.from(post);
    }
    @Transactional
    @Override
    public void delete(Long postId, User user) {
        BuyPost post = buyPostRepository.findByIdOrElseThrow(postId);
        if (!post.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        buyPostRepository.delete(post);
    }

    @Override
    public void markAsCompleted(Long postId, User user) {
        BuyPost post = buyPostRepository.findByIdOrElseThrow(postId);

        if (!post.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("완료 처리 권한이 없습니다.");
        }

        post.markAsCompleted();
    }
}