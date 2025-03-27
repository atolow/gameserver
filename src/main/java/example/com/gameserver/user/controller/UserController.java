package example.com.gameserver.user.controller;

import example.com.gameserver.security.CustomUserDetails;
import example.com.gameserver.user.dto.*;
import example.com.gameserver.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 🟢 회원가입 폼
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userCreateRequestDto", new UserCreateRequestDto());
        return "user/register"; // templates/user/register.html
    }

    // 🟢 회원가입 처리
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userCreateRequestDto") UserCreateRequestDto requestDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        try {
            userService.createUser(requestDto); // 예외 발생 가능
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage()); // 메시지 전달
            return "user/register"; // ← 다시 회원가입 폼으로
        }
    }

    // 🔐 비밀번호 변경 폼
    @GetMapping("/change-password")
    public String changePasswordForm(Model model) {
        model.addAttribute("userPasswordChangeRequestDto", new UserPasswordChangeRequestDto());
        return "user/change-password"; // templates/user/change-password.html
    }

    // 🔐 비밀번호 변경 처리
    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @Valid @ModelAttribute("userPasswordChangeRequestDto") UserPasswordChangeRequestDto requestDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "user/change-password";
        }

        try {
            userService.updatePassword(userDetails.getUser().getId(), requestDto);
            model.addAttribute("message", "비밀번호가 변경되었습니다.");
            return "redirect:/users/me";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/change-password";
        }
    }

    // 🔎 마이페이지 (내 정보)
    @GetMapping("/me")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        UserResponseDto user = userService.getUserById(userDetails.getUser().getId());
        model.addAttribute("user", user);
        return "user/mypage"; // templates/user/mypage.html
    }


    @GetMapping("/deactivate")
    public String deactivateForm() {
        return "user/deactivate"; // ← 이 템플릿 보여주기만 함
    }
    // ❌ 탈퇴 처리
    @PostMapping("/deactivate")
    public String deactivate(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deactivateUser(userDetails.getUser().getId());
        return "redirect:/logout";
    }
}