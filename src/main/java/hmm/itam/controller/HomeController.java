package hmm.itam.controller;

import hmm.itam.service.MemberService;
import hmm.itam.service.UserService;
import hmm.itam.vo.MemberVo;
import hmm.itam.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("index")
    public String index() {
        return "index";
    }


    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Long idx = (Long) session.getAttribute("userId");
        if (idx != null) {
            UserVo userVo = userService.getUserById(idx);
            model.addAttribute("user", userVo);
            return "home";
        }


        return "redirect:/login"; // 정상 작동
    }

    @GetMapping("/headerDepartmentList") // 부서 리스트 불러오기
    public String getHeaderDepartmentList(Model model) {
        List<MemberVo> headerDepartmentList = MemberService.getHeaderDepartmentList();
        model.addAttribute("list", headerDepartmentList);
        return "home";
    }

}