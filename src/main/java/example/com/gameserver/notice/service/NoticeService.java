package example.com.gameserver.notice.service;

import example.com.gameserver.notice.dto.*;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

    NoticeResponseDto create(NoticeCreateRequestDto requestDto, User user);

    NoticeResponseDto getByIdEdit(Long id,User user);

    Page<NoticeResponseDto> getAll(Pageable pageable);

    NoticeResponseDto update(Long id, NoticeUpdateRequestDto requestDto, User user);

    void delete(Long id, User user);

    void checkAdmin(User user);
    NoticeResponseDto getById(Long id);
}