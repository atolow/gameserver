package example.com.gameserver.board.service;

import example.com.gameserver.board.dto.BoardCreateRequestDto;
import example.com.gameserver.board.dto.BoardResponseDto;
import example.com.gameserver.board.dto.BoardUpdateRequestDto;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    // 게시글 목록 조회
    Page<BoardResponseDto> getAll(Pageable pageable);

    // 게시글 상세 조회
    BoardResponseDto getById(Long id);

    // 게시글 작성
    void create(BoardCreateRequestDto boardCreateRequestDto, User user);

    // 게시글 수정
    void update(Long id, BoardUpdateRequestDto boardUpdateRequestDto, User user);

    // 게시글 삭제
    void delete(Long id, User user);

    // 수정 폼에 필요한 게시글 조회 (사용자 권한 체크)
    BoardResponseDto getByIdEdit(Long id, User user);

    void checkNewbie(User user);
}