package example.com.gameserver.item.controller;

import example.com.gameserver.item.dto.*;
import example.com.gameserver.item.service.ItemService;
import example.com.gameserver.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String list(@AuthenticationPrincipal CustomUserDetails userDetails,
                       Model model) {
        model.addAttribute("items", itemService.getAll(userDetails.getUser()));
        return "/admin/item/list";
    }

    @GetMapping("/{id}")
    public String detail(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.getById(id,userDetails.getUser()));
        return "/admin/item/detail";
    }

    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        itemService.checkAdmin(userDetails.getUser()); // 👉 권한 검사만 서비스로 위임
        model.addAttribute("itemCreateRequestDto", new ItemCreateRequestDto());
        return "/admin/item/create";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @Valid @ModelAttribute("itemCreateRequestDto") ItemCreateRequestDto dto) {
        ItemCreateResponseDto saved = itemService.create(dto,userDetails.getUser());
        return "redirect:/admin/items/" + saved.getId();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long id, Model model) {
        ItemDetailResponseDto item = itemService.getById(id,userDetails.getUser());
        model.addAttribute("itemUpdateRequestDto", new ItemUpdateRequestDto(item));
        model.addAttribute("itemId", id);
        return "/admin/item/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable Long id,
                         @Valid @ModelAttribute("itemUpdateRequestDto") ItemUpdateRequestDto dto) {
        itemService.update(id, dto,userDetails.getUser());
        return "redirect:/admin/items/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteConfirm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable Long id, Model model) {
        itemService.checkAdmin(userDetails.getUser());
        model.addAttribute("itemId", id);
        return "/admin/item/delete"; // templates/item/delete.html
    }
    @PostMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable Long id) {
        itemService.delete(id,userDetails.getUser());
        return "redirect:/admin/items";
    }
}