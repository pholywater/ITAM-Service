package hmm.itam.controller;

import hmm.itam.service.UserService;
import hmm.itam.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Long id = (Long) session.getAttribute("userId");
        if (id != null) {
            UserVo userVo = userService.getUserById(id);
            model.addAttribute("user", userVo);
            return "home";
        }
        return "redirect:/login";
    }
}