package example.com.gameserver.notice.service;

import example.com.gameserver.notice.dto.*;
import example.com.gameserver.trade.dto.BuyPostCreateResponseDto;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {

    NoticeResponseDto create(NoticeCreateRequestDto requestDto, User user);

    NoticeResponseDto getById(Long id);

    Page<NoticeResponseDto> getAll(Pageable pageable);

    NoticeResponseDto update(Long id, NoticeUpdateRequestDto requestDto, User user);

    void delete(Long id, User user);

    void checkAdmin(User user);
}