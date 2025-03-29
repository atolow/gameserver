package example.com.gameserver.minecraft;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class MinecraftController {

    private final List<String> onlineUsers = new CopyOnWriteArrayList<>();

    // ✅ 이건 JSON API니까 @ResponseBody 유지
    @PostMapping("/api/minecraft/online-users")
    @ResponseBody
    public String receiveOnlineUsers(@RequestBody List<String> users) {
        System.out.println("받은 유저 목록: " + users); // 디버깅용
        onlineUsers.clear();
        onlineUsers.addAll(users);
        return "OK";
    }

    // ✅ 이건 HTML 렌더링용이라서 Controller로 두는 게 맞음
    @GetMapping("/minecraft/online-users")
    public String showOnlineUsers(Model model) {
        model.addAttribute("users", onlineUsers);
        return "minecraft/online-users";
    }
}