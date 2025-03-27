package example.com.gameserver.trade.service;

import example.com.gameserver.trade.dto.*;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuyPostService {

    // 모든 게시글 조회
    Page<BuyPostCreateResponseDto> getAll(Pageable pageable);

    // 단건 조회
    BuyPostCreateResponseDto getById(Long id);

    // 게시글 생성
    BuyPostCreateResponseDto create(BuyPostCreateRequestDto dto, User user);

    // 게시글 수정
    BuyPostUpdateResponseDto update(Long postId, BuyPostUpdateRequestDto dto, User user);

    // 게시글 삭제
    void delete(Long postId, User user);

    // 거래 완료 처리
    void markAsCompleted(Long postId, User user);



}