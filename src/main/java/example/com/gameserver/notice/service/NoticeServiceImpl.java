package example.com.gameserver.notice.service;

import example.com.gameserver.notice.domain.Notice;
import example.com.gameserver.notice.dto.*;
import example.com.gameserver.notice.repository.NoticeRepository;
import example.com.gameserver.trade.dto.BuyPostCreateResponseDto;
import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.repository.UserRepository;
import example.com.gameserver.utils.EntityValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static example.com.gameserver.utils.EntityValidator.validateIsAdmin;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public NoticeResponseDto create(NoticeCreateRequestDto requestDto, User user) {
        validateIsAdmin(user);
        User FindUser = userRepository.findByIdOrElseThrow(user.getId());

        Notice notice = Notice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(FindUser)
                .build();
        return NoticeResponseDto.from(noticeRepository.save(notice));
    }

    @Override
    public NoticeResponseDto getById(Long id) {
        Notice notice = noticeRepository.findByIdOrElseThrow(id);
        return NoticeResponseDto.from(notice);
    }

    @Override
    public Page<NoticeResponseDto> getAll(Pageable pageable) {
        return noticeRepository.findAllByOrderByIdDesc(pageable)
                .map(NoticeResponseDto::from);
    }

    @Transactional
    @Override
    public NoticeResponseDto update(Long id, NoticeUpdateRequestDto requestDto, User user) {
        validateIsAdmin(user);
        Notice notice = noticeRepository.findByIdOrElseThrow(id);
        if (!notice.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }
        notice.update(requestDto.getTitle(), requestDto.getContent());
        return NoticeResponseDto.from(notice);
    }

    @Transactional
    @Override
    public void delete(Long id, User user) {
        validateIsAdmin(user);
        Notice notice = noticeRepository.findByIdOrElseThrow(id);
        if (!notice.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }
        noticeRepository.delete(notice);
    }
    @Override
    public void checkAdmin(User user) {
        validateIsAdmin(user);
    }
}