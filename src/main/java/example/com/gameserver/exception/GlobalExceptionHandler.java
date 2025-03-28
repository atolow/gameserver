package example.com.gameserver.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model, HttpServletRequest request) {
        log.warn("권한 예외 발생: {}", ex.getMessage());  // 예외 메시지 로그 출력
        model.addAttribute("errorMessage", ex.getMessage());  // 동적으로 에러 메시지 추가

        // 이전 페이지 URL을 참조, 없으면 기본 URL로 설정
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            referer = "/";  // 기본 URL
        }
        model.addAttribute("redirectUrl", referer);  // 리다이렉트할 URL 추가
        return "error/alert";  // Custom Error Page로 이동
    }

    // 다른 예외 처리나 기본 오류 페이지 처리
    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");  // 기본 에러 메시지
        model.addAttribute("redirectUrl", "/");  // 리다이렉트할 URL
        return "error/alert";  // error.html 템플릿을 렌더링
    }
}