package example.com.gameserver.notice.controller;

import example.com.gameserver.notice.dto.*;
import example.com.gameserver.notice.service.NoticeService;
import example.com.gameserver.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    // 🔎 공지사항 목록
    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<NoticeResponseDto> notices = noticeService.getAll(pageable);
        for (NoticeResponseDto dto : notices.getContent())
            System.out.println(dto.getCreatedAt());

        model.addAttribute("notices", notices);

        return "notice/list"; // templates/notice/list.html
    }

    // 🔎 공지사항 상세보기
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        NoticeResponseDto notice = noticeService.getById(id);
        model.addAttribute("notice", notice);
        return "notice/detail"; // templates/notice/detail.html
    }

    // 📝 작성 폼
    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {
        noticeService.checkAdmin(userDetails.getUser());
        model.addAttribute("noticeCreateRequestDto", new NoticeCreateRequestDto());
        return "notice/write"; // templates/notice/create.html
    }

    // ✅ 등록 처리
    @PostMapping()
    public String create(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @Valid @ModelAttribute("noticeCreateRequestDto") NoticeCreateRequestDto requestDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "notice/write";
        }

        noticeService.create(requestDto, userDetails.getUser());
        return "redirect:/notices";
    }

    // ✏️ 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long id, Model model) {
        NoticeResponseDto notice = noticeService.getByIdEdit(id,userDetails.getUser());
        model.addAttribute("noticeUpdateRequestDto", new NoticeUpdateRequestDto(
                notice.getTitle(), notice.getContent()
        ));
        model.addAttribute("noticeId", id);
        return "notice/edit"; // templates/notice/edit.html
    }

    // ✏️ 수정 처리
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       @Valid @ModelAttribute("noticeUpdateRequestDto") NoticeUpdateRequestDto requestDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "notice/edit";
        }

        noticeService.update(id, requestDto, userDetails.getUser());
        return "redirect:/notices/" + id;
    }


    // ❌ 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        noticeService.delete(id, userDetails.getUser());
        return "redirect:/notices";
    }
}