package example.com.gameserver.trade.controller;

import example.com.gameserver.item.service.ItemService;
import example.com.gameserver.security.CustomUserDetails;
import example.com.gameserver.trade.dto.*;
import example.com.gameserver.trade.service.BuyPostService;
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
@RequestMapping("/buy")
public class BuyPostController {

    private final BuyPostService buyPostService;
    private final ItemService itemService;

    // 목록
    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Pageable pageable = PageRequest.of(page, size); // 정렬 생략 가능
        Page<BuyPostCreateResponseDto> postPage = buyPostService.getAll(pageable);

        model.addAttribute("postPage", postPage);
        return "buy/list";
    }

    // 상세
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        BuyPostCreateResponseDto post = buyPostService.getById(id);
        model.addAttribute("post", post);
        return "buy/detail";
    }

    // 글쓰기 폼
    @GetMapping("/write")
    public String writeForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                            Model model) {
        model.addAttribute("buyPostCreateRequestDto", new BuyPostCreateRequestDto());// ✅ 수정된 부분
        return "buy/write";
    }

    // 글쓰기 처리
    @PostMapping("/write")
    public String write(@Valid @ModelAttribute("buyPostCreateRequestDto") BuyPostCreateRequestDto dto,
                        BindingResult bindingResult,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "buy/write";
        }

        BuyPostCreateResponseDto saved = buyPostService.create(dto, userDetails.getUser());
        return "redirect:/buy/" + saved.getId();
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           Model model) {

        BuyPostCreateResponseDto post = buyPostService.getById(id);

        if (!post.getUsername().equals(userDetails.getUsername())) {
            return "redirect:/buy";
        }

        // 기존 내용 dto로 매핑
        BuyPostUpdateRequestDto dto = BuyPostUpdateRequestDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .build();

        model.addAttribute("buyPostUpdateRequestDto", dto);
        model.addAttribute("items", itemService.getAll(userDetails.getUser()));
        model.addAttribute("postId", id); // form action 에서 사용

        return "buy/edit";
    }

    // 수정 처리
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("buyPostUpdateRequestDto") BuyPostUpdateRequestDto dto,
                       BindingResult bindingResult,
                       @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            return "buy/edit";
        }

        buyPostService.update(id, dto, userDetails.getUser());
        return "redirect:/buy/" + id;
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        buyPostService.delete(id, userDetails.getUser());
        return "redirect:/buy";
    }

    // 거래 완료 처리
    @PostMapping("/{id}/complete")
    public String complete(@PathVariable Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        buyPostService.markAsCompleted(id, userDetails.getUser());
        return "redirect:/buy/" + id;
    }
}