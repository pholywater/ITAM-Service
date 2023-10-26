package hmm.itam.controller;

import hmm.itam.service.LoginService;
import hmm.itam.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/loginList") // 웹 URL 매핑
    public String getLoginList(Model model) {
        List<LoginVo> loginList = loginService.getLoginList();
        model.addAttribute("list", loginList);
        return "login/loginList"; // 실제 HTML 경로
    }

}
