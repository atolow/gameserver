package example.com.gameserver.command.controller;

import example.com.gameserver.command.dto.CommandCreateRequestDto;
import example.com.gameserver.command.dto.CommandResponseDto;
import example.com.gameserver.command.dto.CommandUpdateRequestDto;
import example.com.gameserver.command.service.CommandService;
import example.com.gameserver.security.CustomUserDetails;
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
@RequestMapping("/commands")
public class CommandController {

    private final CommandService commandservice;

    // 🔎 공지사항 목록
    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<CommandResponseDto> commands = commandservice.getAll(pageable);
        for (CommandResponseDto dto : commands.getContent())
            System.out.println(dto.getCreatedAt());

        model.addAttribute("commands", commands);

        return "command/list"; // templates/command/list.html
    }

    // 🔎 공지사항 상세보기
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        CommandResponseDto command = commandservice.getById(id);
        model.addAttribute("command", command);
        return "command/detail"; // templates/command/detail.html
    }

    // 📝 작성 폼
    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {
        commandservice.checkAdmin(userDetails.getUser());
        model.addAttribute("commandCreateRequestDto", new CommandCreateRequestDto());
        return "command/write"; // templates/command/create.html
    }

    // ✅ 등록 처리
    @PostMapping()
    public String create(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @Valid @ModelAttribute("commandCreateRequestDto") CommandCreateRequestDto requestDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "command/write";
        }

        commandservice.create(requestDto, userDetails.getUser());
        return "redirect:/commands";
    }

    // ✏️ 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable Long id, Model model) {
        CommandResponseDto command = commandservice.getByIdEdit(id,userDetails.getUser());
        model.addAttribute("commandUpdateRequestDto", new CommandUpdateRequestDto(
                command.getTitle(), command.getContent()
        ));
        model.addAttribute("commandId", id);
        return "command/edit"; // templates/command/edit.html
    }

    // ✏️ 수정 처리
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       @Valid @ModelAttribute("commandUpdateRequestDto") CommandUpdateRequestDto requestDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "command/edit";
        }

        commandservice.update(id, requestDto, userDetails.getUser());
        return "redirect:/commands/" + id;
    }


    // ❌ 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        commandservice.delete(id, userDetails.getUser());
        return "redirect:/commands";
    }
}