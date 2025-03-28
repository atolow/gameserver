package example.com.gameserver.board.controller;

import example.com.gameserver.board.dto.BoardCreateRequestDto;
import example.com.gameserver.board.dto.BoardResponseDto;
import example.com.gameserver.board.dto.BoardUpdateRequestDto;
import example.com.gameserver.board.service.BoardService;
import example.com.gameserver.comment.dto.CommentCreateRequestDto;
import example.com.gameserver.comment.dto.CommentCreateResponseDto;
import example.com.gameserver.comment.dto.CommentResponseDto;
import example.com.gameserver.comment.dto.CommentUpdateRequestDto;
import example.com.gameserver.comment.service.CommentService;
import example.com.gameserver.security.CustomUserDetails;
import example.com.gameserver.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // 🔎 공지사항 목록
    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<BoardResponseDto> boards = boardService.getAll(pageable);
        model.addAttribute("boards", boards);

        return "board/list"; // templates/board/list.html
    }

    @PostMapping("/{boardId}/comments")
    public String createComment(
            @PathVariable Long boardId,
            @ModelAttribute CommentCreateRequestDto commentCreateRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentService.createComment(boardId, commentCreateRequestDto, userDetails.getUser());
        return "redirect:/boards/" + boardId;
    }



    // 댓글 수정
    @PostMapping("/{boardId}/comments/{commentId}/edit")
    public String updateComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @ModelAttribute CommentUpdateRequestDto commentUpdateRequestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // commentId 설정 (Form에서는 id를 넘기지 않을 수도 있으니 명확하게 지정)
        commentUpdateRequestDto.setId(commentId);

        commentService.updateComment(commentUpdateRequestDto, userDetails.getUser());
        return "redirect:/boards/" + boardId;
    }

    // 댓글 삭제
    @PostMapping("/{boardId}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentService.deleteComment(commentId, userDetails.getUser());
        return "redirect:/boards/" + boardId;
    }











    // 🔎 공지사항 상세보기
    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        // Board 엔티티를 BoardResponseDto로 변환
        BoardResponseDto boardResponseDto = boardService.getById(id);
        model.addAttribute("board", boardResponseDto);  // 댓글 목록도 포함된 DTO를 Model에 추가
        return "board/detail";  // 상세 페이지 템플릿 이름
    }

    // 📝 작성 폼
    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {
        boardService.checkNewbie(userDetails.getUser());
        model.addAttribute("boardCreateRequestDto", new BoardCreateRequestDto());
        return "board/write"; // templates/board/create.html
    }

    // ✅ 등록 처리
    @PostMapping()
    public String create(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @Valid @ModelAttribute("boardCreateRequestDto") BoardCreateRequestDto requestDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/write";
        }

        boardService.create(requestDto, userDetails.getUser());
        return "redirect:/boards";
    }

    // ✏️ 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long id, Model model) {
        BoardResponseDto board = boardService.getByIdEdit(id,userDetails.getUser());
        model.addAttribute("boardUpdateRequestDto", new BoardUpdateRequestDto(
                board.getTitle(), board.getContent()
        ));
        model.addAttribute("boardId", id);
        return "board/edit"; // templates/board/edit.html
    }

    // ✏️ 수정 처리
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       @Valid @ModelAttribute("boardUpdateRequestDto") BoardUpdateRequestDto requestDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/edit";
        }

        boardService.update(id, requestDto, userDetails.getUser());
        return "redirect:/boards/" + id;
    }

    // ❌ 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        boardService.delete(id, userDetails.getUser());
        return "redirect:/boards";
    }
}