package example.com.gameserver.board.service;

import example.com.gameserver.board.domain.Board;
import example.com.gameserver.board.dto.BoardCreateRequestDto;
import example.com.gameserver.board.dto.BoardResponseDto;
import example.com.gameserver.board.dto.BoardUpdateRequestDto;
import example.com.gameserver.board.repository.BoardRepository;
import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.domain.UserRole;
import example.com.gameserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static example.com.gameserver.utils.EntityValidator.validateIsNewbie;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 목록 조회
    @Override
    public Page<BoardResponseDto> getAll(Pageable pageable) {
        return boardRepository.findAllByOrderByIdDesc(pageable).map(BoardResponseDto::from);
    }
    // 게시글 상세 조회
    @Override
    public BoardResponseDto getById(Long id) {
        Board board = boardRepository.findByIdOrElseThrow(id);
        return BoardResponseDto.from(board);
    }


    // 게시글 작성
    @Override
    @Transactional
    public void create(BoardCreateRequestDto boardCreateRequestDto, User user) {
        validateIsNewbie(user);

        Board board = new Board(user, boardCreateRequestDto.getTitle(), boardCreateRequestDto.getContent());
        boardRepository.save(board);
    }

    // 게시글 수정
    @Override
    @Transactional
    public void update(Long id, BoardUpdateRequestDto boardUpdateRequestDto, User user) {

        Board board = boardRepository.findByIdOrElseThrow(id);

        // 작성자만 수정 가능
        if (!board.getUser().getId().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        board.updateBoard(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
    }

    // 게시글 삭제
    @Override
    @Transactional
    public void delete(Long id, User user) {
        validateIsNewbie(user);
        Board board = boardRepository.findByIdOrElseThrow(id);

        // 작성자만 삭제 가능
        if (!board.getUser().getId().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }




    // 수정 폼에 필요한 게시글 조회 (사용자 권한 체크)
    @Override
    public BoardResponseDto getByIdEdit(Long id, User user) {
        Board board = boardRepository.findByIdOrElseThrow(id);

        // 수정하려는 게시글의 작성자와 로그인된 사용자가 다르면 예외 처리
        if (!board.getUser().getId().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        return BoardResponseDto.from(board);
    }

    // 게시글 작성 권한 체크 (예: Newbie만 글쓰기 권한)
    @Override
    public void checkNewbie(User user) {
        if (user.getRole() != UserRole.NEWBIE && user.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("비회원은 작성할 수 없습니다.");
        }
    }
}